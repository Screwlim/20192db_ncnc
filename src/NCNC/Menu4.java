package NCNC;

import java.sql.*;
import java.util.Scanner;

//거래 내역 기능
public class Menu4 {
	public static Scanner scan = null;

	public static void main() {
		// TODO Auto-generated method stub

		int menuNum;

		 scan = new Scanner(System.in);

		System.out.println("[관리자 기능입니다.]");

		// 사용자로 화면(거래내역만 지원)
		if (Main.admin == false) {
			System.out.println("잘못된 접근입니다.");
			return;
		} else if (Main.admin == true) {

			while (true) {
				// 관리자일 경우
				System.out.println("1. 새 매물등록");
				System.out.println("2. 등록 차량 수정");
				System.out.println("3. 비공개 처리");
				System.out.println("4. 돌아가기");
				System.out.println("\n메뉴를 입력하세요 : ");

				menuNum = scan.nextInt();

				switch (menuNum) {
				case 1:
					// [새 매물등록]

					break;

				case 2:
					// [등록 차량 수정]
					break;

				case 3:
					// [비공개 처리]
					break;

				case 4:
					return;
				default:
					System.out.println("잘못된 입력입니다!");
				}
			}
		}
	}

	public static void insertForSale() {
		String sql = null;
		int order_num;
		String orderID = null;
		String orderVnum = null;
		
		ResultSet rs = null;
		Statement stmt = null;

		/*
		 * create table Order_info( Order_num number primary key, Order_date date, Buyer
		 * VARCHAR2(20), Seller VARCHAR2(20), Vnum VARCHAR2(10), foreign key (Vnum)
		 * references VEHICLE(Vehicle_num), foreign key (Buyer) references ACCOUNT(ID),
		 * foreign key (Seller) references ACCOUNT(ID) );
		 */

		System.out.println("[새 매물등록]");
		
		try {
			stmt = Main.conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			sql = "select MAX(order_num) from order_info";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				order_num = rs.getInt(1) + 1;
			}
			
			System.out.println("매물 등록자의 ID를 입력하세요 : ");

			orderID = scan.nextLine();
			
			sql = "select * from Account where id = '" + orderID + "'";
			
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

	}

	public static void updateForSale() {
		System.out.println("[등록 차량 수정]");

	}

	public static void blindForSale() {
		System.out.println("[비공개 처리]");
	}
}