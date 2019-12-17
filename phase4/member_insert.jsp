<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>alert</title>
</head>
<%
	request.setCharacterEncoding("euc-kr");
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String name = request.getParameter("name");
	String phone_num = request.getParameter("phone_num");
	String mail = request.getParameter("mail");
	String address = request.getParameter("address");

	String gender = request.getParameter("gender");

	String birth = "";
	String birth_yy = request.getParameter("birth_yy");
	String birth_mm = request.getParameter("birth_mm");
	String birth_dd = request.getParameter("birth_dd");
	String job = request.getParameter("job");
	String is_admin = request.getParameter("is_admin");

	if (id == "" || id == null)
		out.println("id이 null입니다");
	if (pw == "" || pw == null)
		out.println("pw이 null입니다");
	if (name == "" || name == null)
		out.println("name이 null입니다");
	if (phone_num == "" || phone_num == null)
		out.println("phone_num이 null입니다");
	if (address == "" || address == null)
		out.println("address이 null입니다");
	if (mail == "" || mail == null)
		out.println("mail이 null입니다");

	if (birth_yy == "" || birth_yy == null) {
		birth_yy = null;
	}
	;
	if (birth_mm == "" || birth_mm == null) {
		birth_mm = null;
	}
	;
	if (birth_dd == "" || birth_dd == null) {
		birth_dd = null;
	}
	;

	if (birth_yy == null || birth_mm == null || birth_dd == null) {
		birth = "";
	} else {
		birth = birth_yy + "-" + birth_mm + "-" + birth_dd;
	}

	if (job == "" || job == null) {
		job = null;
	}
	;
	if (is_admin == "" || is_admin == null) {
		is_admin = "F";
	}
	;

	try {

		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "ncnc", "ncnc"); //url + id + pw

		con.setAutoCommit(false);
		con.commit();
		
		//out.println("Oracle 데이터베이스 db에 성공적으로 접속했습니다");
		Statement stmt = con.createStatement(); // SQL 쿼리를 날리기위한 Statement 객체 생성



		//여기서 아이디 중복 검사 다시 함!

		String sql = "SELECT * FROM ACCOUNT WHERE ID=" + "'" + id + "'";

		rs = stmt.executeQuery(sql);

		if (rs.isBeforeFirst()) {
			//아이디 사용불가능! 중복된 ID 존재함
			out.println("<script>alert('중복검사를 해주세요');</script>");
			con.close(); //연결끊기.
		}

		sql = null;

		out.println(id);
		out.println(pw);
		out.println(name);
		out.println(phone_num);
		out.println(mail);
		out.println(address);
		out.println(gender);
		out.println(birth);
		out.println(job);
		out.println(is_admin);

		System.out.println(birth);

		if (birth == "") {

			sql = "INSERT INTO ACCOUNT "
					+ "(ID, PASSWORD, NAME, PHONE_NUM, EMAIL_ADDRESS, ADDRESS, GENDER, BIRTH_DATE, JOB,  IS_ADMIN)"
					+ "VALUES ('" + id + "', '" + pw + "', '" + name + "', '" + phone_num + "', '" + mail
					+ "', '" + address + "', '" + gender + "', null, '" + job + "', '" + is_admin + "')";
		} else {

			sql = "INSERT INTO ACCOUNT "
					+ "(ID, PASSWORD, NAME, PHONE_NUM, EMAIL_ADDRESS, ADDRESS, GENDER, BIRTH_DATE, JOB,  IS_ADMIN)"
					+ "VALUES ('" + id + "', '" + pw + "', '" + name + "', '" + phone_num + "', '" + mail
					+ "', '" + address + "', '" + gender + "', to_date('" + birth + "', 'yyyy-mm-dd'), '" + job
					+ "', '" + is_admin + "')";
		}

		stmt.executeUpdate(sql);
		
		con.commit();
		con.close();
		
		out.println("<script>alert('회원가입을 축하합니다');</script>");



	} catch (Exception e) {
		System.out.println("Oracle 데이터베이스 db 접속에 문제가 있습니다. <hr>");

		System.out.println(e.getMessage());

		e.printStackTrace();
	} finally {
		out.print("<script>location.href='first.jsp';</script>");
	}
%>
<body>
</body>
</html>