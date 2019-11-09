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
					break;

				case 3:
					// [����� ó��]
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
			
			sql = "select * from Order_info where Vnum = '" + orderVnum +"'";
			
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
		String vehicleNum = null;
		String Stemp = null;
		int temp = 0;
		
		boolean exist = false;
		
		System.out.println("[��� ���� ����]");
		
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
		
		
		//�켱 ���� ��ȣ �ް�, �ִ��� ������ ��, ������ ���� �޴´�.
		
		try {
			while(true) {
				System.out.print("������ ������ ������ȣ�� �Է��ϼ��� : ");
				vehicleNum = scan.nextLine();
				
				sql = "select * from vehicle where vehicle_num = '" + vehicleNum + "'";

				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
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
			
			System.out.println("������ ������ ������ �Է��ϼ���. (������ ��ġ������, ������ �Է�)");
			
			System.out.print("���� (yyyy-mm-dd) : ");
			System.out.print("����Ÿ� : ");
			System.out.print("���� : ");
			
			sql = "select * from fuels";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("��ȣ : " + String.valueOf(rs.getInt(0)) + ", ����" + rs.getString(1));
			}

			System.out.print("���� ��ȣ�� �Է��ϼ��� : ");
			Stemp = scan.nextLine().trim();
			
			if(Stemp.equals("")) {
				//�ϰ͵� ���ϰ� �Ѿ
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
		System.out.println("[����� ó��]");
	}
}