package NCNC;

import java.sql.*;
import java.util.Scanner;

//�ŷ� ���� ���
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

		System.out.println("[������ ����Դϴ�.]");

		// ����ڷ� ȭ��(�ŷ������� ����)
		if (Main.admin == false) {
			System.out.println("�߸��� �����Դϴ�.");
			return;
		} else if (Main.admin == true) {

			while (true) {
				// �������� ���
				System.out.println("1. �� �Ź����");
				System.out.println("2. ��� ���� ����");
				System.out.println("3. ����� ó��");
				System.out.println("4. ���ư���");
				System.out.print("\n�޴��� �Է��ϼ��� : ");

				menuNum = scan.nextInt();
				scan.nextLine();
				
				switch (menuNum) {
				case 1:
					// [�� �Ź����]
					insertForSale();
					break;

				case 2:
					// [��� ���� ����]
					updateForSale();
					break;

				case 3:
					// [����� ó��]
					blindForSale();
					break;

				case 4:
					return;
				default:
					System.out.println("�߸��� �Է��Դϴ�!");
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
		
		

		System.out.println("[�� �Ź����]");
		
		
		try {
			sql = "select MAX(order_num) from order_info";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				orderNum = rs.getInt(1) + 1;
			}
			
			while(true) {
				System.out.print("�Ź� ������� ID�� �Է��ϼ��� : ");

				orderID = scan.nextLine();
				
				sql = "select * from Account where id = '" + orderID + "'";
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					exist = true;
				}
				
				if(exist == true) {
					System.out.println("��ȿ�� ID �Դϴ�");
					exist = false;
					break;
				}
				else {
					System.out.println("��ȿ�������� ID �Դϴ�");
				}
			}
			
			while(true) {
				System.out.print("�Ź��� ������ȣ�� �Է��ϼ��� : ");

				orderVnum = scan.nextLine();
				
				sql = "select * from vehicle where vehicle_num = '" + orderVnum + "'";
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					exist = true;
				}
				
				if(exist == true) {
					System.out.println("��ȿ�� Vnum �Դϴ�");
					exist = false;
					break;
				}
				else {
					System.out.println("��ȿ�������� Vnum �Դϴ�");
				}
			}
			
			sql = "select * from Order_info where buyer is null and 1"
					+ "Vnum = '" + orderVnum +"'";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				exist = true;
			}
			
			if ( exist == true ) {
				System.out.println("������ ������ �̹� ��ϵǾ��ֽ��ϴ�.");
				return;
			}
			else {
				sql = "insert into Order_info(Order_num, seller, Vnum) values (" + orderNum + ", '" + orderID + "', '" + orderVnum + "')";
				
				int res = stmt.executeUpdate(sql);
				
				if(res == 0) {
					System.out.println("�ٽ� �õ��� �ּ���!");
				}
				else if (res == 1){
					System.out.println("�Ź��� ���������� ��ϵǾ����ϴ�!");
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
		
		System.out.println("[��� ���� ����]");
		
		//�켱 ���� ��ȣ �ް�, �ִ��� ������ ��, ������ ���� �޴´�.
		
		try {
			while(true) {
				System.out.print("������ �Ź��� ������ȣ�� �Է��ϼ��� : ");
				vehicleNum = scan.nextLine();
				
				sql = "select * from order_info where buyer is null and vnum = '" + vehicleNum + "'";

				rs = stmt.executeQuery(sql);
				
				if(rs.next()) {
					exist = true;
				}
				
				if(exist == true) {
					System.out.println("��ȿ�� ������ȣ�Դϴ�.");
					break;
				}
				else {
					System.out.println("��ȿ�������� ������ȣ�Դϴ�.");
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
			
			System.out.println("������ ������ ������ �Է��ϼ���. (������ ��ġ������, ������ �Է�)");
			
			sb1.append("update vehicle set ");
			
			boolean isFirst = true;
			
			while(true) {
				
				System.out.print("���� (yyyy-mm-dd) (���� : " + model_year +  ") : ");
				
				model_year = scan.nextLine().trim();
				
				if(model_year.equals(""))
					break;
								
				if (!model_year.matches("\\d{4}-\\d{2}-\\d{2}"))
				   System.out.println("���Ŀ� ���� �ٽ� �Է��ϼ���!");
				else
					break;
				
			}
			
			if(!model_year.equals("")) {
				change = true;
				isFirst = false;
				sb1.append("model_year = to_date('" + model_year + "', 'yyyy-mm-dd')");
			}
			
			
			
			System.out.print("����Ÿ� (���� : " + mileage + ") : ");
			
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
			
			
			System.out.print("���� (���� : " + price + "): ");
			
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
				System.out.println("��ȣ : " + String.valueOf(rs.getInt("fuel_id")) + ", ���� : " + rs.getString("fuel_type"));
			}
			
			while(true) {
				System.out.print("���� ��ȣ�� �Է��ϼ��� (���� : "+ Fnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("������ Ȯ�����ּ���.");
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
				System.out.println("��ȣ : " + String.valueOf(rs.getInt("color_id")) + ", ���� : " + rs.getString("color_name"));
			}
			
			while(true) {
				System.out.print("������ �Է��ϼ��� (���� : "+ Cnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("������ Ȯ�����ּ���.");
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
				System.out.println("��ȣ : " + String.valueOf(rs.getInt("c_id")) + ", ���� : " + rs.getString("c_type"));
			}
			
			while(true) {
				System.out.print("ī�װ� (���� : "+ Ctnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("������ Ȯ�����ּ���.");
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
				System.out.println("��ȣ : " + String.valueOf(rs.getInt("ed_id")) + ", ���� : " + rs.getString("ed_type"));
			}
			
			while(true) {
				System.out.print("��ⷮ (���� : "+ Enum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("������ Ȯ�����ּ���.");
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
				System.out.println("��ȣ : " + String.valueOf(rs.getInt("t_id")) + ", ���� : " + rs.getString("t_type"));
			}
			
			while(true) {
				System.out.print("Ʈ�����̼� (���� : "+ Tnum +") : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("������ Ȯ�����ּ���.");
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
			
			//����Ŀ �̰�, �� �̰�, ���� �� �̾Ƽ� �����Ű��..
			
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
				System.out.println("��ȣ : " + String.valueOf(rs.getInt("maker_id")) + ", ���� : " + rs.getString("maker_name"));
			}
			
			while(true) {
				System.out.print("�����縦 �����ϼ��� : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals(""))
					break;
				
				if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
					break;
				}
				else {
					System.out.println("������ Ȯ�����ּ���.");
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
					System.out.println("��ȣ : " + String.valueOf(rs.getInt("model_id")) + ", ���� : " + rs.getString("model_name"));
				}
				
				while(true) {
					System.out.print("���� �����ϼ��� : ");
					Stemp = scan.nextLine().trim();
					
					if(Stemp.equals(""))
						break;
					
					if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
						break;
					}
					else {
						System.out.println("������ Ȯ�����ּ���.");
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
						System.out.println("��ȣ : " + String.valueOf(rs.getInt("detail_id")) + ", ���� : " + rs.getString("detail_name"));
					}
					
					while(true) {
						System.out.print("���� ���� �����ϼ��� : ");
						Stemp = scan.nextLine().trim();
						
						if(Stemp.equals(""))
							break;
						
						if( min <= Integer.parseInt(Stemp) && Integer.parseInt(Stemp) <= max ) {
							break;
						}
						else {
							System.out.println("������ Ȯ�����ּ���.");
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
					System.out.println("���������� ó�� �Ǿ����ϴ�.");
					Main.conn.commit();
				}
				else {
					System.out.println("�ٽ� �õ��� �ּ���!");
				}
				
			}
			else {
				System.out.println("���������ʰ� �����մϴ�.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void blindForSale() {
		String sql = null;
		String Stemp = null;
		
		System.out.println("[����� ó��]");
		
		try {
			
			while(true) {
				System.out.print("����� ����� ���ϸ� Y(y), ����� ������ ���ϸ� N(n)�� �Է��ϼ���. : ");
				Stemp = scan.nextLine().trim();
				
				if(Stemp.equals("Y") || Stemp.equals("y")) {
					//����� ���

					while(true) {
						
						System.out.print("����� ó���� �Ź��� ORDER_NUM�� �Է��ϼ���! (��ġ������ ������ �Է��ϼ���) : ");
						Stemp = scan.nextLine().trim();
						
						if(Stemp.equals("")) {
							System.out.println("�Ź� ������� �����մϴ�.");
							break;
						}
						else {
							if(!Stemp.matches("^[0-9]*$")) {
								System.out.println("���ڸ� �Է��ϼ���!");
							}
							else {
								
								sql = "select * from filter where order_num = " + String.valueOf(Stemp);
								
								rs = stmt.executeQuery(sql);
								
								if(rs.next()) {
									System.out.println("�̹� ����� ��ϵ� �Ź��Դϴ�.");
									break;
								}
								
								//�Ǹ����� �Ź����� �Է¹��� ORDER_NUM�� �����ϴ��� Ȯ��.
								sql = "select * from order_info where buyer is null and Order_num = " + String.valueOf(Stemp);
								
								rs = stmt.executeQuery(sql);
								
								if(rs.next()) {
									sql = "insert into FILTER values (" + String.valueOf(Stemp) + ")";
									
									//System.out.println(sql);
									
									int res = stmt.executeUpdate(sql);
									
									if ( res == 0 ) {
										System.out.println("������������ ó�� �Ǿ����ϴ�. �ٽ� Ȯ�����ּ���");
										break;
									}
									else {
										System.out.println("���������� ó�� �Ǿ����ϴ�.");
										Main.conn.commit();
										break;
									}
								}
								else {
									System.out.println("�Ǹ����� �Ź����� ��ġ�ϴ� �Ź��� ���������ʽ��ϴ�.");
									break;
								}
								
							}
						}					
					}
					//����� ��� �Ϸ�
					break;
					
				}
				else if(Stemp.equals("N") || Stemp.equals("n")) {
					//����� ����
					
					while(true) {
						
						System.out.print("����� ó�� ������ �Ź��� ORDER_NUM�� �Է��ϼ���! (��ġ������ ������ �Է��ϼ���) : ");
						Stemp = scan.nextLine().trim();
						
						if(Stemp.equals("")) {
							System.out.println("�Ź� ����� ������ �����մϴ�.");
							break;
						}
						else {
							if(!Stemp.matches("^[0-9]*$")) {
								System.out.println("���ڸ� �Է��ϼ���!");
							}
							else {
								
								sql = "select * from filter where order_num = " + String.valueOf(Stemp);
								
								rs = stmt.executeQuery(sql);
								
								if(rs.next()) {
									
									sql = "delete from FILTER where Order_num = " + String.valueOf(Stemp);
									
									int res = stmt.executeUpdate(sql);
									
									if ( res == 0 ) {
										System.out.println("������������ ó�� �Ǿ����ϴ�. �ٽ� Ȯ�����ּ���");
										break;
									}
									else {
										System.out.println("���������� ó�� �Ǿ����ϴ�.");
										Main.conn.commit();
										break;
									}
								}
								else {
									System.out.println("����� ��ϵ������� �Ź��Դϴ�.");
									break;
									
								}
							}
						}					
					}
					//����� ���� �Ϸ�
					break;
				}
				else {
					System.out.println("�Է��� �ٸ��� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}