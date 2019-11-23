package NCNC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu2 {
	static Scanner scan = new Scanner(System.in);

	public static void main() {
		ResultSet rs = null;
		Statement stmt = null;
		String temp;
		int menuNum;

		System.out.println("[차량 검색 서비스 입니다.]");

		// 모든 차량의 세부정보가 포함되어있는 car_info뷰 생성
		String sqlv = "create or replace view car_info as Select distinct v.vehicle_num, v.model_year, c.c_type, m.model_name, d.detail_name, v.price , t.t_type, e.ed_type, clrs.color_name, fus.fuel_type, mk.maker_name, clr.color_id, fu.fuel_id\n" + 
				"															from ((((((((((VEHICLE v join BLIND_INFO b on v.vehicle_num = b.vnum) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join category c on v.ctnum = c.c_id)join transmission t on v.tnum = t.t_id)join engine_displacement e on v.enum = e.ed_id)join color clr on v.cnum  = clr.color_id) join colors clrs on clr.color_id = clrs.color_id)join fuel fu on v.fnum = fu.fuel_id)join fuels fus on fu.fuel_id = fus.fuel_id)join maker mk on m.maker_no = mk.maker_id \n" + 
				"															where b.order_date is null";

		try {
			stmt = Main.conn.createStatement();

			stmt.executeQuery(sqlv);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 메뉴실행
		while (true) {
			System.out.println("\n1. 모든 매물 보기");
			System.out.println("2. 제조사별 검색");
			System.out.println("3. 조건 검색");
			System.out.println("4. 차량 모델 검색");
			System.out.println("5. 돌아가기");
			System.out.println("\n메뉴를 입력하세요 : ");

			menuNum = scan.nextInt();
			temp = scan.nextLine();

			if (menuNum == 1) {
				// show_all_listed
				System.out.println("\n모든 매물 목록입니다.");

				show_all_listed();
			} else if (menuNum == 2) {
				// show_Maker_listed
				show_Maker_listed();
			} else if (menuNum == 3) {
				// show_Condition_listed
				show_Condition_listed();
			} else if (menuNum == 4) {
				// show_Model_listed
				show_Model_listed();
			} else if (menuNum == 5) {
				System.out.println("차량 검색 서비스를 종료합니다.");

				break;

			} else {
				System.out.println("잘못된 입력입니다!");
			}
		}
	}

	// 모든 판매가능한 매물을 보여줌
	public static void show_all_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;

		String sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price\n"
				+ "                                from car_info c\n"
				+ "                                order by c.price";

		try {
			stmt = Main.conn.createStatement();

			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("번호       차량번호       연식                         차종         모델명           가격");
		try {
			while (rs.next()) {
				String vnum = rs.getString(1);
				String Myear = rs.getString(2);
				String ctype = rs.getString(3);
				String modelname = rs.getString(4);
				String d_model = rs.getString(5);
				String price = rs.getString(6);

				count++;

				System.out.printf("%-3d %12s %17s %14s %10s %7s %10s\n", count, vnum, Myear, ctype, modelname, d_model,
						price);
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
			System.out.println("상세히 보기를 원하시면 y, 메뉴로 돌아가려면 다른 아무 키나 입력하세요. : ");
			String Vnum = scan.nextLine();

			if (Vnum.equals("y")) {
				System.out.println("상세보기를 원하는 매물 번호를 입력하세요 : ");
				Vnum = scan.nextLine();
				show_selected(Vnum);
			} else {
				break;
			}
		}
	}

	// 메이커 별로 검색하여 보여줌
	public static void show_Maker_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		int menuNum;
		String temp;
		String makerlist = "select maker_name from MAKER";

		try {
			// maker 목록 불러오기
			stmt = Main.conn.createStatement();

			rs = stmt.executeQuery(makerlist);

			System.out.println("검색하려는 제조사의 번호를 입력하십시오");

			while (rs.next()) {
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
		temp = scan.nextLine();
		
		menuNum -= 1; // 메뉴 번호와 maker_id와 맞추기 위해 조정함

		// 해당 maker검색
		String sql = "Select distinct v.vehicle_num, v.model_year, c.c_type, m.model_name, d.detail_name, v.price\n"
				+ "	from ((((VEHICLE v join BLIND_INFO b on v.vehicle_num = b.vnum) join detailed_model d on v.dnum = d.detail_id) join model m on m.model_id = d.mno) join category c on v.ctnum = c.c_id) \n"
				+ "	where b.order_date is null \n"
				+ " and m.maker_no = " + menuNum + "\n"
				+ "	order by v.price";

		try {
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

				System.out.printf("%-3d %12s %17s %14s %10s %7s %10s\n", count, vnum, Myear, ctype, modelname, d_model,
						price);
			}

			if (count == 0) {
				System.out.println("\n해당 모델은 현재 구매 가능한 매물이 없습니다.");
			} else {
				// 상세정보 보기
				while (true) {
					System.out.println("상세히 보기를 원하시면 y, 메뉴로 돌아가려면 다른 아무 키나 입력하세요. : ");
					String Vnum = scan.nextLine();

					if (Vnum.equals("y")) {
						System.out.println("상세보기를 원하는 매물 번호를 입력하세요 : ");
						Vnum = scan.nextLine();
						show_selected(Vnum);
					} else {
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 조건별로 검색하여 보여줌
	public static void show_Condition_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int menuNum = 0;
		String temp;

		System.out.println("조건 검색 서비스입니다.");

		// 차종 선택
		System.out.println("차종을 선택하세요.\n1. Light-Weight\n" + "2. MidSize\n" + "3. FullSize\n" + "4. SUV\n"
				+ "5. Coupe\n" + "6. Anything");
		String category = "";

		menuNum = scan.nextInt();
		temp = scan.nextLine();// buffer flush

		switch (menuNum) {
		case 1:
			category = "where c.c_type = 'Light-Weight'\n";
			break;
		case 2:
			category = "where c.c_type = 'MidSize'\n";
			break;
		case 3:
			category = "where c.c_type = 'FullSize'\n";
			break;
		case 4:
			category = "where c.c_type = 'SUV'\n";
			break;
		case 5:
			category = "where c.c_type = 'Coupe'\n";
			break;
		default:
			category = "where c.c_type is not null\n";
			break;
		}

		
		
		// 제조사 선택
		String maker = "";

		System.out.println("제조사를 선택하세요.\n1. Hyundai\n2. Kia\n" + "3. Chevrolet\n" + "4. Ssangyong\n"
				+ "5. Renault Samsung\n" + "6. Benz\n" + "7. BMW\n" + "8. Audi\n" + "9. Porsche\n"
				+ "10. Lamborghini\n11. Anything");

		menuNum = scan.nextInt();
		temp = scan.nextLine();// buffer flush

		switch (menuNum) {
		case 1:
			maker = "and c.maker_name = 'Hyundai'\n";
			break;
		case 2:
			maker = "and c.maker_name = 'Kia'\n";
			break;
		case 3:
			maker = "and c.maker_name = 'Chevrolet'\n";
			break;
		case 4:
			maker = "and c.maker_name = 'Ssangyong'\n";
			break;
		case 5:
			maker = "and c.maker_name = 'Renault Samsung'\n";
			break;
		case 6:
			maker = "and c.maker_name = 'Benz'\n";
			break;
		case 7:
			maker = "and c.maker_name = 'BMW'\n";
			break;
		case 8:
			maker = "and c.maker_name = 'Audi'\n";
			break;
		case 9:
			maker = "and c.maker_name = 'Porsche'\n";
			break;
		case 10:
			maker = "and c.maker_name = 'Lamborghini'\n";
		default:
			maker = "";
			break;
		}

		System.out.println("사용 연료를 선택하여 주세요.\n1. Gasoline\n" + "2. Diesel\n" + "3. LPG\n" + "4. Electric\n"
				+ "5. Gasoline and Electric\n" + "6. Gasoline and LPG\n7. Anything");
		menuNum = scan.nextInt();
		temp = scan.nextLine();// buffer flush
		String fuel = "";
		switch (menuNum) {
		case 1:
			fuel = "and c.fuel_id='0'\n";
			break;
		case 2:
			fuel = "and c.fuel_id='1'\n";
			break;
		case 3:
			fuel = "and c.fuel_id='2'\n";
			break;
		case 4:
			fuel = "and c.fuel_id='3'\n";
			break;
		case 5:
			fuel = "and c.fuel_id='4'\n";
			break;
		case 6:
			fuel = "and c.fuel_id='6'\n";
			break;
		default:
			fuel = "";
			break;
		}
		
		//변속기 선택 
		System.out.println("변속기를 선택하여 주세요.\n1. Auto\n" + 
				"2. Manual\n" + 
				"3. Semi-Auto\n4. Anything");
		menuNum = scan.nextInt();
		temp = scan.nextLine();// buffer flush
		String transmission = "";
		switch (menuNum) {
		case 1:
			transmission = "and c.t_type ='Auto'\n";
			break;
		case 2:
			transmission = "and c.t_type ='Manual'\n";
			break;
		case 3:
			transmission = "and c.t_type ='Semi-Auto'\n";
			break;
		default:
			transmission = "";
			break;
		}
		
		int edmin=0;
		int edmax=1;
		System.out.println("배기량 조건 설정입니다. 다음 값들을 입력해주세요.(설정하지 않으려면 최소값에 -1을 입력하세요.)");
		String ed = "";
		//배기량 선택
		while(true) {
			System.out.print("최소값 :");
			edmin = scan.nextInt();
			if (edmin == -1) {
				ed = "";
				break;
			}
			
			System.out.print("최대값 :");
			edmax = scan.nextInt();
			
			if (edmin > edmax) {
				System.out.println("유효하지 않은 설정 값입니다. 다시 입력해 주세요.");
			}else {
				ed = "\nand c.ed_type > " + edmin + " and c.ed_type < " + edmax;
				break;
			}
		}
		
		
		//색상 선택
		System.out.println("차량의 색상을 선택하여 주세요.\n1. Black\n2. White\n3. Gray\n4. Red\n5. Blue\n6. Black & Gray\n7. White & Gray\n8. Anything");
		menuNum = scan.nextInt();
		temp = scan.nextLine();// buffer flush
		String color = "";
		switch (menuNum) {
		case 1:
			color = " and c.color_id='0'\n";
			break;
		case 2:
			color = " and c.color_id='1'\n";
			break;
		case 3:
			color = " and c.color_id='2'\n";
			break;
		case 4:
			color = " and c.color_id='3'\n";
			break;
		case 5:
			color = " and c.color_id='4'\n";
			break;
		case 6:
			color = " and c.color_id='5'\n";
			break;
		case 7:
			color = " and c.color_id='6'\n";
		default:
			color = "";
			break;
		}
		
		//가격 검색
		int pmin;
		int pmax;
		System.out.println("가격대 조건 설정입니다. 다음 값들을 입력해주세요.(설정하지 않으려면 최소값에 -1을 입력하세요.)");
		//배기량 선택
		String price;
		while(true) {
			System.out.print("최소값 :");
			pmin = scan.nextInt();
			if(pmin == -1) {
				price = "";
				break;
			}
			System.out.print("최대값 :");
			pmax = scan.nextInt();
			
			if (pmin > pmax) {
				System.out.println("유효하지 않은 설정 값입니다. 다시 입력해 주세요.");
			}else {
				price = "\nand c.price > " + pmin + " and c.price < " + pmax;
				break;
			}
		}
		
		temp = scan.nextLine();//flush
		
		String sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price\n" + 
				"from car_info c\n" + category + maker + fuel + ed + color + 
				"order by c.price";
				
		try {
			stmt  = Main.conn.createStatement();
			
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\n조건 검색결과 입니다.\n번호       차량번호       연식                         차종           모델명             가격");
		try {
			int count = 0;
			while (rs.next()) {
				String vnum = rs.getString(1);
				String Myear = rs.getString(2);
				String ctype = rs.getString(3);
				String modelname = rs.getString(4);
				String d_model = rs.getString(5);
				String car_price = rs.getString(6);

				count++;

				System.out.printf("%-3d %12s %17s %14s %10s %7s %10s\n", count, vnum, Myear, ctype, modelname, d_model,
						car_price);
			}

			if (count == 0) {
				System.out.println("\n해당 조건에 현재 구매 가능한 매물이 없습니다.");
			} else {
				// 상세정보 보기
				while (true) {
					System.out.println("상세히 보기를 원하시면 y, 메뉴로 돌아가려면 다른 아무 키나 입력하세요. : ");
					String Vnum = scan.nextLine();

					if (Vnum.equals("y")) {
						System.out.println("상세보기를 원하는 매물 번호를 입력하세요 : ");
						Vnum = scan.nextLine();
						show_selected(Vnum);
					} else {
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// 모델 이름으로 검색하여 보여줌
	public static void show_Model_listed() {
		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;
		int menuNum;
		String modelName;

		System.out.println("검색하려는 모델의 이름을 입력하세요. :");

		modelName = scan.nextLine();

		String sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price "
				+ "from car_info c " + "where c.model_name = '" + modelName + "'";
		try {
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

				System.out.printf("%-3d %12s %17s %14s %10s %7s %10s\n", count, vnum, Myear, ctype, modelname, d_model,
						price);
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
			System.out.println("상세히 보기를 원하시면 y, 메뉴로 돌아가려면 다른 아무 키나 입력하세요. : ");
			String Vnum = scan.nextLine();

			if (Vnum.equals("y")) {
				System.out.println("상세보기를 원하는 매물 번호를 입력하세요 : ");
				Vnum = scan.nextLine();
				show_selected(Vnum);
			} else {
				break;
			}
		}

	}

	// 선택한 매물에 대하여 상세정보를 보여줌
	public static void show_selected(String vnum) {
		ResultSet rs = null;
		Statement stmt = null;
		int menu = 0;

		String model_year;
		String car_type;
		String model_name;
		String detail_name;
		String price;
		String t_type;
		String ed_type;

		// 세부정보 출력
		String sql = "Select distinct c.vehicle_num, c.model_year, c.c_type, c.model_name, c.detail_name, c.price , c.t_type, c.ed_type\n"
				+ "from car_info c\n" + "where c.vehicle_num = '" + vnum + "'";

		try {
			stmt = Main.conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				menu++;
				vnum = rs.getString(1);
				model_year = rs.getString(2);
				car_type = rs.getString(3);
				model_name = rs.getString(4);
				detail_name = rs.getString(5);
				price = rs.getString(6);
				t_type = rs.getString(7);
				ed_type = rs.getString(8);

				System.out.println("선택하신 차량 " + vnum + "의 정보입니다.");
				System.out.println("연식 : " + model_year);
				System.out.println("차종 : " + car_type);
				System.out.println("모델명 : " + model_name + " " + detail_name);
				System.out.println("기어 종류 :" + t_type);
				System.out.println("배기량 : " + ed_type);
				System.out.println("가격 : " + price);
			}
			if (menu == 0) {
				System.out.println("유효하지 않은 차량 번호입니다.");
				return;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 색상정보 출력
		sql = "select cs.color_name\n"
				+ "from (vehicle v join color c on v.cnum = c.color_id) join colors cs on c.color_id = cs.color_id\n"
				+ "where v.vehicle_num = '" + vnum + "'";

		try {
			rs = stmt.executeQuery(sql);
			System.out.print("색상 : ");
			while (rs.next()) {
				String color = rs.getString(1);
				System.out.print(color + " ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 연료정보 출력
		sql = "select fs.fuel_type\n"
				+ "from (vehicle v join fuel f on v.fnum = f.fuel_id) join fuels fs on f.fuel_id = fs.fuel_id\n"
				+ "where v.vehicle_num = '" + vnum + "'";
		try {
			rs = stmt.executeQuery(sql);
			System.out.print("\n사용 연료 : ");
			while (rs.next()) {
				String fuel = rs.getString(1);
				System.out.print(fuel + " ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("\n차량 구매를 원하시면 y를 원하지 않으면 다른 아무 키나 입력하세요. : ");
		String v = scan.nextLine();
		if (v.equals("y")) {
			buycar(vnum);
		}

	}

	// 차량을 구매함
	public static void buycar(String vnum) {
		Statement stmt = null;

		// order_info update
		String sql = "update order_info\n" + "set order_date = TO_DATE( '" + "2019/11/23 03:00:00"
				+ "', 'YYYY/MM/DD HH:MI:SS')\n" + "where order_info.vnum = '" + vnum + "'";
		try {
			stmt = Main.conn.createStatement();

			stmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sql = "update order_info\n" + "set buyer ='" + Main.id + "' " + "where order_info.vnum = '" + vnum + "'";
		try {
			stmt = Main.conn.createStatement();

			stmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 구매후 정보 commit;
		try {
			Main.conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Main.id + "가 성공적으로 차량 " + vnum + "을 구매하였습니다.");
	}

}
