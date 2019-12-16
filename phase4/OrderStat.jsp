<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" import="java.text.DecimalFormat"%>
<html>
<head>
<link rel="stylesheet" href="OrderStat.css">
<script type="text/javascript">
function orderDetail(idx){
	 var x_ = (window.screen.width/2) - 410;
	 var y_ = (window.screen.height/2) - 300;
    if(!(window.open("boardDetail.jsp?idx="+idx+"&rd_only=1","childForm","width=820, height=600 , left="+x_+", top="+y_+", screenX="+x_+", screenY="+y_+", resizable = no, scrollbars = no, status = no"))){
       return false;
    }
}
</script>
<%
	request.setCharacterEncoding("euc-kr");
	if (session.getAttribute("sessionID") == null) {
		response.sendRedirect("Login.jsp"); // 로그인 안되있으면 로그인 페이지로 옮겨감 추후에 첫화면으로 바꿔야함!
	}
	try {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1600:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "nicar", "car");

		String sql = "select order_num, maker_name, maker_id, model_id, model_name, v.*, detail_name from (((vehicle v join order_info o on o.vnum = v.vehicle_num) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join maker on maker_no = maker_id where o.buyer is null";

		//out.println(sql);

		PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		rs = pstmt.executeQuery(sql);

		rs.last();

		int row = rs.getRow();
		rs.first();
%>
</head>
<body>
	<div class="board">
		<div class="room_box">
			<%
				for (int i = 1; i < row; i++) {
						rs.next();
						String idx = rs.getString("order_num");
			%>
			<div class="room">
				<div class="room_text" onclick="orderDetail(<%=idx%>)">
					<div class="room_tilte">
						<%
							out.print("<br>&nbsp&nbsp 매물 번호 : " + idx);
						%>
					</div>
					<div class="room model">
						<%
							out.print("&nbsp&nbsp&nbsp 모델 : " + rs.getString("maker_name") + " " + rs.getString("model_name")
											+ " " + rs.getString("detail_name"));
						%>
					</div>
					<div class="room price">
						<%
							DecimalFormat dc = new DecimalFormat("###,###,###,###,###");
									out.print("&nbsp&nbsp&nbsp 가격 : " + dc.format(Double.valueOf(rs.getString("price"))) + "원");
						%>
					</div>
					<div class="join"></div>
				</div>
			</div>
			<%	}
				} catch (Exception e) {
					out.println("Oracle Database Connection Something Problem. <hr>");

					out.println(e.getMessage());

					e.printStackTrace();
				}
			%>
		</div>
	</div>

</body>
</html>

