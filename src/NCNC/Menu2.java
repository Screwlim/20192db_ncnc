package NCNC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu2 {

	public static void main() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		int menuNum;

		Scanner scan = new Scanner(System.in);
		
		System.out.println("[차량 검색 서비스 입니다.]");
		
		//모든 차량의 세부정보가 포함되어있는 car_info뷰 생성
		String sqlv = "create or replace view car_info as Select distinct v.vehicle_num, v.model_year, c.c_type, m.model_name, d.detail_name, v.price , t.t_type, e.ed_type, clrs.color_name, fus.fuel_type\n" + 
				"												from ((((((((((VEHICLE v join BLIND_INFO b on v.vehicle_num = b.vnum) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join category c on v.ctnum = c.c_id)join transmission t on v.tnum = t.t_id)join engine_displacement e on v.enum = e.ed_id)join color clr on v.cnum  = clr.color_id) join colors clrs on clr.color_id = clrs.color_id)join fuel fu on v.fnum = fu.fuel_id)join fuels fus on fu.fuel_id = fus.fuel_id) \n" + 
				"												where b.order_date is null";
		
		try {
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sqlv);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//메뉴실행 
		while (true) {
			// 관리자일 경우
			System.out.println("\n1. 모든 매물 보기");
			System.out.println("2. 제조사별 검색");
			System.out.println("3. 조건 검색");
			System.out.println("4. 차량 모델 검색");
			System.out.println("5. 돌아가기");
			System.out.println("\n메뉴를 입력하세요 : ");

			menuNum = scan.nextInt();

			if (menuNum == 1) {
				//show_all_listed
				System.out.println("\n모든 매물 목록입니다.");
				
				show_all_listed();
			} else if (menuNum == 2) {
				//show_Maker_listed
				show_Maker_listed();
			} else if (menuNum == 3) {
				//show_Condition_listed
				show_Condition_listed();
			} else if (menuNum == 4) {
				//show_Model_listed
				show_Model_listed();
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
		
		Scanner scan = new Scanner(System.in);
		
		String sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price\n" + 
				"                                from car_info c\n" + 
				"                                order by c.price";
		
		try {
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("번호       차량번호       연식                         차종         모델명           가격");
		try {
			while(rs.next()) {
				String vnum = rs.getString(1);
				String Myear = rs.getString(2);
				String ctype = rs.getString(3);
				String modelname = rs.getString(4);
				String d_model = rs.getString(5);
				String price = rs.getString(6);
				
				count++;
				
				System.out.printf("%-3d %12s %17s %14s %10s %7s %10s\n", count, vnum, Myear, ctype ,modelname, d_model, price);
			}
			
			if(count == 0) {
				System.out.println("\n현재 구매 가능한 차량이 없습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//상세정보 보기 
		while(true) {
			System.out.println("상세히 보기를 원하시면 1, 메뉴로 돌아가려면 다른 숫자를 입력하세요. : ");
			int num = scan.nextInt();
			String Vnum = scan.nextLine();
			
			if(num == 1) {
				System.out.println("상세보기를 원하는 매물 번호를 입력하세요 : ");
				Vnum = scan.nextLine();
				show_selected(Vnum);
			}else {
				break;
			}
		}		
	}
	
	//메이커 별로 검색하여 보여줌 
	public static void show_Maker_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		int menuNum;
		Scanner scan = new Scanner(System.in);
		
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
		
		menuNum = scan.nextInt();
		String vv = scan.nextLine();
		menuNum -= 1; // 메뉴 번호와 maker_id와 맞추기 위해 조정함 
		//해당 maker검색
		String sql = "Select distinct v.vehicle_num, v.model_year, c.c_type, m.model_name, d.detail_name, v.price\n" + 
				"								from ((((VEHICLE v join BLIND_INFO b on v.vehicle_num = b.vnum) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join category c on v.ctnum = c.c_id) \n" + 
				"								where b.order_date is null \n" + 
				"                                and m.maker_no = " + menuNum + "\n" + 
				"								order by v.price";
		
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("번호       차량번호       연식                         차종           모델명             가격");
		try {
			count = 0;
			while(rs.next()) {
				String vnum = rs.getString(1);
				String Myear = rs.getString(2);
				String ctype = rs.getString(3);
				String modelname = rs.getString(4);
				String d_model = rs.getString(5);
				String price = rs.getString(6);
				
				count++;
				
				System.out.printf("%-3d %12s %17s %14s %10s %7s %10s\n", count, vnum, Myear, ctype ,modelname, d_model, price);
			}
			
			if(count == 0) {
				System.out.println("\n현재 구매 가능한 차량이 없습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//상세정보 보기 
		while (true) {
			System.out.println("상세히 보기를 원하시면 1, 메뉴로 돌아가려면 다른 숫자를 입력하세요. : ");
			int num = scan.nextInt();
			String Vnum = scan.nextLine();

			if (num == 1) {
				System.out.println("상세보기를 원하는 매물 번호를 입력하세요 : ");
				Vnum = scan.nextLine();
				show_selected(Vnum);
			} else {
				break;
			}
		}		
	}
	
	//조건별로 검색하여 보여줌
	public static void show_Condition_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int menuNum = 0;
		String temp;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("조건 검색 서비스입니다.");
		
		//차종 선택 
		System.out.println("차종을 선택하세요./n 1.	Light-Weight\n" + 
				"2.	MidSize\n" + 
				"3.	FullSize\n" + 
				"4.	SUV\n" + 
				"5.	Coupe\n"
				+ "6. Anything");
		String category = null;
		
		menuNum = scan.nextInt();
		temp = scan.nextLine();//buffer flush
		
		switch(menuNum) {
		case 1:
			category = "Light-Weight";
			break;
		case 2:
			category = "MidSize";
			break;
		case 3:
			category = "FullSize";
			break;
		case 4:
			category = "SUV";
			break;
		case 5:
			category = "Coupe";
			break;
		default: 
			category = "";
			break;
		}
		
		//제조사 선택
		String maker = null;
		
		System.out.println("제조사를 선택하세요.1. Hyundai\n" + 
				"2.	Kia\n" + 
				"3.	Chevrolet\n" + 
				"4.	Ssangyong\n" + 
				"5.	Renault Samsung\n" + 
				"6.	Benz\n" + 
				"7.	BMW\n" + 
				"8.	Audi\n" + 
				"9.	Porsche\n" + 
				"10. Lamborghini\n11. Anything");
		
		menuNum = scan.nextInt();
		temp = scan.nextLine();//buffer flush
		
		switch(menuNum) {
		case 1:
			maker = "Hyundai";
			break;
		case 2:
			maker = "Kia";
			break;
		case 3:
			maker = "Chevrolet";
			break;
		case 4:
			maker = "Ssangyong";
			break;
		case 5:
			maker = "Renault Samsung";
			break;
		case 6:
			maker = "Benz";
			break;
		case 7:
			maker = "BMW";
			break;
		case 8:
			maker = "Audi";
			break;
		case 9:
			maker = "Porsche";
			break;
		case 10:
			maker = "Lamborghini";
		default: 
			category = "";
			break;
		}
				
		System.out.println("사용 연료를 선택하여 주세요.");

	}
	
	//모델 이름으로 검색하여 보여줌
	public static void show_Model_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		int menuNum;
		String modelName;
		Scanner scan = new Scanner(System.in);		
				
		System.out.println("검색하려는 모델의 이름을 입력하세요. :");
		
		modelName = scan.nextLine();
		
		String sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price " + 
								"from car_info c " + 
								"where c.model_name = '" + modelName +"'";
		try {
			System.out.println(sql);
			
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("번호       차량번호       연식                         차종           모델명             가격");
		try {
			count = 0;
			while (rs.next()) {
				String vnum = rs.getString(1);
				String Myear = rs.getString(2);
				String ctype = rs.getString(3);
				String modelname = rs.getString(4);
				String d_model = rs.getString(5);
				String price = rs.getString(6);

				count++;

				System.out.printf("%-3d %12s %17s %14s %10s %7s %10s\n", count, vnum, Myear, ctype, modelname, d_model, price);
			}

			if (count == 0) {
				System.out.println("\n현재 구매 가능한 차량이 없습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 상세정보 보기
		while (true) {
			System.out.println("상세히 보기를 원하시면 1, 메뉴로 돌아가려면 다른 숫자를 입력하세요. : ");
			int num = scan.nextInt();
			String Vnum = scan.nextLine();

			if (num == 1) {
				System.out.println("상세보기를 원하는 매물 번호를 입력하세요 : ");
				Vnum = scan.nextLine();
				show_selected(Vnum);
			} else {
				break;
			}
		}

	}

	//선택한 매물에 대하여 상세정보를 보여줌 
	public static void show_selected(String vnum) {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		int menu;
		
		Scanner scan = new Scanner(System.in);		
		
		String model_year;
		String car_type;
		String model_name;
		String detail_name;
		String price;
		String t_type;
		String ed_type;
		
		//세부정보 출력
		String sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price , c.t_type, c.ed_type\n" + 
				"																from car_info c\n" + 
				"                                                                where c.vehicle_num = '" + vnum + "'";
		
		try {
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				vnum = rs.getString(1);
				model_year = rs.getString(2);
				car_type = rs.getString(3);
				model_name = rs.getString(4);
				detail_name = rs.getString(5);
				price = rs.getString(6);
				t_type = rs.getString(7);
				ed_type = rs.getString(8);
				
				System.out.println("선택하신 차량 "+ vnum + "의 정보입니다.");
				System.out.println("연식 : " + model_year);
				System.out.println("차종 : " + car_type);
				System.out.println("모델명 : " + model_name +" "+ detail_name);
				System.out.println("기어 종류 :" + t_type);
				System.out.println("배기량 : " + ed_type);
				System.out.println("가격 : " + price);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//색상정보 출력 
		sql = "select cs.color_name\n" + 
				"from (vehicle v join color c on v.cnum = c.color_id) join colors cs on c.color_id = cs.color_id\n" + 
				"where v.vehicle_num = '" + vnum + "'";
		
		try {
			rs = stmt.executeQuery(sql);
			System.out.print("색상 : ");
			while(rs.next()) {
				String color = rs.getString(1);
				System.out.print(color + " ");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//연료정보 출력
		sql = "select fs.fuel_type\n" + 
				"from (vehicle v join fuel f on v.fnum = f.fuel_id) join fuels fs on f.fuel_id = fs.fuel_id\n" + 
				"where v.vehicle_num = '" + vnum + "'";
		try {
			rs = stmt.executeQuery(sql);
			System.out.print("사용 연료 : ");
			while(rs.next()) {
				String fuel = rs.getString(1);
				System.out.print(fuel + " ");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\n차량 구매를 원하시면 1을 원하지 않으면 다른 숫자를 입력하세요. : ");
		menu = scan.nextInt();
		String v = scan.nextLine();
		if(menu == 1) {
			buycar(vnum);
		}
	}
	
	//차량을 구매함 
	public static void buycar(String vnum) {
		ResultSet rs = null;
		Statement stmt = null;
		
		//order_info update
		String sql = "update order_info\n" + 
				"set order_date = TO_DATE( '"+ "2019/11/22 03:00:00" + "', 'YYYY/MM/DD HH:MI:SS')\n"+
				"where order_info.vnum = '" +vnum + "'";
		System.out.println("sql :" + sql);
		try {
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql = "update order_info\n" + 
				"set buyer ='"+ Main.id+"' "+
				"where order_info.vnum = '" +vnum + "'";
		try {
			stmt = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("성공적으로 차량을 구매하였습니다.");
	}
	
	
	
}
