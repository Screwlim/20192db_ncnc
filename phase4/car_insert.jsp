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
			out.println("<script>alert('모든 항목을 기입해 입력해주세요');</script>");
		} else {
			String driverName = "oracle.jdbc.driver.OracleDriver";

			String url = "jdbc:oracle:thin:@localhost:1600:xe";

			ResultSet rs = null;

			Class.forName(driverName);

			Connection con = DriverManager.getConnection(url, "nicar", "car"); //url + id + pw

			//out.println("Oracle 데이터베이스 db에 성공적으로 접속했습니다");
			
			String sql = "select * from vehicle where vehicle_num = '" + vnum + "'";
			
			Statement stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			String seller = (String) session.getAttribute("sessionID");
			
			if (rs.next()) {
				out.println("<script>alert('이미 존재하는 차량번호입니다.');</script>");
			} else {
				//seller 존재 유무 확인
				
				sql = "select * from account where id = '" + seller + "'";
				
				System.out.println(sql);
				
				rs = stmt.executeQuery(sql);				
				
				if(rs.next()){
					
					sql = "insert into vehicle values (" + "'" + vnum + "', to_date(" + model_year + ", yyyy-mm-dd), " + mileage + ", " + price + ", " + fuel + ", " + color + ", " + category + ", " + engine + ", " + trans + ", " + detail + ")";

					System.out.println(sql);
					
					int res = stmt.executeUpdate(sql);

					if (res == 0) {
						out.println("<script>alert('비정상적으로 처리 되었습니다. 다시 확인해주세요.');</script>");
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
						
						
						out.println("<script>alert('정상적으로 처리 되었습니다.')</script>");
						con.commit();
					}
					
				}
				else{
					out.println("<script>alert('존재하지 않는 회원입니다.');</script>");
				}
			}
			

			rs.close();
			stmt.close();
			con.close();
		}
	} catch (Exception e) {

		out.println("Oracle 데이터베이스 db 접속에 문제가 있습니다. <hr>");

		out.println(e.getMessage());

		e.printStackTrace();
	} finally {
		out.print("<script>location.href='CarInsert.jsp';</script>");
	}
%>
<body>

</body>
</html>