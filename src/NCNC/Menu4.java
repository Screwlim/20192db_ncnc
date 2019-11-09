package NCNC;

import java.sql.*;
import java.util.Scanner;

//거래 내역 기능
public class Menu4 {
	public static Scanner scan = null;
	
	public static ResultSet rs = null;
	public static Statement stmt = null;

	public static void main() {
		// TODO Auto-generated method stub

		int menuNum;

		scan = new Scanner(System.in);
		
		try {
			stmt = Main.conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
				System.out.print("\n메뉴를 입력하세요 : ");

				menuNum = scan.nextInt();
				scan.nextLine();
				
				switch (menuNum) {
				case 1:
					// [새 매물등록]
					insertForSale();
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
		int orderNum = 0;
		String orderID = null;
		String orderVnum = null;
		boolean exist = false;
		
		

		System.out.println("[새 매물등록]");
		
		
		try {
			sql = "select MAX(order_num) from order_info";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				orderNum = rs.getInt(1) + 1;
			}
			
			while(true) {
				System.out.print("매물 등록자의 ID를 입력하세요 : ");

				orderID = scan.nextLine();
				
				sql = "select * from Account where id = '" + orderID + "'";
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					exist = true;
				}
				
				if(exist == true) {
					System.out.println("유효한 ID 입니다");
					exist = false;
					break;
				}
				else {
					System.out.println("유효하지않은 ID 입니다");
				}
			}
			
			while(true) {
				System.out.print("매물의 차량번호를 입력하세요 : ");

				orderVnum = scan.nextLine();
				
				sql = "select * from vehicle where vehicle_num = '" + orderVnum + "'";
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					exist = true;
				}
				
				if(exist == true) {
					System.out.println("유효한 Vnum 입니다");
					exist = false;
					break;
				}
				else {
					System.out.println("유효하지않은 Vnum 입니다");
				}
			}
			
			sql = "select * from Order_info where Vnum = '" + orderVnum +"'";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				exist = true;
			}
			
			if ( exist == true ) {
				System.out.println("동일한 차량이 이미 등록되어있습니다.");
				return;
			}
			else {
				sql = "insert into Order_info(Order_num, seller, Vnum) values (" + orderNum + ", '" + orderID + "', '" + orderVnum + "')";
				
				int res = stmt.executeUpdate(sql);
				
				if(res == 0) {
					System.out.println("다시 시도해 주세요!");
				}
				else if (res == 1){
					System.out.println("매물이 정상적으로 등록되었습니다!");
					Main.conn.commit();
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void updateForSale() {
		String sql = null;
		String vehicleNum = null;
		String Stemp = null;
		int temp = 0;
		
		boolean exist = false;
		
		System.out.println("[등록 차량 수정]");
		
		/*
		 create table vehicle (
      Vehicle_num varchar2(8) primary key,
      Model_year date not null,
      Mileage Number not null,
      Price Number not null,
      Fnum Number not null,
      Cnum Number not null,
      Ctnum Number not null,
      Enum Number not null,
      Tnum Number not null,
      Dnum Number not null,
      Foreign key (Fnum) references FUEL(Fuel_id),
      Foreign key (Cnum) references Color(Color_id),
      Foreign key (Ctnum) references CATEGORY(C_id),
      Foreign key (Enum) references Engine_Displacement(Ed_id),
      Foreign key (Tnum) references TRANSMISSION(T_id),
      Foreign key (Dnum) references DETAILED_MODEL(Detail_id));
		 */
		
		
		//우선 차량 번호 받고, 있는지 조사한 후, 수정할 값을 받는다.
		
		try {
			while(true) {
				System.out.print("수정할 차량의 차량번호를 입력하세요 : ");
				vehicleNum = scan.nextLine();
				
				sql = "select * from vehicle where vehicle_num = '" + vehicleNum + "'";

				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					exist = true;
				}
				
				if(exist == true) {
					System.out.println("유효한 차량번호입니다.");
					break;
				}
				else {
					System.out.println("유효하지않은 차량번호입니다.");
				}
			}
			
			System.out.println("차량의 수정할 정보를 입력하세요. (수정을 원치않으면, 공백을 입력)");
			
			System.out.print("연식 (yyyy-mm-dd) : ");
			System.out.print("주행거리 : ");
			System.out.print("가격 : ");
			
			sql = "select * from fuels";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("번호 : " + String.valueOf(rs.getInt(0)) + ", 종류" + rs.getString(1));
			}

			System.out.print("연료 번호를 입력하세요 : ");
			Stemp = scan.nextLine().trim();
			
			if(Stemp.equals("")) {
				//암것도 안하고 넘어감
			}
			else {
				temp = Integer.parseInt(Stemp);
			}
			
			
			
			System.out.print(" : ");
			System.out.print(" : ");
			System.out.print(" : ");
			System.out.print(" : ");
			System.out.print(" : ");
			System.out.print(" : ");
			System.out.print(" : ");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

	public static void blindForSale() {
		System.out.println("[비공개 처리]");
	}
}