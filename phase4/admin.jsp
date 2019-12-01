<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<html>
<head>
<link rel="stylesheet" href="admin.css">
<link rel="stylesheet" href="board.css">
<%
	if (session.getAttribute("sessionID") == null) {
		response.sendRedirect("Login.jsp"); // �α��� �ȵ������� �α��� �������� �Űܰ�!
	}
	try {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1600:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "nicar", "car");

		Statement stmt = con.createStatement();
%>
<script type="text/javascript">
	function goCreateboard() {
		var x_ = (window.screen.width / 2) - 160;
		var y_ = (window.screen.height / 2) - 215;
		if (!(window.open("createboard.jsp", "childForm",
				"width=720, height=480 , left=" + x_ + ", top=" + y_
						+ ", screenX=" + x_ + ", screenY=" + y_
						+ ", resizable = no, scrollbars = no, status = no"))) {
			return false;
		}
		//    location.href="createboard.jsp";      
	}
	function goAdmin() {
		location.href = "admin.jsp";
	}
	function goLogout() {
		location.href = "Logout.jsp";
	}
	function goTaxi() {
		location.href = "taxi.jsp";
	}
	function checkValue() {
		if (!document.reportInfo.report_id.value) {
			alert('�Ű��� �Ź���ȣ�� �Է��ϼ���.');
			document.reportInfo.report_id.focus();
			return false;
		}
		if (!document.reportInfo.report_content.value) {
			alert('������ �Է��ϼ���.');
			document.reportInfo.report_caontent.focus();
			return false;
		}

	}
</script>
</head>

<body>
	<img id="img" src="3.jpg">
	<div class="up"></div>
	<div class="center">
		<div class="box">
			<div class="state">
				<img id="gotaxi" src="5.png" style="height: 100px"
					onclick="goTaxi()">
				<div id="user">
					<div id="mystate">
						<span> <img src="person.jpg"> �� ����
						</span>
					</div>
					<div id="mystatebody">
						<div style="margin-left: 10px"></div>
					</div>
					<button class="button1" onclick="goCreateboard()">�� �Ź� ���</button>
					<%
						String sql = "select is_admin from account where id = \'" + session.getAttribute("sessionID") + "\'";

							rs = stmt.executeQuery(sql);

							boolean is_admin = false;

							if (rs.next())
								if (rs.getString("is_admin").equals("T"))
									is_admin = true;

							if (is_admin)
								out.print(
										"<button class=\"button3\" onclick=\"goAdmin()\"> <img src=\"realreport.png\" style=\"height: 15px\">������ ��� </button>");
							rs.close();
							stmt.close();
							con.close();
						} catch (Exception e) {
							out.println("Oracle Database Connection Something Problem. <hr>");

							out.println(e.getMessage());

							e.printStackTrace();

						}
					%>
					<button class="button2" onclick="goLogout()">�α׾ƿ�</button>
				</div>
			</div>
			<div class="main">
				<div class="header">
					<div>
						<span><img src="realreport.png" style="height: 15px">�����ó��</span>
					</div>
				</div>
				<div align="center" class="board">
					<span style="float: left; margin: 50;">
						<form method="post" action="filter_insert.jsp" name="reportInfo"
							onsubmit="return checkValue()">
							<p style="align-self: center; color: antiquewhite">
								<input id="report_num" name="report_num" type="text"
									placeholder="����� ��� �Ź� ��ȣ" style="border-radius: 5px">
							</p>
							<p style="align-self: center; color: antiquewhite;">����</p>
							<p style="align-self: center; color: antiquewhite;">
								<textarea name="report_content" id="report_content" rows="10"
									cols="30" style="border-radius: 5px"> </textarea>
							</p>
							<button class="button4">
								���<img src="realreport.png" style="height: 18px">
							</button>
						</form>
					</span> <span style="float: right; margin: 50;">
						<form method="post" action="filter_delete.jsp" name="reportInfo"
							onsubmit="return checkValue()">
							<p style="align-self: center; color: antiquewhite">
								<input id="report_num" name="report_num" type="text"
									placeholder="����� ���� �Ź� ��ȣ" style="border-radius: 5px">
							</p>
							<p style="align-self: center; color: antiquewhite;">����</p>
							<p style="align-self: center; color: antiquewhite;">
								<textarea name="report_content" id="report_content" rows="10"
									cols="30" style="border-radius: 5px"> </textarea>
							</p>
							<button class="button4">
								����<img src="realreport.png" style="height: 18px">
							</button>
						</form>
					</span>
				</div>
			</div>
		</div>
	</div>
	<div class="down"></div>
</body>
</html>