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
	String vnum = request.getParameter("vnum");
	String mileage = request.getParameter("mileage");
	String  price = request.getParameter("price");
	String  model_year = request.getParameter("model_year");
	String  fuel = request.getParameter("fuel");
	String  category = request.getParameter("category");
	String  color = request.getParameter("color");
	String  engine = request.getParameter("engine");
	String  trans = request.getParameter("trans");
	String  detail = request.getParameter("detail");

	try {
		if (vnum.trim().equals("") || price.trim().equals("") || model_year.trim().equals("") || fuel.trim().equals("") || category.trim().equals("") || color.trim().equals("") || engine.trim().equals("") || trans.trim().equals("") ||  detail.trim().equals("")) {
			out.println("<script>alert('��� �׸��� ������ �Է����ּ���');</script>");
		} else {
			String driverName = "oracle.jdbc.driver.OracleDriver";

			String url = "jdbc:oracle:thin:@localhost:1600:xe";

			ResultSet rs = null;

			Class.forName(driverName);

			Connection con = DriverManager.getConnection(url, "nicar", "car"); //url + id + pw

			//out.println("Oracle �����ͺ��̽� db�� ���������� �����߽��ϴ�");
			
			String sql = "select * from vehicle where vehicle_num = '" + vnum + "'";
			
			Statement stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			String seller = (String) session.getAttribute("sessionID");
			
			if (rs.next()) {
				out.println("<script>alert('�̹� �����ϴ� ������ȣ�Դϴ�.');</script>");
			} else {
				//seller ���� ���� Ȯ��
				
				sql = "select * from account where id = '" + seller + "'";
				
				System.out.println(sql);
				
				rs = stmt.executeQuery(sql);				
				
				if(rs.next()){
					
					sql = "insert into vehicle values (" + "'" + vnum + "', to_date(" + model_year + ", yyyy-mm-dd), " + mileage + ", " + price + ", " + fuel + ", " + color + ", " + category + ", " + engine + ", " + trans + ", " + detail + ")";

					System.out.println(sql);
					
					int res = stmt.executeUpdate(sql);

					if (res == 0) {
						out.println("<script>alert('������������ ó�� �Ǿ����ϴ�. �ٽ� Ȯ�����ּ���.');</script>");
					} else {
						sql = "select max(order_num) as num from order_info";
						
						rs = stmt.executeQuery(sql);
						
						int cnt = 0;
						
						if(rs.next()){
							cnt = rs.getInt("num");
						}
						
						sql = "insert into order_info(order_num, seller, vnum) values (" + cnt + ", '" + seller + "', '" + vnum +"')";

						System.out.println(sql);
						
						res = stmt.executeUpdate(sql);
						
						
						out.println("<script>alert('���������� ó�� �Ǿ����ϴ�.')</script>");
						con.commit();
					}
					
				}
				else{
					out.println("<script>alert('�������� �ʴ� ȸ���Դϴ�.');</script>");
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
		out.print("<script>location.href='CarInsert.jsp';</script>");
	}
%>
<body>

</body>
</html>