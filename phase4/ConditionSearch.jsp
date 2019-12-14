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
            + "                                             from ((((((((((VEHICLE v join BLIND_INFO b on v.vehicle_num = b.vnum) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join category c on v.ctnum = c.c_id)join transmission t on v.tnum = t.t_id)join engine_displacement e on v.enum = e.ed_id)join color clr on v.cnum  = clr.color_id) join colors clrs on clr.color_id = clrs.color_id)join fuel fu on v.fnum = fu.fuel_id)join fuels fus on fu.fuel_id = fus.fuel_id)join maker mk on m.maker_no = mk.maker_id \n"
            + "                                             where b.order_date is null";
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
          function gotaxi(){
        	  location.href="ConditionSearch.jsp";
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

                     if (is_admin){
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
					<div class="search">
						<form action="ConditionSearch.jsp" method="post" name="searchInfo">
							차종 : <select id="category" name="category">
								<option value="where c.c_type is not null ">Anything</option>
								<option value="where c.c_type = 'Light-Weight' ">Light-Weight</option>
								<option value="where c.c_type = 'MidSize' ">MidSize</option>
								<option value="where c.c_type = 'FullSize' ">FullSize</option>
								<option value="where c.c_type = 'SUV' ">SUV</option>
								<option value="where c.c_type = 'Coupe' ">Coupe</option>
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
							</select> 연료 : <select id="fuel" name="fuel">
								<option value=" ">Anything</option>
								<option value="and c.fuel_id='0' ">Gasoline</option>
								<option value="and c.fuel_id='1' ">Diesel</option>
								<option value="and c.fuel_id='2' ">LPG</option>
								<option value="and c.fuel_id='3' ">Electric</option>
								<option value="and c.fuel_id='4' ">Gasoline & Electric</option>
								<option value="and c.fuel_id='6' ">Gasoline & LPG</option>
							</select> 변속기 종류 : <select id="transmission" name="transmission">
								<option value=" ">Anything</option>
								<option value="and c.t_type ='Auto' ">Auto</option>
								<option value="and c.t_type ='Semi-Auto' ">Semi-Auto</option>
								<option value="and c.t_type ='Manual' ">Manual</option>
							</select> 색상 : <select id="color" name="color">
								<option value=" ">Anything</option>
								<option value="and c.color_id = '0' ">Black</option>
								<option value="and c.color_id = '1' ">White</option>
								<option value="and c.color_id = '2' ">Gray</option>
								<option value="and c.color_id = '3' ">Red</option>
								<option value="and c.color_id = '4' ">Blue</option>
								<option value="and c.color_id = '5' ">Black & Gray</option>
								<option value="and c.color_id = '6' ">White & Gray</option>
							</select> 배기량 : <select id="ed" name="ed">
								<option value=" ">Anything</option>
								<option value=" and c.ed_type = '1500' ">1500</option>
								<option value=" and c.ed_type = '2000' ">2000</option>
								<option value=" and c.ed_type = '2500' ">2500</option>
								<option value=" and c.ed_type = '3000' ">3000</option>
								<option value=" and c.ed_type = '4000' ">4000</option>
								<option value=" and c.ed_type = '5000' ">5000</option>
							</select> 가격대 : <input type="number" name="pricemin"> ~ <input
								type="number" name="pricemax">
							<button class="refresh_button" onclick="gotaxi()">검색</button>
						</form>
					</div>
					<div class="search">
						<form action="ConditionSearch.jsp" method="post" name="searchInfo">
							모델 : <input type="text" name="model">
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
					String sql = "";
					
                  String model = request.getParameter("model");
                  if(model != null){
                	  sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price, c.order_num, c.maker_name\n"
                              + "from car_info c\nwhere c.model_name = '" + model + "' order by c.price";
                  }else{
                	  String category = request.getParameter("category");
                      if(category == null){
                         category = "where c.c_type is not null ";
                      }
                      String maker = request.getParameter("maker");
                      if(maker == null){
                         maker = "";
                      }
                      String fuel = request.getParameter("fuel");
                      if(fuel == null){
                         fuel = "";
                      }
                      String transmission = request.getParameter("transmission");
                      if(transmission == null){
                         transmission = "";
                      }
                      String ed = request.getParameter("ed");
                      if(ed == null){
                         ed = "";
                      }
                      String color = request.getParameter("color");
                      if(color == null){
                         color = "";
                      }
                      String pricemin = request.getParameter("pricemin");
                      String pricemax = request.getParameter("pricemax");
                      if(pricemin == null || pricemin == ""){
                         pricemin = "0";
                      }
                      if(pricemax == null || pricemax == ""){
                         pricemax = "9999999999";
                      }
                      String price = "and c.price > " + pricemin + " and c.price < " + pricemax;
                      
                      
                      sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price, c.order_num, c.maker_name\n"
                            + "from car_info c\n" + category + maker + fuel + transmission + color + ed + price + " order by c.price";
                  }
                  
                  
                  
//                  out.println(sql);
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
                     if(row == 0){
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