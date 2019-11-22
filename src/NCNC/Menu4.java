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
					updateForSale();
					break;

				case 3:
					// [비공개 처리]
					blindForSale();
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
			
			sql = "select * from Order_info where buyer is null and 1"
					+ "Vnum = '" + orderVnum +"'";
			
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
		StringBuffer sb1 = new StringBuffer();
		
		boolean change = false;
		
		String vehicleNum = null;
		String model_year = null;
		int mileage = 0;
		int price = 0;
		int Fnum = 0;
		int Cnum = 0;
		int Ctnum = 0;
		int Enum = 0;
		int Tnum = 0;
		int Dnum = 0;
		
		String Stemp = null;
		int temp = 0;
		
		boolean exist = false;
		
		System.out.println("[등록 차량 수정]");
		
		//우선 차량 번호 받고, 있는지 조사한 후, 수정할 값을 받는다.
		
		try {
			while(true) {
				System.out.print("수정할 매물의 차량번호를 입력하세요 : ");
				vehicleNum = scan.nextLine();
				
				sql = "select * from order_info where buyer is null and vnum = '" + vehicleNum + "'";

				rs = stmt.executeQuery(sql);
				
				if(rs.next()) {
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
			
			rs.close();
			
			sql = "select * from vehicle where vehicle_num = '" + vehicleNum + "'";

			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				model_year = rs.getString(2);
				mileage = rs.getInt(3);
				price = rs.getInt(4);
				Fnum = rs.getInt(5);
				Cnum = rs.getInt(6);
				Ctnum = rs.getInt(7);
				Enum = rs.getInt(8);
				Tnum = rs.getInt(9);
				Dnum = rs.getInt(10);
			}
			
			System.out.println("차량의 수정할 정보를 입력하세요. (수정을 원치않으면, 공백을 입력)");
			
			sb1.append("update vehicle set ");
			
			boolean isFirst = true;
			
			while(true) {
				
				System.out.print("연식 (yyyy-mm-dd) (현재 : " + model_year +  ") : ");
				
				model_year = scan.nextLine().trim();
				
				if(model_year.equals(""))
					break;
								
				if (!model_year.matches("\\d{4}-\\d{2}-\\d{2}"))
				   System.out.println("형식에 맞춰 다시 입력하세요!");
				else
					break;
				
			}
			
			if(!model_year.equals("")) {
				change = true;
				isFirst = false;
				sb1.append("model_year = to_date('" + model_year + "', 'yyyy-mm-dd')");
			}
			
			
			
			System.out.print("주행거리 (현재 : " + mileage + ") : ");
			
			Stemp = scan.nextLine().trim();
			
			if(!Stemp.equals("")) {
				change = true;
				
				if(isFirst) {
					sb1.append("mileage = " + Integer.valueOf(Stemp));
					isFirst = false;
				}else {
					sb1.append(", mileage = " + Integer.valueOf(Stemp));
				}
			}
			
			
			System.out.print("가격 (현재 : " + price + "): ");
			
			Stemp = scan.nextLine().trim();
			
			if(!Stemp.equals("")) {
				change = true;
				
				if(isFirst) {
					sb1.append("price = " + Integer.valueOf(Stemp));
					isFirst = false;
				}else {
					sb1.append(", price = " + Integer.valueOf(Stemp));
				}
			}
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			sql = "select min(fuel_id), max(fuel_id) from fuels";
			
			rs = stmt.executeQuery(sql);
			
			int min = 0;
			int max = 0;
			
			while(rs.next()) {
				min = rs.getInt("min(fuel_id)");
				max = rs.getInt("max(fuel_id)");
			}
			
			sql = "select * from fuels";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("번호 : " + String.valueOf(rs.getInt("fuel_id")) + ", 종류 : " + rs.getString("fuel_type"));
			}
			
			while(true) {
				System.out.print("연료 번호를 입력하세요 (현재 : "+ Fnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("범위를 확인해주세요.");
				}
				
			}

			if(!Stemp.equals("")) {
				change = true;
				
				if(isFirst) {
					sb1.append("Fnum = " + Integer.parseInt(Stemp));
					isFirst = false;
				}else {
					sb1.append(", Fnum = " + Integer.parseInt(Stemp));
				}
			}
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql = "select min(color_id), max(color_id) from colors";
			
			rs = stmt.executeQuery(sql);
			
			min = 0;
			max = 0;
			
			while(rs.next()) {
				min = rs.getInt("min(color_id)");
				max = rs.getInt("max(color_id)");
			}
			
			sql = "select * from colors";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("번호 : " + String.valueOf(rs.getInt("color_id")) + ", 종류 : " + rs.getString("color_name"));
			}
			
			while(true) {
				System.out.print("색상을 입력하세요 (현재 : "+ Cnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("범위를 확인해주세요.");
				}
				
			}

			if(!Stemp.equals("")) {
				change = true;
				
				if(isFirst) {
					sb1.append("Cnum = " + Integer.parseInt(Stemp));
					isFirst = false;
				}else {
					sb1.append(", Cnum = " + Integer.parseInt(Stemp));
				}
				
			}
			
			
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql = "select min(c_id), max(c_id) from category";
			
			rs = stmt.executeQuery(sql);
			
			min = 0;
			max = 0;
			
			while(rs.next()) {
				min = rs.getInt("min(c_id)");
				max = rs.getInt("max(c_id)");
			}
			
			sql = "select * from category";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("번호 : " + String.valueOf(rs.getInt("c_id")) + ", 종류 : " + rs.getString("c_type"));
			}
			
			while(true) {
				System.out.print("카테고리 (현재 : "+ Ctnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("범위를 확인해주세요.");
				}
				
			}

			if(!Stemp.equals("")) {
				change = true;
				
				if(isFirst) {
					sb1.append("Ctnum = " + Integer.parseInt(Stemp));
					isFirst = false;
				}else {
					sb1.append(", Ctnum = " + Integer.parseInt(Stemp));
				}
			}
			
		
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql = "select min(ed_id), max(ed_id) from engine_displacement";
			
			rs = stmt.executeQuery(sql);
			
			min = 0;
			max = 0;
			
			while(rs.next()) {
				min = rs.getInt("min(ed_id)");
				max = rs.getInt("max(ed_id)");
			}
			
			sql = "select * from engine_displacement";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("번호 : " + String.valueOf(rs.getInt("ed_id")) + ", 종류 : " + rs.getString("ed_type"));
			}
			
			while(true) {
				System.out.print("배기량 (현재 : "+ Enum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("범위를 확인해주세요.");
				}
				
			}

			if(!Stemp.equals("")) {
				change = true;
				
				if(isFirst) {
					sb1.append("Enum = " + Integer.parseInt(Stemp));
					isFirst = false;
				}else {
					sb1.append(", Enum = " + Integer.parseInt(Stemp));
				}
			}
		
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			sql = "select min(t_id), max(t_id) from transmission";
			
			rs = stmt.executeQuery(sql);
			
			min = 0;
			max = 0;
			
			while(rs.next()) {
				min = rs.getInt("min(t_id)");
				max = rs.getInt("max(t_id)");
			}
			
			sql = "select * from transmission";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("번호 : " + String.valueOf(rs.getInt("t_id")) + ", 종류 : " + rs.getString("t_type"));
			}
			
			while(true) {
				System.out.print("트랜스미션 (현재 : "+ Tnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("범위를 확인해주세요.");
				}
				
			}

			if(!Stemp.equals("")) {
				change = true;
				
				if(isFirst) {
					sb1.append("Tnum = " + Integer.parseInt(Stemp));
					isFirst = false;
				}else {
					sb1.append(", Tnum = " + Integer.parseInt(Stemp));
				}	
			}
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			//메이커 뽑고, 모델 뽑고, 세부 모델 뽑아서 적용시키자..
			
			sql = "select min(maker_id), max(maker_id) from maker";
			
			rs = stmt.executeQuery(sql);
			
			min = 0;
			max = 0;
			
			while(rs.next()) {
				min = rs.getInt("min(maker_id)");
				max = rs.getInt("max(maker_id)");
			}
			
			sql = "select * from maker";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("번호 : " + String.valueOf(rs.getInt("maker_id")) + ", 종류 : " + rs.getString("maker_name"));
			}
			
			while(true) {
				System.out.print("제조사를 선택하세요 : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("범위를 확인해주세요.");
				}
				
			}
			
			boolean isSelect = false; 
			int maker = 0;
			
			if(!Stemp.equals("")) {
				isSelect = true;
				maker = Integer.parseInt(Stemp);
			}
			
			if(isSelect == true) {
				
				sql = "select min(model_id), max(model_id) from model where maker_no = " + String.valueOf(maker);
				
				rs = stmt.executeQuery(sql);
				
				min = 0;
				max = 0;
				
				while(rs.next()) {
					min = rs.getInt("min(model_id)");
					max = rs.getInt("max(model_id)");
				}
				
				sql = "select * from model where maker_no =" + String.valueOf(maker);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					System.out.println("번호 : " + String.valueOf(rs.getInt("model_id")) + ", 종류 : " + rs.getString("model_name"));
				}
				
				while(true) {
					System.out.print("모델을 선택하세요 : ");
					Stemp = scan.nextLine().trim();
					
					if(Stemp.equals(""))
						break;
					
					if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
						break;
					}
					else {
						System.out.println("범위를 확인해주세요.");
					}
					
				}
				
				isSelect = false;
				int model = 0;
				
				if(!Stemp.equals("")) {
					isSelect = true;
					model = Integer.parseInt(Stemp);
				}
				
				if(isSelect == true) {
					
					sql = "select min(detail_id), max(detail_id) from detailed_model where mno = " + String.valueOf(model);
					
					rs = stmt.executeQuery(sql);
					
					min = 0;
					max = 0;
					
					while(rs.next()) {
						min = rs.getInt("min(detail_id)");
						max = rs.getInt("max(detail_id)");
					}
					
					sql = "select * from detailed_model where mno =" + String.valueOf(model);
					
					rs = stmt.executeQuery(sql);
					
					while(rs.next()) {
						System.out.println("번호 : " + String.valueOf(rs.getInt("detail_id")) + ", 종류 : " + rs.getString("detail_name"));
					}
					
					while(true) {
						System.out.print("세부 모델을 선택하세요 : ");
						Stemp = scan.nextLine().trim();
						
						if(Stemp.equals(""))
							break;
						
						if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
							break;
						}
						else {
							System.out.println("범위를 확인해주세요.");
						}
						
					}
					
					if(!Stemp.equals("")) {
						change = true;
						
						if(isFirst) {
							sb1.append(", Dnum = " + Integer.parseInt(Stemp));
							isFirst = false;
						}else {
							sb1.append(", Dnum = " + Integer.parseInt(Stemp));
						}
						
					}
					
				}
				
			}
			 
			
			if(change == true) {
				sb1.append(" where vehicle_num = '" + vehicleNum +"'");
				
			//	System.out.println(sb1.toString());
				
				sql = sb1.toString();
				
				int res = stmt.executeUpdate(sql);
				
				if(res == 1) {
					System.out.println("정상적으로 처리 되었습니다.");
					Main.conn.commit();
				}
				else {
					System.out.println("다시 시도해 주세요!");
				}
				
			}
			else {
				System.out.println("갱신하지않고 종료합니다.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void blindForSale() {
		String sql = null;
		String Stemp = null;
		
		System.out.println("[비공개 처리]");
		
		try {
			
			while(true) {
				System.out.print("비공개 등록을 원하면 Y(y), 비공개 해제를 원하면 N(n)을 입력하세요. : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals("Y") || Stemp.equals("y")) {
					//비공개 등록

					while(true) {
						
						System.out.print("비공개 처리할 매물의 ORDER_NUM을 입력하세요! (원치않으면 공백을 입력하세요) : ");
						Stemp = scan.nextLine().trim();
						
						if(Stemp.equals("")) {
							System.out.println("매물 비공개를 종료합니다.");
							break;
						}
						else {
							if(!Stemp.matches("^[0-9]*$")) {
								System.out.println("숫자만 입력하세요!");
							}
							else {
								
								sql = "select * from filter where order_num = " + String.valueOf(Stemp);
								
								rs = stmt.executeQuery(sql);
								
								if(rs.next()) {
									System.out.println("이미 비공개 등록된 매물입니다.");
									break;
								}
								
								//판매중인 매물에서 입력받은 ORDER_NUM이 존재하는지 확인.
								sql = "select * from order_info where buyer is null and Order_num = " + String.valueOf(Stemp);
								
								rs = stmt.executeQuery(sql);
								
								if(rs.next()) {
									sql = "insert into FILTER values (" + String.valueOf(Stemp) + ")";
									
									//System.out.println(sql);
									
									int res = stmt.executeUpdate(sql);
									
									if ( res == 0 ) {
										System.out.println("비정상적으로 처리 되었습니다. 다시 확인해주세요");
										break;
									}
									else {
										System.out.println("정상적으로 처리 되었습니다.");
										Main.conn.commit();
										break;
									}
								}
								else {
									System.out.println("판매중인 매물에서 일치하는 매물이 존재하지않습니다.");
									break;
								}
								
							}
						}					
					}
					//비공개 등록 완료
					break;
					
				}
				else if(Stemp.equals("N") || Stemp.equals("n")) {
					//비공개 해제
					
					while(true) {
						
						System.out.print("비공개 처리 해제할 매물의 ORDER_NUM을 입력하세요! (원치않으면 공백을 입력하세요) : ");
						Stemp = scan.nextLine().trim();
						
						if(Stemp.equals("")) {
							System.out.println("매물 비공개 해제를 종료합니다.");
							break;
						}
						else {
							if(!Stemp.matches("^[0-9]*$")) {
								System.out.println("숫자만 입력하세요!");
							}
							else {
								
								sql = "select * from filter where order_num = " + String.valueOf(Stemp);
								
								rs = stmt.executeQuery(sql);
								
								if(rs.next()) {
									
									sql = "delete from FILTER where Order_num = " + String.valueOf(Stemp);
									
									int res = stmt.executeUpdate(sql);
									
									if ( res == 0 ) {
										System.out.println("비정상적으로 처리 되었습니다. 다시 확인해주세요");
										break;
									}
									else {
										System.out.println("정상적으로 처리 되었습니다.");
										Main.conn.commit();
										break;
									}
								}
								else {
									System.out.println("비공개 등록되지않은 매물입니다.");
									break;
									
								}
							}
						}					
					}
					//비공개 해제 완료
					break;
				}
				else {
					System.out.println("입력이 바르지 않습니다. 다시 입력해주세요.");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}