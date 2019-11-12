package NCNC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu2 {

	public static void main() {
		
		System.out.println("[차량 검색 서비스 입니다.]");
		
		int menuNum;

		Scanner scan = new Scanner(System.in);

		while (true) {
			// 관리자일 경우
			System.out.println("\n1. 모든 매물 보기");
			System.out.println("2. 제조사별 검색");
			System.out.println("3. 조건 검색");
			System.out.println("4. 차량 이름 검색");
			System.out.println("5. 돌아가기");
			System.out.println("\n메뉴를 입력하세요 : ");

			menuNum = scan.nextInt();

			if (menuNum == 1) {
				//show_all_listed
				System.out.println("\n모든 매물 목록입니다.");
				
				show_all_listed();
			} else if (menuNum == 2) {
				//show_Maker_listed
			} else if (menuNum == 3) {
				//show_Condition_listed
			} else if (menuNum == 4) {
				//show_Model_listed
			} else if (menuNum == 5) {

				System.out.println("차량 검색 서비스를 종료합니다.");

				break;

			} else {
				System.out.println("잘못된 입력입니다!");
			}
		}


	}
	
	
	//모든 판매가능한 매물을 보여줌
	public static void show_all_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		
		String sql = "Select v.vehicle_num, v.model_year, v.mileage, v.price\n" + 
				"from VEHICLE v, BLIND_INFO b\n" + 
				"where v.vehicle_num = b.vnum\n" + 
				"and b.order_date is null\n" + 
				"order by v.price";
		
		try {
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("번호       차량번호       연식                       마일리지      가격");
		try {
			while(rs.next()) {
				String vnum = rs.getString(1);
				String Myear = rs.getString(2);
				String mileage = rs.getString(3);
				String price = rs.getString(4);
				
				count++;
				
				System.out.printf("%-3d %12s %17s %15s %10s\n", count, vnum, Myear, mileage, price);
			}
			
			if(count == 0) {
				System.out.println("\n현재 구매 가능한 차량이 없습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//메이커 별로 검색하여 보여줌 
	public static void show_Maker_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		
		String makerlist = "select maker_name from MAKER";
		
		//maker 목록 불러오기
		try {
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(makerlist);
			
			System.out.println("검색하려는 제조사의 번호를 입력하십시오");
			
			while(rs.next()) {
				String makername = rs.getString(1);
				count++;
				
				System.out.println(count + ". " + makername);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//해당 maker검색 
		
		
		
		
		
		
	}
	
	
	//조건별로 검색하여 보여줌
	public static void show_Condition_listed() {
		
	}
	
	//이름으로 검색하여 보여줌
	public static void show_Model_listed() {
		
	}
	
	//선택한 매물에 대하여 상세정보를 보여줌 
	public static void show_selected(String vnum) {
		
	}
	
	
	//차량을 구매함 
	public static void buycar() {
		
	}
	
	
	
}
