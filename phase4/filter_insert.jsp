<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
</head>
<%
   request.setCharacterEncoding("euc-kr");
   String num = request.getParameter("report_num");
   String content = request.getParameter("report_content");

   try {
      if (num.trim().equals("") || content.trim().equals("")) {
         out.println("<script>alert('모든 항목을 기입해 입력해주세요');</script>");
      } else {
         String driverName = "oracle.jdbc.driver.OracleDriver";

         String url = "jdbc:oracle:thin:@localhost:1521:xe";

         ResultSet rs = null;

         Class.forName(driverName);

         Connection con = DriverManager.getConnection(url, "ncnc", "ncnc"); //url + id + pw
         
         con.setAutoCommit(false);

         //out.println("Oracle 데이터베이스 db에 성공적으로 접속했습니다");
         
         con.commit();

         String sql = "select * from filter where order_num = " + num +" for update";

         Statement stmt = con.createStatement();

         rs = stmt.executeQuery(sql);

         if (rs.next()) {
            con.rollback();
            out.println("<script>alert('이미 비공개 등록된 매물입니다.');</script>");
         } else {
            // 판매중인 매물에서 입력받은 ORDER_NUM이 존재하는지 확인.
            sql = "select * from order_info where buyer is null and Order_num = " + num + " for update";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
               sql = "insert into FILTER values (" + num + ")";

               // System.out.println(sql);

               int res = stmt.executeUpdate(sql);

               if (res == 0) {
                  con.rollback();
                  out.println("<script>alert('비정상적으로 처리 되었습니다. 다시 확인해주세요.');</script>");
               } else {
                  con.commit();
                  out.println("<script>alert('정상적으로 처리 되었습니다.')</script>");
               }
            } else {
               con.rollback();
               out.println("<script>alert('판매중인 매물에서 일치하는 매물이 존재하지않습니다.')</script>");
            }

         }
         rs.close();
         stmt.close();
         con.close();
      }
   } catch (Exception e) {

      out.println("Oracle 데이터베이스 db 접속에 문제가 있습니다. <hr>");

      out.println(e.getMessage());

      e.printStackTrace();
   } finally {
      out.print("<script>location.href='admin.jsp';</script>");
   }
%>
<body>

</body>
</html>