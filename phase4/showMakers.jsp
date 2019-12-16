<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<html>
    <head>
       <link rel = stylesheet href = "statDetail.css">
      <%
      String idx = request.getParameter("idx");
      try {
         String driverName = "oracle.jdbc.driver.OracleDriver";

         String url = "jdbc:oracle:thin:@localhost:1600:xe";

         ResultSet rs = null;

         Class.forName(driverName);

         Connection con = DriverManager.getConnection(url, "nicar", "car");

         Statement stmt = con.createStatement();       
    
         String sql = "SELECT Maker_Name AS MAKER, SUM(v.price) FROM ((((vehicle V JOIN order_info O On V.Vehicle_num = O.Vnum) JOIN Detailed_model D ON V.Dnum=Detail_id) JOIN MODEL ON MODEL.Model_id = D.Mno) JOIN MAKER M ON M.Maker_id = MODEL.Maker_no) WHERE buyer is not null GROUP BY M.Maker_Name ORDER BY M.Maker_Name ASC";
         
           rs = stmt.executeQuery(sql);
           
       %>      
    </head>

    <body>
        <div class = "entire">
            <div class = "room_state">
                <span>
                제조사별 매출
                </span>
            </div>

            <div class = "Vehicle_detail">
            <%
            	while(rs.next()){
            		out.print("<div class = \"maker_price\">");
            		out.print("&nbsp;" + rs.getString(1) + " : " + rs.getString(2) +"원");
            		out.print("</div>");
            		
            	}            
            %>
            </div>    
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