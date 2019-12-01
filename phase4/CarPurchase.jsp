<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<html>
    <head>
		<%
		if(session.getAttribute("sessionID") == null)
		{
			response.sendRedirect("Login.jsp"); // 로그인 안되있으면 로그인 페이지로 옮겨감 추후에 첫화면으로 바꿔야함!
		}
		String id = (String)session.getAttribute("sessionID");
		String idx = request.getParameter("idx");
		try {
			String driverName = "oracle.jdbc.driver.OracleDriver"; 
	 
	        String url = "jdbc:oracle:thin:@localhost:1600:xe";
	 
	        ResultSet rs = null;
	        
	        Class.forName(driverName);
	 
	        Connection con = DriverManager.getConnection(url,"nicar","car");
	 
	        Statement stmt = con.createStatement();
	        
	        String inDate   = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());

			// order_info update
			String sql = "update order_info set order_date = TO_DATE( '" + inDate + "', 'YYYY/MM/DD'), Buyer = \'" + session.getAttribute("sessionID") + "\' where order_num = '" + idx + "'";
	        
	        int res = stmt.executeUpdate(sql);
	        
	        if ( res == 1){
	        	out.println("<script>alert('구매하셨습니다');</script>");
	        }
	        else{
	        	out.println("<script>alert('다시 시도해주세요');</script>");
	        }
	        
	        //con.commit();
	        
	        con.close();
		}catch(Exception e){
					out.println("Oracle Database Connection Something Problem. <hr>");
		 
        			out.println(e.getMessage());
 
        			e.printStackTrace();
		}
		finally{
    		out.print("<script>location.href='board.jsp';</script>");
		}
		
		%>
		
    </head>

    <body>
    </body>
</html>