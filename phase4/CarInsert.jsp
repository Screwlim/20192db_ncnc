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
				alert('������ȣ�� �Է��ϼ���');
				document.forms[0].vnum.focus();
				return false;
			}
			
			if (mileage == null || mileage == "") {
				alert('����Ÿ��� �Է��ϼ���');
				document.forms[0].mileage.focus();
				return false;
			}

			if (price == null || price == "") {
				alert('������ �Է��ϼ���');
				document.forms[0].start.focus();
				return false;
			}
			
			if( !(price > 5000000 && price < 200000000 ) ){
				alert('5�鸸������ 2��� ������ �ݾ��� �Է����ּ���');
				document.forms[0].start.focus();
				return false;
			}

			if (model_year == null || model_year == "") {
				alert('���ĸ� �Է��ϼ���');
				document.forms[0].model_year.focus();
				return false;
			}

			if (fuel == null || fuel == "") {
				alert('���Ḧ �Է��ϼ���');
				document.forms[0].fuel.focus();
				return false;
			}

			if (category == null || category == "") {
				alert('�з��� �Է��ϼ���');
				document.forms[0].category.focus();
				return false;
			}
			
			if (color == null || color == "") {
				alert('������ �Է��ϼ���');
				document.forms[0].color.focus();
				return false;
			}
			
			if (engine == null || engine == "") {
				alert('��ⷮ�� �Է��ϼ���');
				document.forms[0].engine.focus();
				return false;
			}
			
			if (trans == null || trans == "") {
				alert('Ʈ�����̼��� �Է��ϼ���');
				document.forms[0].trans.focus();
				return false;
			}
			
			if (detail == null || detail == "") {
				alert('���θ��� �Է��ϼ���');
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
					<label for="fname">�Ǹ���</label>
				</div>
				<div class="col-75">
					<input type="text" id="seller" name="seller" placeholder="�Ǹ���">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="fname">���� ��ȣ</label>
				</div>
				<div class="col-75">
					<input type="text" id="vnum" name="vnum" placeholder="������ȣ">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">����</label>
				</div>
				<div class="col-75">
					<input type="number" id="price" name="price" placeholder="����">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">����Ÿ�</label>
				</div>
				<div class="col-75">
					<input type="number" id="mileage" name="mileage" placeholder="����Ÿ�" min=1000 max=1000000>
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="country">����</label>
				</div>
				<div class="col-75">
					<input type="date" id="model_year" name="model_year"
						min="1977-01-01" max="2019-12-31">
				</div>
			</div>
			<div class="row">
				<div class="col-25">
					<label for="name">����</label>
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
					<label for="time">����</label>
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
					<label for="name">����</label>
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
					<label for="name">��ⷮ</label>
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
					<label for="name">Ʈ�����̼�</label>
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
					<label for="name">���θ�</label>
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
				<input type="submit" value="�����">
			</div>
		</form>
	</div>
</body>
</html>