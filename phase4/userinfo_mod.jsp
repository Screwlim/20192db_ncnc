<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
</head>
<%
	String user_pw = request.getParameter("user_pw");
	String name = request.getParameter("name");
	String phone = request.getParameter("phone");
	String email = request.getParameter("email");
	String address = request.getParameter("address");
	String gender = request.getParameter("gender");
	String birth = request.getParameter("birth");
	String job = request.getParameter("job");

	if (user_pw == "" || user_pw == null)
		out.println("user_pw이 null입니다");
	if (name == "" || name == null)
		out.println("name이 null입니다");
	if (phone == "" || phone == null)
		out.println("phone이 null입니다");
	if (email == "" || email == null)
		out.println("email이 null입니다");
	if (address == "" || address == null)
		out.println("address이 null입니다");
	
	Connection conn = null;
	
	try {


		String url = "jdbc:oracle:thin:@localhost:1521:xe";


		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url, "ncnc", "ncnc");

		Statement stmt = conn.createStatement(); // SQL 쿼리를 날리기위한 Statement 객체 생성

		conn.setAutoCommit(false);
		
		conn.commit();
		
		String sql1 = "update account set PASSWORD= \'" + user_pw + "\' where id = \'" + session.getAttribute("sessionID") + "\'";
		String sql2 = "update account set NAME= \'" + name + "\' where id = \'" + session.getAttribute("sessionID") + "\'";
		String sql3 = "update account set PHONE_NUM= \'" + phone + "\' where id = '" + session.getAttribute("sessionID") + "\'";
		String sql4 = "update account set EMAIL_ADDRESS= \'" + email + "\' where id = \'" + session.getAttribute("sessionID") + "\'";
		String sql5 = "update account set ADDRESS= \'" + address + "\' where id = \'" + session.getAttribute("sessionID") + "\'";
		String sql6 = "update account set GENDER= \'" + gender + "' where id = \'" + session.getAttribute("sessionID") + "\'";
		String sql7 = "update account set BIRTH_DATE= \'" + birth + "' where id = \'" + session.getAttribute("sessionID") + "\'";
		String sql8 = "update account set JOB= \'" + job + "\' where id = \'" + session.getAttribute("sessionID") + "\'";


		
		stmt.executeUpdate(sql1);
		stmt.executeUpdate(sql2);
		stmt.executeUpdate(sql3);
		stmt.executeUpdate(sql4);
		stmt.executeUpdate(sql5);
		stmt.executeUpdate(sql6);
		stmt.executeUpdate(sql7);
		stmt.executeUpdate(sql8);
		

		conn.commit();
		conn.close();
		
		out.println("<script language='javascript'>");
		out.println("alert('수정이 완료되었습니다.')");
		out.println("location.href='userInfo.jsp'");
		out.println("</script>");

	} catch (Exception e) {

		System.out.println("Oracle 데이터베이스 db 접속에 문제가 있습니다. <hr>");	
		System.out.println(e.getMessage());
		
		conn.rollback();
		
		out.println("<script language='javascript'>");
		out.println("alert('올바르지 않는 수정값이 있습니다.')");
		out.println("</script>");
		
		e.printStackTrace();
	}
%>
<body>
</body>
</html>