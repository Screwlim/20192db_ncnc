<%@page import="java.awt.print.PrinterIOException"%>
<%@ page language="java" import="java.text.*, java.sql.*"
	contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel=stylesheet href="CarInsert.css">
<title>ȸ�� ���� ����</title>

<script type="text/javascript">
	function goBack() {
		location.href = "first.jsp";
	}
	function user_delete() {
		location.href = "userOut.jsp";
	}
</script>

<%
	// �������� ��������
	String driverName = "oracle.jdbc.driver.OracleDriver";

	String url = "jdbc:oracle:thin:@localhost:1600:xe";

	Class.forName(driverName);

	Connection con = DriverManager.getConnection(url, "nicar", "car");
	
	Statement stmt = con.createStatement();

	String sql = "SELECT * FROM ACCOUNT WHERE ID = \'" + session.getAttribute("sessionID") + "\'";

	ResultSet rs = stmt.executeQuery(sql);

	while (rs.next()) {
%>
</head>
<body>

	<form action="userinfo_mod.jsp" method="post">
		<div class="user_info">
			<div class="col-25">
				<label for="fname">���̵�</label>
			</div>
			<div class="col-75">
				<input type="text" name="user_id" value="<%=session.getAttribute("sessionID")%>" disabled>
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">��й�ȣ</label>
			</div>
			<div class="col-75">
				<input type="text" name="user_pw" value="<%=rs.getString(2)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="country">�̸�</label>
			</div>
			<div class="col-75">
				<input type="text" name="name" value="<%=rs.getString(3)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">�޴��� ��ȣ</label>
			</div>
			<div class="col-75">
				<input type="text" name="phone" value="<%=rs.getString(4)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="time">�̸���</label>
			</div>
			<div class="col-75">
				<input type="text" name="email" value="<%=rs.getString(5)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">�ּ�</label>
			</div>
			<div class="col-75">
				<input type="text" name="address" value="<%=rs.getString(6)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">����</label>
			</div>
			<div class="col-75">
				<Select name="gender">
					<option value="M" selected>����</option>
					<option value="F">����</option>
					<option value="">���þ���</option>
				</Select>
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="country">����</label>
			</div>
			<div class="col-75">
				<input type="date" name="birth" min="1920-01-01" max="2019-12-31" value="<%=rs.getDate(8)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">����</label>
			</div>
			<div class="col-75">
				<input type="text" name="job" value="<%=rs.getString(9)%>">
			</div>
		</div>
		<br>
		<div class="user_info" style="justify-content: flex-end;">
			<input type="submit" value="����">
			<input type="button" value="�ڷ� ����" onClick="goBack()">
			<input type="button" value="ȸ�� Ż��" onClick="user_delete()">
		</div>
		
			<%
				}
				rs.close();
			%>
	</form>

</body>
</html>