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
	request.setCharacterEncoding("euc-kr");
	String num = request.getParameter("report_num");
	String content = request.getParameter("report_content");

	try {
		if (num.trim().equals("") || content.trim().equals("")) {
			out.println("<script>alert('��� �׸��� ������ �Է����ּ���');</script>");
		} else {
			String driverName = "oracle.jdbc.driver.OracleDriver";

			String url = "jdbc:oracle:thin:@localhost:1600:xe";

			ResultSet rs = null;

			Class.forName(driverName);

			Connection con = DriverManager.getConnection(url, "nicar", "car"); //url + id + pw

			//out.println("Oracle �����ͺ��̽� db�� ���������� �����߽��ϴ�");

			String sql = "select * from filter where order_num = " + num;

			Statement stmt = con.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				out.println("<script>alert('�̹� ����� ��ϵ� �Ź��Դϴ�.');</script>");
			} else {
				// �Ǹ����� �Ź����� �Է¹��� ORDER_NUM�� �����ϴ��� Ȯ��.
				sql = "select * from order_info where buyer is null and Order_num = " + num;

				rs = stmt.executeQuery(sql);

				if (rs.next()) {
					sql = "insert into FILTER values (" + num + ")";

					// System.out.println(sql);

					int res = stmt.executeUpdate(sql);

					if (res == 0) {
						out.println("<script>alert('������������ ó�� �Ǿ����ϴ�. �ٽ� Ȯ�����ּ���.');</script>");
					} else {
						out.println("<script>alert('���������� ó�� �Ǿ����ϴ�.')</script>");
						con.commit();
					}
				} else {
					out.println("<script>alert('�Ǹ����� �Ź����� ��ġ�ϴ� �Ź��� ���������ʽ��ϴ�.')</script>");
				}

			}
			rs.close();
			stmt.close();
			con.close();
		}
	} catch (Exception e) {

		out.println("Oracle �����ͺ��̽� db ���ӿ� ������ �ֽ��ϴ�. <hr>");

		out.println(e.getMessage());

		e.printStackTrace();
	} finally {
		out.print("<script>location.href='admin.jsp';</script>");
	}
%>
<body>

</body>
</html>