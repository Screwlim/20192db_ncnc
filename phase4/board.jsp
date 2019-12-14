<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" import="java.text.DecimalFormat"%>
<html>
<head>
<link rel="stylesheet" href="boardstyle.css">
<%
	request.setCharacterEncoding("euc-kr");
	if (session.getAttribute("sessionID") == null) {
		response.sendRedirect("Login.jsp"); // 로그인 안되있으면 로그인 페이지로 옮겨감 추후에 첫화면으로 바꿔야함!
	}
	try {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "ncnc", "ncnc");

		Statement stmt = con.createStatement();
		
		//car_info view update / create
		String sqlv = "create or replace view car_info as Select distinct v.vehicle_num, b.order_num, v.model_year, c.c_type, m.model_name, d.detail_name, v.price , t.t_type, e.ed_type, clrs.color_name, fus.fuel_type, mk.maker_name, clr.color_id, fu.fuel_id\n"
				+ "															from ((((((((((VEHICLE v join BLIND_INFO b on v.vehicle_num = b.vnum) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join category c on v.ctnum = c.c_id)join transmission t on v.tnum = t.t_id)join engine_displacement e on v.enum = e.ed_id)join color clr on v.cnum  = clr.color_id) join colors clrs on clr.color_id = clrs.color_id)join fuel fu on v.fnum = fu.fuel_id)join fuels fus on fu.fuel_id = fus.fuel_id)join maker mk on m.maker_no = mk.maker_id \n"
				+ "															where b.order_date is null";
		stmt.execute(sqlv);
%>
<script type="text/javascript">
         function goCreateboard(){
        	var x_ = (window.screen.width/2) - 160;
        	var y_ = (window.screen.height/2) - 215;
            if(!(window.open("CarInsert.jsp","childForm","width=720, height=480 , left="+x_+", top="+y_+", screenX="+x_+", screenY="+y_+", resizable = no, scrollbars = no, status = no"))){
               return false;
            }
         }
         function goAdmin(){
            location.href="admin.jsp";   
         }
          function goPurchase(idx){
             location.href="CarPurchase.jsp?idx="+idx;
         }
         function goDetail(idx){
        	 var x_ = (window.screen.width/2) - 165;
        	 var y_ = (window.screen.height/2) - 225;
             if(!(window.open("boardDetail.jsp?idx="+idx,"childForm","width=325, height=450 , left="+x_+", top="+y_+", screenX="+x_+", screenY="+y_+", resizable = no, scrollbars = no, status = no"))){
                return false;
             }
         }
          function goLogout(){
             location.href="Logout.jsp";   
         }
          function goFirst(){
             location.href="first.jsp";   
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
					onclick="goFirst()">
				<div id="user">
					<div id="mystate">
						<span> <img src="person.jpg"> <b>내 정보</b>
						</span>
					</div>
					<div id="mystatebody">
						<div style="margin-left: 10px">
							<%
								String sql = "select Vnum from order_info where buyer = \'" + session.getAttribute("sessionID")
											+ "\' order by order_date desc";

									rs = stmt.executeQuery(sql);

									out.print("<b>&nbsp최근 구매내역</b><br>");

									int i = 1;
									// 최근 구매이력 7개 출력
									while (rs.next()) {
										out.print(i++ + ". " + rs.getString("Vnum") + "<br>");
										if (i > 7)
											break;
									}
							%>
						</div>
					</div>
					<button class="button" onclick="goCreateboard()">새 매물 등록</button>
					<%
						sql = "select is_admin from account where id = \'" + session.getAttribute("sessionID") + "\'";

							rs = stmt.executeQuery(sql);

							boolean is_admin = false;

							if (rs.next())
								if (rs.getString("is_admin").equals("T"))
									is_admin = true;

							if (is_admin)
								out.print(
										"<button class=\"button\" onclick=\"goAdmin()\"> <img src=\"realreport.png\" style=\"height: 15px\">관리자 기능 </button>");
							rs.close();
							stmt.close();
							con.close();
						} catch (Exception e) {
							out.println("Oracle Database Connection Something Problem. <hr>");

							out.println(e.getMessage());

							e.printStackTrace();
						}
					%>
					<button class="logout_button" onclick="goLogout()">로그아웃</button>
				</div>
			</div>
			<div class="main">
				<div class="header">
					<div class="menu">
						<div class=menu_list>
							<span>매물 목록</span>
						</div>
					</div>
					<div class="search">
						<form action="board.jsp" method="post" name="searchInfo">
							<select id="search_1" name="search_1">
								<option value=null></option>
								<option value="출발지">출발지</option>
								<option value="도착지">도착지</option>
							</select> 
							<select id="search_2" name="search_2">
								<option value=null></option>
								<option value="남구">남구</option>
								<option value="달서구">달서구</option>
								<option value="달성군">달성군</option>
								<option value="동구">동구</option>
								<option value="북구">북구</option>
								<option value="서구">서구</option>
								<option value="수성구">수성구</option>
								<option value="중구">중구</option>
							</select>
							<button class="refresh_button" onclick="gotaxi()">검색</button>
						</form>
					</div>
				</div>
				<%
					try {
						String driverName = "oracle.jdbc.driver.OracleDriver";

						String url = "jdbc:oracle:thin:@localhost:1521:xe";

						ResultSet rs = null;

						Class.forName(driverName);

						Connection con = DriverManager.getConnection(url, "ncnc", "ncnc");

						String search1 = request.getParameter("search_1");
						String search2 = request.getParameter("search_2");

						String sql = null;

						if (search1 == null || search2 == null)
							sql = "with vehicle_to_maker as (select * from ((((vehicle V JOIN blind_info O On V.Vehicle_num = O.Vnum and buyer is null) JOIN Detailed_model D ON V.Dnum=Detail_id) JOIN MODEL ON MODEL.Model_id = D.Mno) JOIN MAKER M ON M.Maker_id = MODEL.Maker_no)) SELECT order_num, maker_name, model_name, detail_name, price FROM vehicle_to_maker ORDER BY Order_num";
						else if (search1.equals("null") || search2.equals("null"))
							sql = "with vehicle_to_maker as (select * from ((((vehicle V JOIN blind_info O On V.Vehicle_num = O.Vnum and buyer is null) JOIN Detailed_model D ON V.Dnum=Detail_id) JOIN MODEL ON MODEL.Model_id = D.Mno) JOIN MAKER M ON M.Maker_id = MODEL.Maker_no)) SELECT order_num, maker_name, model_name, detail_name, price FROM vehicle_to_maker ORDER BY Order_num";
						else if (search1.equals("출발지"))
							sql = "";
						else if (search1.equals("도착지"))
							sql = "";

						PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);

						rs = pstmt.executeQuery(sql);

						rs.last();

						int row = rs.getRow();

						rs.first();
				%>
				<div class="board">
					<div class="room_box">
						<%
							for (int i = 1; i < row; i++) {
									rs.next();
									String idx = rs.getString("order_num");
						%>
						<div class="room">
							<div class="room_text" onclick="goDetail(<%=idx%>)">
								<div class="room_tilte">
									<%
										out.print("<br>&nbsp&nbsp 매물 번호 : " + idx);
									%>
								</div>
								<div class="room model">
									<%
										out.print("&nbsp&nbsp&nbsp 모델 : " + rs.getString("maker_name") + " " + rs.getString("model_name") + " " + rs.getString("detail_name"));
									%>
								</div>
								<div class="room price">
									<%
										DecimalFormat dc = new DecimalFormat("###,###,###,###,###");
										out.print("&nbsp&nbsp&nbsp 가격 : " + dc.format(Double.valueOf(rs.getString("price"))) + "원");
									%>
								</div>
							</div>
							<div class="join">
								<button class="join_button" onclick="goPurchase(<%=idx%>)">
									+ 구매</button>
							</div>
						</div>
						<%
							}
						%>

					</div>
				</div>
				<%
					} catch (Exception e) {
						out.println("Oracle Database Connection Something Problem. <hr>");

						out.println(e.getMessage());

						e.printStackTrace();
					}
				%>
			</div>
		</div>
	</div>
	<div class="down"></div>
</body>
</html>