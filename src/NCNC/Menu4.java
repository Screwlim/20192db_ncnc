package NCNC;

import java.sql.*;
import java.util.Scanner;

//�ŷ� ���� ���
public class Menu4 {
	public static Scanner scan = null;

	public static void main() {
		// TODO Auto-generated method stub

		int menuNum;

		 scan = new Scanner(System.in);

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
				System.out.println("\n�޴��� �Է��ϼ��� : ");

				menuNum = scan.nextInt();

				switch (menuNum) {
				case 1:
					// [�� �Ź����]

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

		System.out.println("[�� �Ź����]");
		
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
			
			System.out.println("�Ź� ������� ID�� �Է��ϼ��� : ");

			orderID = scan.nextLine();
			
			sql = "select * from Account where id = '" + orderID + "'";
			
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

	}

	public static void updateForSale() {
		System.out.println("[��� ���� ����]");

	}

	public static void blindForSale() {
		System.out.println("[����� ó��]");
	}
}