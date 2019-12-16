<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<html>
<head>
<link rel=stylesheet href="statDetail.css">
<%
      try {
         String driverName = "oracle.jdbc.driver.OracleDriver";

         String url = "jdbc:oracle:thin:@localhost:1600:xe";

         ResultSet rs = null;

         Class.forName(driverName);

         Connection con = DriverManager.getConnection(url, "nicar", "car");

         Statement stmt = con.createStatement();       
    
         String sql = "SELECT extract(year from order_date) as year, SUM(v.price) FROM VEHICLE V JOIN ORDER_INFO O ON v.vehicle_num = o.vnum where buyer is not null GROUP BY extract(year from order_date) ORDER BY extract(year from order_date) ASC";
         
         rs = stmt.executeQuery(sql);
           
       %>
</head>

<body>
	<div class="entire">
		<div class="room_state">
			<span> 연별 매출 </span>
		</div>

		<%
            	while(rs.next()){
            		out.print("<div class = \"year_price\">");
            		out.print("&nbsp;" + rs.getString(1) + "년: " + rs.getString(2) + "원");
            		out.print("</div>");
            	}
            %>

		<%
           con.close();
      }catch(Exception e){
               out.println("Oracle Database Connection Something Problem. <hr>");
       
                 out.println(e.getMessage());
 
                 e.printStackTrace();
      }
      finally{
      }
      %>
	
</body>
</html>
</html>