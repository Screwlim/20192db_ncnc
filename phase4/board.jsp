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

		String url = "jdbc:oracle:thin:@localhost:1600:xe";

		ResultSet rs = null;

		Class.forName(driverName);

		Connection con = DriverManager.getConnection(url, "nicar", "car");

		Statement stmt = con.createStatement();

		//car_info view update / create
		String sqlv = "create or replace view car_info as select order_num, maker_name, maker_id, model_id, model_name, d.detail_name,v.* " +
				"from (((vehicle v join blind_info b on b.vnum = v.vehicle_num) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join maker on maker_no = maker_id " +
				"where b.buyer is null";
		stmt.execute(sqlv);
%>
<script type="text/javascript">
         function goCreateboard(){
        	var x_ = (window.screen.width/2) - 395;
        	var y_ = (window.screen.height/2) - 260;
            if(!(window.open("CarInsert.jsp","childForm","width=790, height=520 , left="+x_+", top="+y_+", screenX="+x_+", screenY="+y_+", resizable = no, scrollbars = no, status = no"))){
               return false;
            }
         }
         function goAdmin(){
            location.href="admin.jsp";   
         }
          function goPurchase(idx, vnum, year, mileage, price, fnum, cnum, ctnum, _enum, tnum, dnum){
             location.href="CarPurchase.jsp?idx="+idx+"&vnum="+vnum+"&model_year="+year+"&mileage="+mileage+"&price="+price+"&fnum="+fnum+"&cnum="+cnum+"&ctnum="+ctnum+"&enum="+_enum+"&tnum="+tnum+"&dnum="+dnum;
         }
         function goDetail(idx){
        	 var x_ = (window.screen.width/2) - 410;
        	 var y_ = (window.screen.height/2) - 300;
             if(!(window.open("boardDetail.jsp?idx="+idx,"childForm","width=820, height=600 , left="+x_+", top="+y_+", screenX="+x_+", screenY="+y_+", resizable = no, scrollbars = no, status = no"))){
                return false;
             }
         }
         function orderDetail(idx){
        	 var x_ = (window.screen.width/2) - 410;
        	 var y_ = (window.screen.height/2) - 300;
             if(!(window.open("orderDetail.jsp?idx="+idx+"&rd_only=1","childForm","width=820, height=600 , left="+x_+", top="+y_+", screenX="+x_+", screenY="+y_+", resizable = no, scrollbars = no, status = no"))){
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
								String sql = "select order_num, Vnum from order_info where buyer = \'"
											+ session.getAttribute("sessionID") + "\' order by order_date desc";

									rs = stmt.executeQuery(sql);

									int i = 1;
									// 최근 구매이력 7개 출력
									if (rs.next()) {
										out.print("<b>&nbsp최근 구매내역</b><br>");
										out.print("<a href=\"#\" onclick=\"orderDetail(" + rs.getString("order_num") + ")\">" + i++ + ". "
												+ rs.getString("Vnum") + "</a></br>");
										while (rs.next()) {
											out.print("<a href=\"#\" onclick=\"orderDetail(" + rs.getString("order_num") + ")\">" + i++
													+ ". " + rs.getString("Vnum") + "</a></br>");
										}
									} else {
										out.print("<b>&nbsp추천 상품</b><br>");
										//성별, 나이대, 최근 많이 팔린 상품으로...ㅇ, 상위 3개 모델.
										sql = "select gender, extract(year from birth_date) as year from account where id = '"
												+ session.getAttribute("sessionID") + "'";
										System.out.println(sql);
										rs = stmt.executeQuery(sql);
										if (rs.next()) {
											String year = rs.getString("year");
											String gender = rs.getString("gender");
											if (!gender.equals("") && !year.equals("")) {
												//성별과 나이대에서 가장 많이 구매 색상과 모델 차량 출력
												/*
												if(gender.equals("M")){
													out.print("<b>&nbsp또래 남성고객이 가장 선호하는 색상과 모델</b><br>");
												}
												else if(gender.equals("F")){
													out.print("<b>&nbsp또래 여성고객이 가장 선호하는 색상과 모델</b><br>");
												}
												*/
												int a = Integer.parseInt(year);
												sql = "with model_to_order as "
														+ "(select order_num, model_id from (((order_info join account on buyer = id and gender = '"
														+ gender + "' and birth_date between to_date('" + Integer.toString(a - 5)
														+ "-01-01', 'yyyy-mm-dd') and to_date('" + Integer.toString(a + 5)
														+ "-01-01', 'yyyy-mm-dd')) join vehicle on vnum = vehicle_num) join detailed_model on dnum=detail_id) join model on mno = model_id), "
														+ "modelList as (select model_id, count(order_num) as m_count from model_to_order group by model_id), "
														+ "maxModel as (select model_id from modelList where m_count = (select max(m_count) from modelList)), "
														+ "colorList as (select cnum, count(order_num) as c_count from (order_info join vehicle on vehicle_num = vnum) join account on id = buyer "
														+ "where gender = '" + gender + "' and birth_date between to_date('"
														+ Integer.toString(a - 5) + "-01-01', 'yyyy-mm-dd') and to_date('"
														+ Integer.toString(a + 5) + "-01-01', 'yyyy-mm-dd') group by cnum), "
														+ "maxColor as (select cnum from colorList where c_count = (select max(c_count) from colorList)) "
														+ "select vnum, order_num from (((order_info join account on buyer = id ) join vehicle on vnum = vehicle_num) join detailed_model on dnum=detail_id) join model on mno = model_id where cnum in (select max(c_count) from colorList) and mno in (select * from maxModel)";

												System.out.println(sql);

												rs = stmt.executeQuery(sql);
												i = 1;
												boolean result = false;
												while (rs.next()) {
													result = true;
													out.print("<a href=\"#\" onclick=\"orderDetail(" + rs.getString("order_num") + ")\">"
															+ i++ + ". " + rs.getString("Vnum") + "</a></br>");
													if (i > 3)
														break;
												}
												
												if(result==false){
													out.print("추천 매물이 없습니다.</br>");
												}
											}
											

											out.print("<b>&nbsp최다 판매 모델</b><br>");
											//가장 잘 팔리는 세부모델
											sql = "with detailList as "
													+ "(select dnum, count(order_num) as d_count from (order_info join account on id = buyer) join vehicle on vnum = vehicle_num group by dnum), "
													+ "maxdetail as (select dnum from detailList where d_count = (select max(d_count) from detailList))"
													+ "select order_num, vnum from (blind_info join vehicle on vnum = vehicle_num) where dnum in (select * from maxdetail)";

											System.out.println(sql);

											rs = stmt.executeQuery(sql);
											i = 1;
											while (rs.next()) {
												out.print("<a href=\"#\" onclick=\"orderDetail(" + rs.getString("order_num") + ")\">"
														+ i++ + ". " + rs.getString("Vnum") + "</a></br>");
												if (i > 3)
													break;
											}
										}
									}
							%>
						</div>
					</div>
					<%
						sql = "select is_admin from account where id = \'" + session.getAttribute("sessionID") + "\'";

							rs = stmt.executeQuery(sql);

							boolean is_admin = false;

							if (rs.next())
								if (rs.getString("is_admin").equals("T"))
									is_admin = true;

							if (is_admin) {
								out.print("<button class=\"button\" onclick=\"goCreateboard()\">새 매물 등록</button>");
								out.print(
										"<button class=\"button\" onclick=\"goAdmin()\"> <img src=\"realreport.png\" style=\"height: 15px\">관리자 기능 </button>");
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
					<div class="search_condition">
						<form action="board.jsp" method="post" name="search_condition">
							차종 : <select id="category" name="category">
								<option value="where c.ctnum is not null ">Anything</option>
								<option value="where c.ctnum = '0' ">Light-Weight</option>
								<option value="where c.ctnum = '1' ">MidSize</option>
								<option value="where c.ctnum = '2' ">FullSize</option>
								<option value="where c.ctnum = '3' ">SUV</option>
								<option value="where c.ctnum = '4' ">Coupe</option>
							</select> 제조사 : <select id="maker" name="maker">
								<option value=" ">Anything</option>
								<option value="and c.maker_name = 'Hyundai' ">Hyundai</option>
								<option value="and c.maker_name = 'Kia' ">Kia</option>
								<option value="and c.maker_name = 'Chevrolet' ">Chevrolet</option>
								<option value="and c.maker_name = 'Ssangyong' ">SsangYong</option>
								<option value="and c.maker_name = 'Renault Samsung' ">Renault Samsung</option>
								<option value="and c.maker_name = 'Benz' ">Benz</option>
								<option value="and c.maker_name = 'BMW' ">BMW</option>
								<option value="and c.maker_name = 'Audi' ">Audi</option>
								<option value="and c.maker_name = 'Porsche' ">Porsche</option>
								<option value="and c.maker_name = 'Lamborghini' ">Lamborghini</option>
							</select> 연료 : <select id="fuel" name="fuel">d
								<option value=" ">Anything</option>
								<option value="and c.fnum='0' ">Gasoline</option>
								<option value="and c.fnum='1' ">Diesel</option>
								<option value="and c.fnum='2' ">LPG</option>
								<option value="and c.fnum='3' ">Electric</option>
								<option value="and c.fnum='4' ">Gasoline & Electric</option>
								<option value="and c.fnum='6' ">Gasoline & LPG</option>
							</select> 배기량 : <select id="ed" name="ed">
								<option value=" ">Anything</option>
								<option value=" and c.enum = '0' ">1500</option>
								<option value=" and c.enum = '1' ">2000</option>
								<option value=" and c.enum = '2' ">2500</option>
								<option value=" and c.enum = '3' ">3000</option>
								<option value=" and c.enum = '4' ">4000</option>
								<option value=" and c.enum = '5' ">5000</option>
							</select> </br>변속기 종류 : <select id="transmission" name="transmission">
								<option value=" ">Anything</option>
								<option value="and c.tnum ='Auto' ">Auto</option>
								<option value="and c.tnum ='Semi-Auto' ">Semi-Auto</option>
								<option value="and c.tnum ='Manual' ">Manual</option>
							</select> 색상 : <select id="color" name="color">
								<option value=" ">Anything</option>
								<option value="and c.cnum = '0' ">Black</option>
								<option value="and c.cnum = '1' ">White</option>
								<option value="and c.cnum = '2' ">Gray</option>
								<option value="and c.cnum = '3' ">Red</option>
								<option value="and c.cnum = '4' ">Blue</option>
								<option value="and c.cnum = '5' ">Black & Gray</option>
								<option value="and c.cnum = '6' ">White & Gray</option>
							</select> 가격대 : <input type="number" name="pricemin"> ~ <input
								type="number" name="pricemax">
							<button class="refresh_button" onclick="gotaxi()">검색</button>
						</form>
					</div>
					<div class="search_model">
						<form action="board.jsp" method="post" name="search_model">
							모델 : <input type="text" name="model">
							<button class="refresh_button" onclick="gotaxi()">검색</button>
						</form>
					</div>
				</div>
				<%
					try {
						String driverName = "oracle.jdbc.driver.OracleDriver";

						String url = "jdbc:oracle:thin:@localhost:1600:xe";

						ResultSet rs = null;

						Class.forName(driverName);

						Connection con = DriverManager.getConnection(url, "nicar", "car");

						String sql = "";

						String model = request.getParameter("model");
						if (model != null) {
							sql = "Select *\n"
									+ "from car_info c\nwhere c.model_name = '" + model + "' order by c.price";
						} else {
							String category = request.getParameter("category");
							if (category == null) {
								category = "where c.ctnum is not null ";
							}
							String maker = request.getParameter("maker");
							if (maker == null) {
								maker = "";
							}
							String fuel = request.getParameter("fuel");
							if (fuel == null) {
								fuel = "";
							}
							String transmission = request.getParameter("transmission");
							if (transmission == null) {
								transmission = "";
							}
							String ed = request.getParameter("ed");
							if (ed == null) {
								ed = "";
							}
							String color = request.getParameter("color");
							if (color == null) {
								color = "";
							}
							String pricemin = request.getParameter("pricemin");
							String pricemax = request.getParameter("pricemax");
							if (pricemin == null || pricemin == "") {
								pricemin = "0";
							}
							if (pricemax == null || pricemax == "") {
								pricemax = "9999999999";
							}
							String price = "and c.price > " + pricemin + " and c.price < " + pricemax;

							sql = "Select *\n"
									+ "from car_info c\n" + category + maker + fuel + transmission + color + ed + price
									+ " order by c.price";
						}
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
							if (row == 0) {
									out.println("해당 조건의 구매 가능한 매물이 없습니다.");
								}
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
							</div>
							<div class="join">
								<button class="join_button" onclick="goPurchase(<%=idx%>, '<%=rs.getString("vehicle_num") %>', '<%=rs.getString("model_year") %>', '<%=rs.getString("mileage") %>', '<%=rs.getString("price") %>', '<%=rs.getString("fnum") %>', '<%=rs.getString("cnum") %>', '<%=rs.getString("ctnum") %>', '<%=rs.getString("enum") %>', '<%=rs.getString("tnum") %>', '<%=rs.getString("dnum") %>')">
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