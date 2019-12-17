<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<html>
<head>
<link rel="stylesheet" href="admin.css">
<link rel="stylesheet" href="board.css">
<%
	if (session.getAttribute("sessionID") == null) {
		response.sendRedirect("Login.jsp"); // 로그인 안되있으면 로그인 페이지로 옮겨감!
	}
	try {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "ncnc", "ncnc");

		Statement stmt = con.createStatement();
%>
<script type="text/javascript">
	function goSalemonth() {
		var x_ = (window.screen.width / 2) - 165;
		var y_ = (window.screen.height / 2) - 225;
		if (!(window.open("showMonth.jsp", "childForm",
				"width=325, height=450 , left=" + x_ + ", top=" + y_
						+ ", screenX=" + x_ + ", screenY=" + y_
						+ ", resizable = no, scrollbars = no, status = no"))) {
			return false;
		}
	}
	function goSaleyear() {
		var x_ = (window.screen.width / 2) - 165;
		var y_ = (window.screen.height / 2) - 225;
		if (!(window.open("showYear.jsp", "childForm",
				"width=325, height=450 , left=" + x_ + ", top=" + y_
						+ ", screenX=" + x_ + ", screenY=" + y_
						+ ", resizable = no, scrollbars = no, status = no"))) {
			return false;
		}
	}
	function goSalemakers() {
		var x_ = (window.screen.width / 2) - 165;
		var y_ = (window.screen.height / 2) - 225;
		if (!(window.open("showMakers.jsp", "childForm",
				"width=325, height=450 , left=" + x_ + ", top=" + y_
						+ ", screenX=" + x_ + ", screenY=" + y_
						+ ", resizable = no, scrollbars = no, status = no"))) {
			return false;
		}
	}
	function goCreateboard() {
		var x_ = (window.screen.width / 2) - 395;
		var y_ = (window.screen.height / 2) - 260;
		if (!(window.open("CarInsert.jsp", "childForm",
				"width=790, height=520 , left=" + x_ + ", top=" + y_
						+ ", screenX=" + x_ + ", screenY=" + y_
						+ ", resizable = no, scrollbars = no, status = no"))) {
			return false;
		}
	}
	function goView() {
		var x_ = (window.screen.width / 2) - 360;
		var y_ = (window.screen.height / 2) - 245;
		if (!(window.open("OrderStat.jsp", "childForm",
				"width=820, height=600 , left=" + x_ + ", top=" + y_
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
	function goFirst() {
		location.href = "first.jsp";
	}
	function checkValue() {
		if (!document.reportInfo.report_id.value) {
			alert('신고할 매물번호를 입력하세요.');
			document.reportInfo.report_id.focus();
			return false;
		}
		if (!document.reportInfo.report_content.value) {
			alert('사유를 입력하세요.');
			document.reportInfo.report_caontent.focus();
			return false;
		}

	}
</script>
</head>

<body>
	<img id="img" src="background.PNG">
	<div class="up"></div>
	<div class="center">
		<div class="box">
			<div class="state">
				<img id="goFirst" src="N.png" style="height: 100px"
					onclick="goFirst()">
				<div id="user">
					<div id="mystate">
						<span> <img src="person.jpg"> 내 정보
						</span>
					</div>
					<div id="mystatebody">
						<div style="margin-left: 10px"></div>
					</div>
					<button class="button1" onclick="goCreateboard()">새 매물 등록</button>
					<%
						String sql = "select is_admin from account where id = \'" + session.getAttribute("sessionID") + "\'";

							rs = stmt.executeQuery(sql);

							boolean is_admin = false;

							if (rs.next())
								if (rs.getString("is_admin").equals("T"))
									is_admin = true;

							if (is_admin) {
								out.print("<button class=\"button3\" onclick=\"goAdmin()\"> <img src=\"realreport.png\" style=\"height: 15px\">비공개 처리 </button>");
								out.print("<button class=\"button3\" onclick=\"goView()\">거래 내역 조회 </button>");
								out.print("<button class=\"button3\" onclick=\"goSalemakers()\">제조사별 매출 조회 </button>");
								out.print("<button class=\"button3\" onclick=\"goSaleyear()\">연도별 매출 조회 </button>");
								out.print("<button class=\"button3\" onclick=\"goSalemonth()\">월별 매출 조회 </button>");
							}

							rs.close();
							stmt.close();
							con.close();
						} catch (Exception e) {
							out.println("Oracle Database Connection Something Problem. <hr>");

							out.println(e.getMessage());

							e.printStackTrace();

						}
					%>
					<button class="button2" onclick="goLogout()">로그아웃</button>
				</div>
			</div>
			<div class="main">
				<div class="header">
					<div>
						<span><img src="realreport.png" style="height: 15px">비공개처리</span>
					</div>
				</div>
				<div align="center" class="board">
					<span style="float: left; margin: 50;">
						<form method="post" action="filter_insert.jsp" name="reportInfo"
							onsubmit="return checkValue()">
							<p style="align-self: center; color: antiquewhite">
								<input id="report_num" name="report_num" type="text"
									placeholder="비공개 등록 매물 번호" style="border-radius: 5px">
							</p>
							<p style="align-self: center; color: antiquewhite;">사유</p>
							<p style="align-self: center; color: antiquewhite;">
								<textarea name="report_content" id="report_content" rows="10"
									cols="30" style="border-radius: 5px"> </textarea>
							</p>
							<button class="button4">
								등록<img src="realreport.png" style="height: 18px">
							</button>
						</form>
					</span> <span style="float: right; margin: 50;">
						<form method="post" action="filter_delete.jsp" name="reportInfo"
							onsubmit="return checkValue()">
							<p style="align-self: center; color: antiquewhite">
								<input id="report_num" name="report_num" type="text"
									placeholder="비공개 해제 매물 번호" style="border-radius: 5px">
							</p>
							<p style="align-self: center; color: antiquewhite;">사유</p>
							<p style="align-self: center; color: antiquewhite;">
								<textarea name="report_content" id="report_content" rows="10"
									cols="30" style="border-radius: 5px"> </textarea>
							</p>
							<button class="button4">
								해제<img src="realreport.png" style="height: 18px">
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