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
   String vnum = request.getParameter("vnum");
   String mileage = request.getParameter("mileage");
   String  price = request.getParameter("price");
   String  model_year = request.getParameter("model_year");
   String  fuel = request.getParameter("fuel");
   String  category = request.getParameter("category");
   String  color = request.getParameter("color");
   String  engine = request.getParameter("engine");
   String  trans = request.getParameter("trans");
   String  detail = request.getParameter("detail");

   try {
      if (vnum.trim().equals("") || price.trim().equals("") || model_year.trim().equals("") || fuel.trim().equals("") || category.trim().equals("") || color.trim().equals("") || engine.trim().equals("") || trans.trim().equals("") ||  detail.trim().equals("")) {
         out.println("<script>alert('��� �׸��� ������ �Է����ּ���');</script>");
      } 
      else {
         String driverName = "oracle.jdbc.driver.OracleDriver";

         String url = "jdbc:oracle:thin:@localhost:1521:xe";

         ResultSet rs = null;

         Class.forName(driverName);

         Connection con = DriverManager.getConnection(url, "ncnc", "ncnc");
         
         Statement stmt = con.createStatement();

         //out.println("Oracle �����ͺ��̽� db�� ���������� �����߽��ϴ�");
         
         String sql = "select is_admin from account where id= '" + session.getAttribute("sessionID") + "'";
         
         rs = stmt.executeQuery(sql);
         
         if(rs.next()){

            if(rs.getString("is_admin").equals("T")){
               sql = "update vehicle set model_year = to_date('" + model_year + "', 'yyyy-mm-dd'), mileage = " + mileage + ", price = " + price + ", fnum = " + fuel + ", cnum = " + color + ", ctnum = " + category + ", enum= " + engine + ", tnum= " + trans + ", dnum= " + detail + " where vehicle_num ='" + vnum+ "'";
               
               System.out.println(sql);
               
               int res = stmt.executeUpdate(sql);
               
               if (res == 0) {
                  out.println("<script>alert('������������ ó�� �Ǿ����ϴ�. �ٽ� Ȯ�����ּ���.');</script>");
               } 
               else {
                  out.println("<script>alert('���������� ó�� �Ǿ����ϴ�.');</script>");
               }               
            }
            else{
               out.println("<script>alert('�����ڸ� ���������մϴ�');</script>");
            }
         }
         rs.close();
         stmt.close();
         con.close();
      }
   } catch (Exception e) {

      out.println("Oracle �����ͺ��̽� db ���ӿ� ������ �ֽ��ϴ�. <hr>");

      out.println(e.getMessage());

      e.printStackTrace();
   } finally {
      //out.print("<script>location.href='CarInsert.jsp';</script>");
   }
%>
<body>
<script type="text/javascript">
   self.close();
</script>

</body>
</html>