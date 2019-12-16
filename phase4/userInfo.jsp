<%@page import="java.awt.print.PrinterIOException"%>
<%@ page language="java" import="java.text.*, java.sql.*"
	contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel=stylesheet href="CarInsert.css">
<title>회원 정보 수정</title>

<script type="text/javascript">
	function goBack() {
		location.href = "first.jsp";
	}
	function user_delete() {
		location.href = "userOut.jsp";
	}
</script>

<%
	// 세션정보 가져오기
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
				<label for="fname">아이디</label>
			</div>
			<div class="col-75">
				<input type="text" name="user_id" value="<%=session.getAttribute("sessionID")%>" disabled>
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">비밀번호</label>
			</div>
			<div class="col-75">
				<input type="text" name="user_pw" value="<%=rs.getString(2)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="country">이름</label>
			</div>
			<div class="col-75">
				<input type="text" name="name" value="<%=rs.getString(3)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">휴대폰 번호</label>
			</div>
			<div class="col-75">
				<input type="text" name="phone" value="<%=rs.getString(4)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="time">이메일</label>
			</div>
			<div class="col-75">
				<input type="text" name="email" value="<%=rs.getString(5)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">주소</label>
			</div>
			<div class="col-75">
				<input type="text" name="address" value="<%=rs.getString(6)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">성별</label>
			</div>
			<div class="col-75">
				<Select name="gender">
					<option value="M" selected>남자</option>
					<option value="F">여자</option>
					<option value="">선택안함</option>
				</Select>
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="country">생일</label>
			</div>
			<div class="col-75">
				<input type="date" name="birth" min="1920-01-01" max="2019-12-31" value="<%=rs.getDate(8)%>">
			</div>
		</div>
		<div class="user_info">
			<div class="col-25">
				<label for="name">직업</label>
			</div>
			<div class="col-75">
				<input type="text" name="job" value="<%=rs.getString(9)%>">
			</div>
		</div>
		<br>
		<div class="user_info" style="justify-content: flex-end;">
			<input type="submit" value="수정">
			<input type="button" value="뒤로 가기" onClick="goBack()">
			<input type="button" value="회원 탈퇴" onClick="user_delete()">
		</div>
		
			<%
				}
				rs.close();
			%>
	</form>

</body>
</html>