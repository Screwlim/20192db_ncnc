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
		out.println("id�� null�Դϴ�");
	if (pw == "" || pw == null)
		out.println("pw�� null�Դϴ�");
	if (name == "" || name == null)
		out.println("name�� null�Դϴ�");
	if (phone_num == "" || phone_num == null)
		out.println("phone_num�� null�Դϴ�");
	if (address == "" || address == null)
		out.println("address�� null�Դϴ�");
	if (mail == "" || mail == null)
		out.println("mail�� null�Դϴ�");

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
		
		//out.println("Oracle �����ͺ��̽� db�� ���������� �����߽��ϴ�");
		Statement stmt = con.createStatement(); // SQL ������ ���������� Statement ��ü ����



		//���⼭ ���̵� �ߺ� �˻� �ٽ� ��!

		String sql = "SELECT * FROM ACCOUNT WHERE ID=" + "'" + id + "'";

		rs = stmt.executeQuery(sql);

		if (rs.isBeforeFirst()) {
			//���̵� ���Ұ���! �ߺ��� ID ������
			out.println("<script>alert('�ߺ��˻縦 ���ּ���');</script>");
			con.close(); //�������.
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
		
		out.println("<script>alert('ȸ�������� �����մϴ�');</script>");



	} catch (Exception e) {
		System.out.println("Oracle �����ͺ��̽� db ���ӿ� ������ �ֽ��ϴ�. <hr>");

		System.out.println(e.getMessage());

		e.printStackTrace();
	} finally {
		out.print("<script>location.href='first.jsp';</script>");
	}
%>
<body>
</body>
</html>