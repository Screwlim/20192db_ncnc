<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<html>
    <head>
		<%
		if(session.getAttribute("sessionID") == null)
		{
			response.sendRedirect("Login.jsp"); // �α��� �ȵ������� �α��� �������� �Űܰ� ���Ŀ� ùȭ������ �ٲ����!
		}
		String id = (String)session.getAttribute("sessionID");
		String idx = request.getParameter("idx");
		try {
			String driverName = "oracle.jdbc.driver.OracleDriver"; 
	 
	        String url = "jdbc:oracle:thin:@localhost:1521:root";
	 
	        ResultSet rs = null;
	        
	        Class.forName(driverName);
	 
	        Connection con = DriverManager.getConnection(url,"BOARD","board");
	 
	        Statement stmt = con.createStatement();        
	 
	        String sql = "SELECT * FROM BOARD WHERE IDX="+idx;
	        
	        rs = stmt.executeQuery(sql);
	        
	        rs.next();
	        
	        if( !id.equals( rs.getString("WRITER") ) ){
	        	out.println("<script>alert('������ �����ϴ�');</script>");
	        	return;
	        }
	        
	        sql = "DELETE FROM BOARD WHERE IDX="+idx;
	        	        
	        stmt.executeQuery(sql);
	        
	        out.println("<script>alert('�����ϼ̽��ϴ�');</script>");
	        
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