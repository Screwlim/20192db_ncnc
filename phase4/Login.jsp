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
        	 
			String url = "jdbc:oracle:thin:@localhost:1600:xe";
             
			ResultSet rs = null;
             
			Class.forName(driverName);
             
			con = DriverManager.getConnection(url,"nicar","car");
         
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
        }
        else if(res == 0) // ��й�ȣ�� Ʋ�����
        {
            msg = "LoginPage.jsp?msg=0";
        }
        else    // ���̵� Ʋ�����
        {
            msg = "LoginPage.jsp?msg=0";
        }
        response.sendRedirect(msg);
    %>
</body>
</html>