package NCNC;

import java.util.Scanner;

//거래 내역 기능
public class Menu3 {

   public static void main(String[] args) {
      // TODO Auto-generated method stub

      int menuNum;
      
      Scanner scan = new Scanner(System.in);
        menuNum = scan.nextInt();
        
        
        System.out.println("[거래 내역 서비스 입니다.]");


        // 사용자로 화면(거래내역만 지원)
        if (Main.admin == false) {
           
           System.out.println("\n회원님의 거래 내역입니다.");
                     
          }
        else if (Main.admin == true) {
           
           // 관리자일 경우
            System.out.println("1. 모든 거래 내역 출력");
            System.out.println("2. 월별 매출액");
            System.out.println("3. 연도사별 매출액");
            System.out.println("4. 제조사별 매출액");           
            System.out.println("5. 돌아가기");
            System.out.println("\n메뉴를 입력하세요 : ");

            menuNum = scan.nextInt();

            switch (menuNum) {
            case 1:
               break;
               
            case 2:
               break;
               
            case 3:
               break;
               
            case 4:
               break;
               
            case 5:
               
               
               break;
               
            default:
                 System.out.println("잘못된 입력입니다!");
           }
        }       
   }
}