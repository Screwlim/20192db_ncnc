<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<html>
<head>
<link rel=stylesheet href="statDetail.css">
<%
	try {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "ncnc", "ncnc");

		Statement stmt = con.createStatement();

		String sql = "select extract(month from order_date) as month, sum(price) from vehicle v join order_info on Vnum = vehicle_num where buyer is not null group by extract(month from order_date) order by extract(month from order_date)";

		rs = stmt.executeQuery(sql);
%>
</head>

<body>
	<div class="entire">
		<div class="room_state">
			<span> 월별 매출 </span>
		</div>

		<div class="Vehicle_detail">
			<%
				while (rs.next()) {
						out.print("<div class = \"month_price\">");
						out.print("&nbsp;" + rs.getInt(1) + "월: " + rs.getString(2) + "원");
						out.print("</div>");
					}
			%>
		</div>
		<%
			con.close();
			} catch (Exception e) {
				out.println("Oracle Database Connection Something Problem. <hr>");

				out.println(e.getMessage());

				e.printStackTrace();
			} finally {
			}
		%>
	
</body>
</html>
</html>