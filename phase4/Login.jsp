<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>�α��� ó�� JSP</title>
</head>
<body>
    <%
        // ���ڵ� ó��
        request.setCharacterEncoding("euc-kr"); 
        
        // �α��� ȭ�鿡 �Էµ� ���̵�� ��й�ȣ�� �����´�
        String id= request.getParameter("id");
        String pw = request.getParameter("password");
        
        int res = -1;
        String pw_db = "";
        Statement stmt = null;
        Connection con = null;
        
		try {
			String driverName = "oracle.jdbc.driver.OracleDriver"; 
        	 
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
             
			ResultSet rs = null;
             
			Class.forName(driverName);
             
			con = DriverManager.getConnection(url,"ncnc","ncnc");
         
			stmt = con.createStatement(); 
             
			String sql= "SELECT password FROM Account WHERE id ="+"'"+id+"'";
            
			rs = stmt.executeQuery(sql);
             
			if(rs.next()){
				pw_db = rs.getString("password");
            	 
				if(pw_db.equals(pw))
					res = 1;	//equal
				else
					res = 0;	//not equal
			}
			else{
				res = -1;		//not EXIST
			}
        } catch (Exception sqle) {
            throw new RuntimeException(sqle.getMessage());
        } finally {
            try{
                if ( stmt != null ){ stmt.close(); stmt=null; }
                if ( con != null ){ con.close(); con=null;    }
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        // URL �� �α��ΰ��� ���� �޽���
        String msg = "";
        
        if(res == 1)    // �α��� ����
        { 
            session.setAttribute("sessionID", id);
            msg = "first.jsp";
           	response.sendRedirect(msg);

        }
        else if(res == 0) // ��й�ȣ�� Ʋ�����
        {
        	out.println("<script>alert('�߸��� ��й�ȣ�Դϴ�.');history.back();</script>");
        	out.flush();
        	msg = "LoginPage.jsp?msg=0";
        }
        else    // ���̵� Ʋ�����
        {
        	out.println("<script>alert('�������� �ʴ� ���̵��Դϴ�.');history.back();</script>");
        	out.flush();
            msg = "LoginPage.jsp?msg=0";
        }
    %>
</body>
</html>