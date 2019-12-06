package NCNC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//거래 내역 기능
public class Menu3 {

   public static void main(){

      ResultSet rs = null;
      Statement stmt = null;

      System.out.println("[거래 내역 서비스 입니다.]");

      // 사용자로 화면(거래내역만 지원)
      if (Main.admin == false) {

         System.out.println("\n회원님의 거래 내역입니다.");


         showOrder();

      } else if (Main.admin) {

         int menuNum;

         Scanner scan = new Scanner(System.in);

         while (true) {
            // 관리자일 경우
            System.out.println("\n1. 모든 거래 내역 출력");
            System.out.println("2. 월별 매출액");
            System.out.println("3. 연도별 매출액");
            System.out.println("4. 제조사별 매출액");
            System.out.println("5. 돌아가기");
            System.out.println("\n메뉴를 입력하세요 : ");

            menuNum = scan.nextInt();

            if (menuNum == 1) {

               System.out.println("\n모든 거래 내역입니다.");
               showOrder();
            } else if (menuNum == 2) {
               showMonthsale();
            } else if (menuNum == 3) {
               showYearsale();
            } else if (menuNum == 4) {
               showMakersale();
            } else if (menuNum == 5) {

               System.out.println("거래 내역 서비스를 종료합니다.");

               break;

            } else {
               System.out.println("잘못된 입력입니다!");
            }
         }
      }
   }

   //거래 내역(sql1 : 회원 , sql2 : 관리자)
   public static void showOrder() {

      ResultSet rs = null;
      Statement stmt = null;
      int count = 0;

      String sql1 = "select * from ORDER_INFO where SELLER = '" + Main.id + "' OR BUYER = '" + Main.id + "'";
      String sql2 =  "select * from ORDER_INFO where buyer is not null";
      try {
         stmt = Main.conn.createStatement();
         
         if (Main.admin == false) {
            rs = stmt.executeQuery(sql1);
         }
         else if (Main.admin) {
            rs = stmt.executeQuery(sql2);
         }
         
         
      } catch (SQLException e) {
         e.printStackTrace();
      }

      try {

         while (rs.next()) {
            
            int orderNum = rs.getInt(1);
            String orderDate = rs.getString(2);
            String buyer = rs.getString(3);
            String seller = rs.getString(4);
            String vNum = rs.getString(5);

            System.out.println("주문번호 : " + orderNum + ", 주문날짜 = " + orderDate + ", 구매자 = " + buyer + ", 판매자 = "
                  + seller + ", 차량번호 = " + vNum);
            
            count++;
         }
         
         if(count == 0) {
            System.out.println("\n거래 내역이 없습니다!\n");
         }
         
         rs.close();

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   //월별 매출 내역
   public static void showMonthsale() {

      ResultSet rs = null;
      Statement stmt = null;

      try {
         
         //String sql = "SELECT SUBSTR(O.order_date, 4, 2), SUM(v.price)\r\n FROM VEHICLE V JOIN ORDER_INFO O ON v.vehicle_num = o.vnum GROUP BY SUBSTR(O.order_date, 4, 2)HAVING SUBSTR(O.order_date, 4, 2) IS NOT NULL ORDER BY SUBSTR(O.order_date, 4, 2) ASC";
         String sql = "select extract(month from order_date), sum(price) from vehicle v join order_info on Vnum = vehicle_num where buyer is not null group by extract(month from order_date) order by extract(month from order_date)";
         
         stmt = Main.conn.createStatement();
         rs = stmt.executeQuery(sql);

         System.out.println("[월별 매출액 입니다.]");
         while (rs.next()) {

            String Month = rs.getString(1);
            int totlaPrice = (int) rs.getLong(2);

            System.out.println("월 = " + Month + ", 매출 = " + totlaPrice + "원");
         }
         rs.close();
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   //연별 매출 내역
   public static void showYearsale() {

      ResultSet rs = null;
      Statement stmt = null;

      try {
         
         String sql = "SELECT SUBSTR(O.order_date, 1, 2), SUM(v.price)\r\n FROM VEHICLE V JOIN ORDER_INFO O ON v.vehicle_num = o.vnum GROUP BY SUBSTR(O.order_date, 1, 2)HAVING SUBSTR(O.order_date, 1, 2) IS NOT NULL ORDER BY SUBSTR(O.order_date, 1, 2) ASC";
         stmt = Main.conn.createStatement();
         rs = stmt.executeQuery(sql);

         System.out.println("[년별 매출액 입니다.]");
         while (rs.next()) {

            String Year = rs.getString(1);
            int totlaPrice = (int) rs.getLong(2);

            System.out.println("년 = 20" + Year + ", 매출 = " + totlaPrice + "원");
         }
         rs.close();
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   //제조사별 매출 내역
   public static void showMakersale() {

      ResultSet rs = null;
      Statement stmt = null;

      try {
         
         String sql = "SELECT Maker_Name AS MAKER, SUM(v.price) FROM ((((vehicle V JOIN order_info O On V.Vehicle_num = O.Vnum) JOIN Detailed_model D ON V.Dnum=Detail_id) JOIN MODEL ON MODEL.Model_id = D.Mno) JOIN MAKER M ON M.Maker_id = MODEL.Maker_no) WHERE buyer is not null GROUP BY M.Maker_Name ORDER BY M.Maker_Name ASC";
         stmt = Main.conn.createStatement();
         rs = stmt.executeQuery(sql);

         System.out.println("[제조사별 매출액 입니다.]");
         while (rs.next()) {

            String Maker = rs.getString(1);
            int totlaPrice = (int) rs.getLong(2);

            System.out.println("제조사 = " + Maker + ", 매출 = " + totlaPrice + "원");
         }
         rs.close();
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}