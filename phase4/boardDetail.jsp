<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<html>
    <head>
    	<link rel = stylesheet href = "boardDetail.css">
		<%
		String idx = request.getParameter("idx");
		try {
			String driverName = "oracle.jdbc.driver.OracleDriver"; 
	 
	        String url = "jdbc:oracle:thin:@localhost:1521:xe";
	 
	        ResultSet rs = null;
	        
	        Class.forName(driverName);
	 
	        Connection con = DriverManager.getConnection(url,"ncnc","ncnc");
	 
	        Statement stmt = con.createStatement();        
	 
	        String sql = "SELECT * FROM CAR_INFO WHERE order_num="+idx;
	        
	        rs = stmt.executeQuery(sql);
	        
	        rs.next();
	    %>		
    </head>

    <body>
        <div class = "entire">
            <div class = "room_state">
                <span>
                    ���� ���� ����
                </span>
            </div>

            <div class = "Vehicle_detail">
            	<div class = "ORDER_NUM">
                    &nbsp;�Ź� ��ȣ: <%out.println(rs.getString("ORDER_NUM")); %>
                </div>
                <div class = "vnum">
                    &nbsp;���� ��ȣ: <%out.println(rs.getString("VEHICLE_NUM")); %>
                </div>
                <div class = "vtype">
                    &nbsp;����: <%out.println(rs.getString("C_TYPE")); %>
                </div>
                <div class = "maker">
                    &nbsp;������: <%out.println(rs.getString("MAKER_NAME")); %>
                </div>
                <div class = "mname">
                    &nbsp;�𵨸�: <%out.println(rs.getString("MODEL_NAME") + " " + rs.getString("DETAIL_NAME")); %>
                </div>
                <div class = "myear">
                    &nbsp;����: <%out.println(rs.getString("MODEL_YEAR")); %>
                </div>
                <div class = "ttype">
                    &nbsp;�������: <%out.println(rs.getString("T_TYPE")); %>
                </div>
                <div class = "edtype">
                    &nbsp;��ⷮ: <%out.println(rs.getString("ED_TYPE")); %>
                </div>
                <div class = "fuel">
                    &nbsp;��� ����: <%out.println(rs.getString("FUEL_TYPE")); %>
                </div>
                <div class = "color">
                    &nbsp;����: <%out.println(rs.getString("COLOR_NAME")); %>
                </div>
                <div class = "price">
                    &nbsp;����: <%out.println(rs.getString("PRICE")); %>
                </div>
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