<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<html>
<head>
<link rel=stylesheet href="boardDetail.css">
<%
	String idx = request.getParameter("idx");
	String rd = request.getParameter("rd_only");
	
	boolean rd_only = true;
	
	if( rd == null || !rd.equals("1")){
		rd_only = false; //수정가능
	}
	
	try {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1600:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "nicar", "car");

		Statement stmt = con.createStatement();

		String sql = "SELECT order_num, seller, vnum, price, mileage, extract(year from model_year) as year FROM vehicle join order_info on vehicle_num = vnum WHERE order_num = " + idx;

		System.out.println("1 :" + sql);
		
		rs = stmt.executeQuery(sql);

		String order_num = "";
		String seller= "";
		String vnum = "";
		String price = "";
		String mileage = "";
		String year = "";
		if(rs.next()){
			order_num = rs.getString("order_num");
			seller = rs.getString("seller");
			vnum = rs.getString("vnum");
			price = rs.getString("price");
			mileage = rs.getString("mileage");
			year = rs.getString("year");
		}
		System.out.println(year);
%>
</head>
<body>
	<div class="container">
		<form name="info" action="car_update.jsp" method="post">
			<div class="row">
				<div class="col-25">
					<label for="fname">매물 변호</label>
				</div>
				<div class="col-75">
					<input type="text" value="<%=order_num%>" id="order_num" name="order_num" placeholder="매물 번호" disabled>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="fname">판매자</label>
				</div>
				<div class="col-75">
					<input type="text" value="<%=seller %>" id="seller" name="seller" placeholder="판매자" disabled>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="fname">차량 변호</label>
				</div>
				<div class="col-75">
					<input type="text" value="<%=vnum%>" id="vnum" name="vnum" placeholder="차량 번호" readonly>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">가격</label>
				</div>
				<div class="col-75">
					<input type="number" value="<%=price%>" id="price" name="price" placeholder="가격" <%if(rd_only){out.print("readonly");} %>>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">주행거리</label>
				</div>
				<div class="col-75">
					<input type="number" id="mileage" value="<%=mileage%>" name="mileage" placeholder="주행거리"
						min=1000 max=1000000 <%if(rd_only){out.print("readonly");} %>>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="country">연식</label>
				</div>
				<div class="col-75">
					<input type="date" id="model_year" name="model_year" value="<%=year+"-01-01"%>"
						min="1977-01-01" max="2019-12-31" <%if(rd_only){out.print("readonly");} %>>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">연료</label>
				</div>
				<div class="col-75">
					<select id="fuel" name="fuel" <%if(rd_only){out.print("disabled");} %>>
						<%
							sql = "select * from fuels";

								rs = stmt.executeQuery(sql);

								while (rs.next()) {
									out.print("<option value=" + rs.getInt("fuel_id") + ">" + rs.getInt("fuel_id") + " "
											+ rs.getString("fuel_type") + "</option>");
								}
						%>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="time">차종</label>
				</div>
				<div class="col-75">
					<select id="category" name="category" <%if(rd_only){out.print("disabled");} %>>
						<%
							sql = "select * from category";

								rs = stmt.executeQuery(sql);

								while (rs.next()) {
									out.print("<option value=" + rs.getInt("c_id") + ">" + rs.getInt("c_id") + " "
											+ rs.getString("c_type") + "</option>");
								}
						%>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">색상</label>
				</div>
				<div class="col-75">
					<select id="color" name="color" <%if(rd_only){out.print("disabled");} %>>
						<%
							sql = "select * from colors";

								rs = stmt.executeQuery(sql);

								while (rs.next()) {
									out.print("<option value=" + rs.getInt("color_id") + ">" + rs.getInt("color_id") + " "
											+ rs.getString("color_name") + "</option>");
								}
						%>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">배기량</label>
				</div>
				<div class="col-75">
					<select id="engine" name="engine" <%if(rd_only){out.print("disabled");} %>>
						<%
							sql = "select * from engine_displacement";

								rs = stmt.executeQuery(sql);

								while (rs.next()) {
									out.print("<option value=" + rs.getInt("ed_id") + ">" + rs.getInt("ed_id") + " "
											+ rs.getString("ed_type") + "</option>");
								}
						%>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">트랜스미션</label>
				</div>
				<div class="col-75">
					<select id="trans" name="trans" <%if(rd_only){out.print("disabled");} %>>
						<%
							sql = "select * from transmission";

								rs = stmt.executeQuery(sql);

								while (rs.next()) {
									out.print("<option value=" + rs.getInt("t_id") + ">" + rs.getInt("t_id") + " "
											+ rs.getString("t_type") + "</option>");
								}
						%>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">세부모델</label>
				</div>
				<div class="col-75">
					<select id="detail" name="detail" <%if(rd_only){out.print("disabled");} %>>
						<%
							sql = "select maker_id, maker_name, model_id, model_name, detail_id, detail_name from (maker join model on maker_no = maker_id) join detailed_model D on D.mno = model_id ";

								rs = stmt.executeQuery(sql);

								while (rs.next()) {
									out.print("<option value=" + rs.getInt("detail_id") + ">" + rs.getString("maker_name") + " "
											+ rs.getString("model_name") + " " + rs.getString("detail_name") + "</option>");
								}
						%>
					</select>
				</div>
			</div>
			<%
				if(!rd_only){
					out.print("<div class=\"row\" style=\"justify-content: flex-end;\">");
					out.print("<input type=\"submit\" value=\"수정하기\">");
					out.print("</div>");
				}
			%>
		</form>
	</div>
</body>
<%
sql = "SELECT * FROM vehicle join order_info on vehicle_num = vnum WHERE order_num = " + idx;

rs = stmt.executeQuery(sql);

rs.next();
%>
<script type="text/javascript">
	document.info.fuel.value=<%=rs.getInt("fnum")%>;
	document.info.category.value=<%=rs.getInt("ctnum")%>;
	document.info.color.value=<%=rs.getInt("cnum")%>;
	document.info.trans.value=<%=rs.getInt("tnum")%>;
	document.info.engine.value=<%=rs.getInt("enum")%>;
	document.info.detail.value=<%=rs.getInt("dnum")%>;
</script>
<%
		rs.close();
		stmt.close();
		con.close();
	} catch (Exception e) {
		out.println("Oracle Database Connection Something Problem. <hr>");

		out.println(e.getMessage());

		e.printStackTrace();
	}
%>
</html>