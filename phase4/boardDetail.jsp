<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<html>
    <head>
    	<link rel = stylesheet href = "boardDetail.css">
		<%
		String idx = request.getParameter("idx");
		try {
			String driverName = "oracle.jdbc.driver.OracleDriver"; 
	 
	        String url = "jdbc:oracle:thin:@localhost:1521:root";
	 
	        ResultSet rs = null;
	        
	        Class.forName(driverName);
	 
	        Connection con = DriverManager.getConnection(url,"BOARD","board");
	 
	        Statement stmt = con.createStatement();        
	 
	        String sql = "SELECT * FROM BOARD WHERE IDX="+idx;
	        
	        rs = stmt.executeQuery(sql);
	        
	        rs.next();
	    %>		
    </head>

    <body>
        <div class = "entire">
            <div class = "room_state">
                <span>
                    �� ����
                </span>
            </div>

            <div class = "detail">
                <div class = "room_title">
                    &nbsp;������: <%out.println(rs.getString("TITLE")); %>
                </div>
                <div class = "room_start">
                    &nbsp;�����: <%out.println(rs.getString("START_")); %>
                </div>
                <div class = "room_dest">
                    &nbsp;������: <%out.println(rs.getString("DEST_")); %>
                </div>
                <div class = "room_time">
                    &nbsp;�ð�: <%out.println(rs.getString("TIME_")); %>
                </div>
                <div class = "room_cloth">
                    &nbsp;�λ����� �� ���: <%out.println(rs.getString("CLOTH_")); %>
                </div>
            </div>    
            <div class = "participant_state">
                <span>������</span>
            </div>
            <div class = "participant">
                <div>
                <%out.println("&nbsp"+rs.getString("WRITER")); %>
                </div>
                <div>
                <br>
                <%
                if(rs.getString("PART1")==null)
                	out.println("&nbsp����");
                else
                	out.println("&nbsp"+rs.getString("PART1"));
                %>
                </div>
                <div>
                <br>
                <%
                if(rs.getString("PART2")==null)
                	out.println("&nbsp����");
                else
                	out.println("&nbsp"+rs.getString("PART2"));
                %>
                </div>
                <div>
                <br>
                <%
                if(rs.getString("PART3")==null)
                	out.println("&nbsp����");
                else
                	out.println("&nbsp"+rs.getString("PART3"));
                %>
                </div>
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