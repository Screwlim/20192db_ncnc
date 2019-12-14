<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인 처리 JSP</title>
</head>
<body>
    <%
        // 인코딩 처리
        request.setCharacterEncoding("euc-kr"); 
        
        // 로그인 화면에 입력된 아이디와 비밀번호를 가져온다
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
        // URL 및 로그인관련 전달 메시지
        String msg = "";
        
        if(res == 1)    // 로그인 성공
        { 
            session.setAttribute("sessionID", id);
            msg = "first.jsp";
           	response.sendRedirect(msg);

        }
        else if(res == 0) // 비밀번호가 틀릴경우
        {
        	out.println("<script>alert('잘못된 비밀번호입니다.');history.back();</script>");
        	out.flush();
        	msg = "LoginPage.jsp?msg=0";
        }
        else    // 아이디가 틀릴경우
        {
        	out.println("<script>alert('존재하지 않는 아이디입니다.');history.back();</script>");
        	out.flush();
            msg = "LoginPage.jsp?msg=0";
        }
    %>
</body>
</html>