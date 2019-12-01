<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" import="java.text.DecimalFormat"%>
<html>
<head>
<link rel="stylesheet" href="boardstyle.css">
<%
	request.setCharacterEncoding("euc-kr");
	if (session.getAttribute("sessionID") == null) {
		response.sendRedirect("Login.jsp"); // �α��� �ȵ������� �α��� �������� �Űܰ� ���Ŀ� ùȭ������ �ٲ����!
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
          function goTaxi(){
             location.href="taxi.jsp";   
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
						<span> <img src="person.jpg"> <b>�� ����</b>
						</span>
					</div>
					<div id="mystatebody">
						<div style="margin-left: 10px">
							<%
								String sql = "select Vnum from order_info where buyer = \'" + session.getAttribute("sessionID")
											+ "\' order by order_date desc";

									rs = stmt.executeQuery(sql);

									out.print("<b>&nbsp�ֱ� ���ų���</b><br>");

									int i = 1;

									while (rs.next()) {
										out.print(i++ + ". " + rs.getString("Vnum") + "<br>");
										if (i > 7)
											break;
									}
							%>
						</div>
					</div>
					<button class="button" onclick="goCreateboard()">�� �Ź� ���</button>
					<%
						sql = "select is_admin from account where id = \'" + session.getAttribute("sessionID") + "\'";

							rs = stmt.executeQuery(sql);

							boolean is_admin = false;

							if (rs.next())
								if (rs.getString("is_admin").equals("T"))
									is_admin = true;

							if (is_admin)
								out.print(
										"<button class=\"button\" onclick=\"goAdmin()\"> <img src=\"realreport.png\" style=\"height: 15px\">������ ��� </button>");
							rs.close();
							stmt.close();
							con.close();
						} catch (Exception e) {
							out.println("Oracle Database Connection Something Problem. <hr>");

							out.println(e.getMessage());

							e.printStackTrace();
						}
					%>
					<button class="logout_button" onclick="goLogout()">�α׾ƿ�</button>
				</div>
			</div>
			<div class="main">
				<div class="header">
					<div class="menu">
						<div class=menu_list>
							<span>�Ź� ���</span>
						</div>
					</div>
					<div class="search">
						<form action="board.jsp" method="post" name="searchInfo">
							<select id="search_1" name="search_1">
								<option value=null></option>
								<option value="�����">�����</option>
								<option value="������">������</option>
							</select> <select id="search_2" name="search_2">
								<option value=null></option>
								<option value="����">����</option>
								<option value="�޼���">�޼���</option>
								<option value="�޼���">�޼���</option>
								<option value="����">����</option>
								<option value="�ϱ�">�ϱ�</option>
								<option value="����">����</option>
								<option value="������">������</option>
								<option value="�߱�">�߱�</option>
							</select>
							<button class="refresh_button" onclick="gotaxi()">�˻�</button>
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

						String search1 = request.getParameter("search_1");
						String search2 = request.getParameter("search_2");

						String sql = null;

						//out.print(search1);
						//out.print(search2);

						if (search1 == null || search2 == null)
							sql = "with vehicle_to_maker as (select * from ((((vehicle V JOIN blind_info O On V.Vehicle_num = O.Vnum and buyer is null) JOIN Detailed_model D ON V.Dnum=Detail_id) JOIN MODEL ON MODEL.Model_id = D.Mno) JOIN MAKER M ON M.Maker_id = MODEL.Maker_no)) SELECT order_num, maker_name, model_name, detail_name, price FROM vehicle_to_maker ORDER BY Order_num";
						else if (search1.equals("null") || search2.equals("null"))
							sql = "with vehicle_to_maker as (select * from ((((vehicle V JOIN blind_info O On V.Vehicle_num = O.Vnum and buyer is null) JOIN Detailed_model D ON V.Dnum=Detail_id) JOIN MODEL ON MODEL.Model_id = D.Mno) JOIN MAKER M ON M.Maker_id = MODEL.Maker_no)) SELECT order_num, maker_name, model_name, detail_name, price FROM vehicle_to_maker ORDER BY Order_num";
						else if (search1.equals("�����"))
							sql = "";
						else if (search1.equals("������"))
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
										out.print("<br>&nbsp&nbsp �Ź� ��ȣ : " + idx);
									%>
								</div>
								<div class="room model">
									<%
										out.print("&nbsp&nbsp&nbsp �� : " + rs.getString("maker_name") + " " + rs.getString("model_name") + " " + rs.getString("detail_name"));
									%>
								</div>
								<div class="room price">
									<%
										DecimalFormat dc = new DecimalFormat("###,###,###,###,###");
										out.print("&nbsp&nbsp&nbsp ���� : " + dc.format(Double.valueOf(rs.getString("price"))) + "��");
									%>
								</div>
							</div>
							<div class="join">
								<button class="join_button" onclick="goPurchase(<%=idx%>)">
									+ ����</button>
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