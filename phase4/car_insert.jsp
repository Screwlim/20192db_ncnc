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
	String seller = request.getParameter("seller");
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
		if (seller.trim().equals("") || vnum.trim().equals("") || price.trim().equals("") || model_year.trim().equals("") || fuel.trim().equals("") || category.trim().equals("") || color.trim().equals("") || engine.trim().equals("") || trans.trim().equals("") ||  detail.trim().equals("")) {
			out.println("<script>alert('��� �׸��� ������ �Է����ּ���');</script>");
		} else {
			String driverName = "oracle.jdbc.driver.OracleDriver";

			String url = "jdbc:oracle:thin:@localhost:1600:xe";

			ResultSet rs = null;

			Class.forName(driverName);

			Connection con = DriverManager.getConnection(url, "nicar", "car"); //url + id + pw
			
			con.setAutoCommit(false);

			//out.println("Oracle �����ͺ��̽� db�� ���������� �����߽��ϴ�");
			
			String sql = "select * from vehicle where vehicle_num = '" + vnum + "'";
			
			Statement stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			//���ÿ� ���� ��ȣ�� ������ �Է��Ѵٸ�..?
			if (rs.next()) {
				out.println("<script>alert('�̹� �����ϴ� ������ȣ�Դϴ�.');</script>");
			} else {
				//seller ���� ���� Ȯ��
				
				sql = "select * from account where id = '" + seller + "'";
				
				System.out.println(sql);
				
				rs = stmt.executeQuery(sql);				
				
				if(rs.next()){
					sql = "select * from user_sequences where sequence_name = 'ORDER_SEQ'";
					
					rs = stmt.executeQuery(sql);		
					
					if(!rs.next()){
						sql = "create SEQUENCE order_seq START WITH 1000 INCREMENT BY 1";
						
						int res = stmt.executeUpdate(sql);
					}
					
					con.commit();
					
					sql = "insert into vehicle values (" + "'" + vnum + "', to_date('" + model_year + "', 'yyyy-mm-dd'), " + mileage + ", " + price + ", " + fuel + ", " + color + ", " + category + ", " + engine + ", " + trans + ", " + detail + ")";

					System.out.println(sql);
					
					int res = stmt.executeUpdate(sql);

					if (res == 0) {
						con.rollback();
						out.println("<script>alert('������������ ó�� �Ǿ����ϴ�. �ٽ� Ȯ�����ּ���.');</script>");
					} else {
						sql = "insert into order_info(order_num, seller, vnum) values (ORDER_SEQ.NEXTVAL, '" + seller + "', '" + vnum +"')";

						System.out.println(sql);
						
						res = stmt.executeUpdate(sql);
						
						if(res == 1){
							con.commit();
							out.println("<script>alert('���������� ó�� �Ǿ����ϴ�.')</script>");
						}
						else{
							con.rollback();
							out.println("<script>alert('������������ ó�� �Ǿ����ϴ�. �ٽ� Ȯ�����ּ���.');</script>");
						}
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
		out.println("<script>alert('�ٽ� �õ����ּ���.');</script>");
	} finally {
		out.print("<script>location.href='CarInsert.jsp';</script>");
	}
%>
<body>

</body>
</html>