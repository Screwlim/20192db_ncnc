package NCNC;

import java.util.Scanner;

//�ŷ� ���� ���
public class Menu3 {

   public static void main(String[] args) {
      // TODO Auto-generated method stub

      int menuNum;
      
      Scanner scan = new Scanner(System.in);
        menuNum = scan.nextInt();
        
        
        System.out.println("[�ŷ� ���� ���� �Դϴ�.]");


        // ����ڷ� ȭ��(�ŷ������� ����)
        if (Main.admin == false) {
           
           System.out.println("\nȸ������ �ŷ� �����Դϴ�.");
                     
          }
        else if (Main.admin == true) {
           
           // �������� ���
            System.out.println("1. ��� �ŷ� ���� ���");
            System.out.println("2. ���� �����");
            System.out.println("3. �����纰 �����");
            System.out.println("4. �����纰 �����");           
            System.out.println("5. ���ư���");
            System.out.println("\n�޴��� �Է��ϼ��� : ");

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
                 System.out.println("�߸��� �Է��Դϴ�!");
           }
        }       
   }
}