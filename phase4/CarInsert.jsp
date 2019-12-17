<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" import="java.util.*" %>
<html>
<head>
<meta charset="EUC-KR">
<link rel=stylesheet href="CarInsert.css">
</head>
<script type="text/javascript">
		function formCheck() {
			var vnum = document.insertForm.vnum.value;
			var mileage = document.insertForm.mileage.value;
			var price = document.insertForm.price.value;
			var model_year = document.insertForm.model_year.value;
			var fuel = document.insertForm.fuel.value;
			var category = document.insertForm.category.value;
			var color = document.insertForm.color.value;
			var engine = documnet.insertForm.engine.value;
			var trans = document.insertForm.trans.value;
			var detail = document.insertForm.detail.value;

			if (vnum == null || vnum == "") {
				alert('차량번호를 입력하세요');
				document.forms[0].vnum.focus();
				return false;
			}
			
			if (mileage == null || mileage == "") {
				alert('주행거리를 입력하세요');
				document.forms[0].mileage.focus();
				return false;
			}

			if (price == null || price == "") {
				alert('가격을 입력하세요');
				document.forms[0].start.focus();
				return false;
			}
			
			if( !(price > 5000000 && price < 200000000 ) ){
				alert('5백만원에서 2억원 사이의 금액을 입력해주세요');
				document.forms[0].start.focus();
				return false;
			}

			if (model_year == null || model_year == "") {
				alert('연식를 입력하세요');
				document.forms[0].model_year.focus();
				return false;
			}

			if (fuel == null || fuel == "") {
				alert('연료를 입력하세요');
				document.forms[0].fuel.focus();
				return false;
			}

			if (category == null || category == "") {
				alert('분류를 입력하세요');
				document.forms[0].category.focus();
				return false;
			}
			
			if (color == null || color == "") {
				alert('색상을 입력하세요');
				document.forms[0].color.focus();
				return false;
			}
			
			if (engine == null || engine == "") {
				alert('배기량을 입력하세요');
				document.forms[0].engine.focus();
				return false;
			}
			
			if (trans == null || trans == "") {
				alert('트랜스미션을 입력하세요');
				document.forms[0].trans.focus();
				return false;
			}
			
			if (detail == null || detail == "") {
				alert('세부모델을 입력하세요');
				document.forms[0].detail.focus();
				return false;
			}
		}
</script>
<%
request.setCharacterEncoding("euc-kr");

String driverName = "oracle.jdbc.driver.OracleDriver";

String url = "jdbc:oracle:thin:@localhost:1521:xe";

ResultSet rs = null;

Class.forName(driverName);

Connection con = DriverManager.getConnection(url, "ncnc", "ncnc");

Statement stmt = con.createStatement();

String sql;
%>
<body>
	<div class="container">
		<form name="insertForm" action="car_insert.jsp" method="post" onsubmit="formCheck();return false">
			<div class="row">
				<div class="col-25">
					<label for="fname">판매자</label>
				</div>
				<div class="col-75">
					<input type="text" id="seller" name="seller" placeholder="판매자">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="fname">차량 변호</label>
				</div>
				<div class="col-75">
					<input type="text" id="vnum" name="vnum" placeholder="차량번호">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">가격</label>
				</div>
				<div class="col-75">
					<input type="number" id="price" name="price" placeholder="가격">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">주행거리</label>
				</div>
				<div class="col-75">
					<input type="number" id="mileage" name="mileage" placeholder="주행거리" min=1000 max=1000000>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="country">연식</label>
				</div>
				<div class="col-75">
					<input type="date" id="model_year" name="model_year"
						min="1977-01-01" max="2019-12-31">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">연료</label>
				</div>
				<div class="col-75">
					<select id="fuel" name="fuel">
						<%
							sql = "select * from fuels";
							
							rs = stmt.executeQuery(sql);
							
							while (rs.next()){
								out.print("<option value=" + rs.getInt("fuel_id") + ">" + rs.getInt("fuel_id") + " " + rs.getString("fuel_type") + "</option>");
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
					<select id="category" name="category">
						<%
							sql = "select * from category";	
						
							rs = stmt.executeQuery(sql);
							
							while (rs.next()){
								out.print("<option value=" + rs.getInt("c_id") + ">" + rs.getInt("c_id") + " " + rs.getString("c_type") + "</option>");
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
					<select id="color" name="color">
						<%
							sql = "select * from colors";	
						
							rs = stmt.executeQuery(sql);
							
							while (rs.next()){
								out.print("<option value=" + rs.getInt("color_id") + ">" + rs.getInt("color_id") + " " + rs.getString("color_name") + "</option>");
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
					<select id="engine" name="engine">
						<%
							sql = "select * from engine_displacement";	
						
							rs = stmt.executeQuery(sql);
							
							while (rs.next()){
								out.print("<option value=" + rs.getInt("ed_id") + ">" + rs.getInt("ed_id") + " " + rs.getString("ed_type") + "</option>");
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
					<select id="trans" name="trans">
						<%
							sql = "select * from transmission";	
						
							rs = stmt.executeQuery(sql);
							
							while (rs.next()){
								out.print("<option value=" + rs.getInt("t_id") + ">" + rs.getInt("t_id") + " " + rs.getString("t_type") + "</option>");
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
					<select id="detail" name="detail">
						<%
							sql = "select maker_id, maker_name, model_id, model_name, detail_id, detail_name from (maker join model on maker_no = maker_id) join detailed_model D on D.mno = model_id ";	
						
							rs = stmt.executeQuery(sql);
							
							while (rs.next()){
								out.print("<option value=" + rs.getInt("detail_id") + ">" +rs.getString("maker_name") + " " + rs.getString("model_name") + " " + rs.getString("detail_name") + "</option>");
							}
						%>
					</select>
				</div>
			</div>
			<div class="row" style="justify-content: flex-end;">
				<input type="submit" value="만들기">
			</div>
		</form>
	</div>
</body>
</html>