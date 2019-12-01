<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<html>
<head>
<meta charset="EUC-KR">
<link rel=stylesheet href="CarInsert.css">
</head>

<body>
	<div class="container">
		<form action="board_insert.jsp" method="post"
			onsubmit="return formCheck();">
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
							String driverName = "oracle.jdbc.driver.OracleDriver";

							String url = "jdbc:oracle:thin:@localhost:1600:xe";

							ResultSet rs = null;

							Class.forName(driverName);

							Connection con = DriverManager.getConnection(url, "nicar", "car");

							Statement stmt = con.createStatement();
							
							String sql = "select * from fuels";
							
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
					<select id="engine" name="engine">
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
					<label for="name">���� ��</label>
				</div>
				<div class="col-75">
					<select id="engine" name="engine">
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
			
			<div class="row" style="justify-content: flex-end;">
				<input type="submit" value="�����">
			</div>
		</form>
	</div>
	<script>
		function formCheck() {
			var title = document.forms[0].title.value;
			var start = document.forms[0].start.value;
			var dest = document.forms[0].dest.value;
			var time = document.forms[0].time.value;
			var cloth = document.forms[0].cloth.value;

			if (title == null || title == "") {
				alert('������ �Է��ϼ���');
				document.forms[0].title.focus();
				return false;
			}

			if (start == null || start == "") {
				alert('������� �Է��ϼ���');
				document.forms[0].start.focus();
				return false;
			}

			if (dest == null || dest == "") {
				alert('�������� �Է��ϼ���');
				document.forms[0].dest.focus();
				return false;
			}

			if (time == null || time == "") {
				alert('�ð��� �Է��ϼ���');
				document.forms[0].time.focus();
				return false;
			}

			if (time.match(/^(\d+)[:](\d+)$/ig) == null) {
				alert('�ð�(00:00)�� �Է��ϼ���');
				document.forms[0].time.focus();
				return false;
			}

			if (cloth == null || cloth == "") {
				alert('�λ����Ǹ� �Է��ϼ���');
				document.forms[0].cloth.focus();
				return false;
			}

		}
	</script>
</body>
</html>