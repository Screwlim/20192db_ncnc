package NCNC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//�ŷ� ���� ���
public class Menu3 {

	public static void main(){

		ResultSet rs = null;
		Statement stmt = null;

		System.out.println("[�ŷ� ���� ���� �Դϴ�.]");

		// ����ڷ� ȭ��(�ŷ������� ����)
		if (Main.admin == false) {

			System.out.println("\nȸ������ �ŷ� �����Դϴ�.");


			showOrder();

		} else if (Main.admin) {

			int menuNum;

			Scanner scan = new Scanner(System.in);

			while (true) {
				// �������� ���
				System.out.println("\n1. ��� �ŷ� ���� ���");
				System.out.println("2. ���� �����");
				System.out.println("3. �����纰 �����");
				System.out.println("4. �����纰 �����");
				System.out.println("5. ���ư���");
				System.out.println("\n�޴��� �Է��ϼ��� : ");

				menuNum = scan.nextInt();

				if (menuNum == 1) {

					System.out.println("\n��� �ŷ� �����Դϴ�.");
					showOrder();
				} else if (menuNum == 2) {
					showMonthsale();
				} else if (menuNum == 3) {
					showYearsale();
				} else if (menuNum == 4) {
					showMakersale();
				} else if (menuNum == 5) {

					System.out.println("�ŷ� ���� ���񽺸� �����մϴ�.");

					break;

				} else {
					System.out.println("�߸��� �Է��Դϴ�!");
				}
			}
		}
	}

	//�ŷ� ����(sql1 : ȸ�� , sql2 : ������)
	public static void showOrder() {

		ResultSet rs = null;
		Statement stmt = null;
		int count = 0;

		String sql1 = "select * from ORDER_INFO where SELLER = '" + Main.id + "'";
		String sql2 = "select * from ORDER_INFO";
		try {
			stmt = Main.conn.createStatement();
			
			if (Main.admin == false) {
				rs = stmt.executeQuery(sql1);
			}
			else if (Main.admin) {
				rs = stmt.executeQuery(sql2);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			while (rs.next()) {
				
				int orderNum = rs.getInt(1);
				String orderDate = rs.getString(2);
				String buyer = rs.getString(3);
				String seller = rs.getString(4);
				String vNum = rs.getString(5);

				System.out.println("�ֹ���ȣ : " + orderNum + ", �ֹ���¥ = " + orderDate + ", ������ = " + buyer + ", �Ǹ��� = "
						+ seller + ", ������ȣ = " + vNum);
				
				count++;
			}
			
			if(count == 0) {
				System.out.println("\n�ŷ� ������ �����ϴ�!\n");
			}
			
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//���� ���� ����
	public static void showMonthsale() {

		ResultSet rs = null;
		Statement stmt = null;

		try {
			
			String sql = "SELECT SUBSTR(O.order_date, 4, 2), SUM(v.price)\r\n FROM VEHICLE V JOIN ORDER_INFO O ON v.vehicle_num = o.vnum GROUP BY SUBSTR(O.order_date, 4, 2)HAVING SUBSTR(O.order_date, 4, 2) IS NOT NULL ORDER BY SUBSTR(O.order_date, 4, 2) ASC";
			stmt = Main.conn.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.println("[���� ����� �Դϴ�.]");
			while (rs.next()) {

				String Month = rs.getString(1);
				int totlaPrice = (int) rs.getLong(2);

				System.out.println("�� = " + Month + ", ���� = " + totlaPrice + "��");
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//���� ���� ����
	public static void showYearsale() {

		ResultSet rs = null;
		Statement stmt = null;

		try {
			
			String sql = "SELECT SUBSTR(O.order_date, 1, 2), SUM(v.price)\r\n FROM VEHICLE V JOIN ORDER_INFO O ON v.vehicle_num = o.vnum GROUP BY SUBSTR(O.order_date, 1, 2)HAVING SUBSTR(O.order_date, 1, 2) IS NOT NULL ORDER BY SUBSTR(O.order_date, 1, 2) ASC";
			stmt = Main.conn.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.println("[�⺰ ����� �Դϴ�.]");
			while (rs.next()) {

				String Year = rs.getString(1);
				int totlaPrice = (int) rs.getLong(2);

				System.out.println("�� = 20" + Year + ", ���� = " + totlaPrice + "��");
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//�����纰 ���� ����
	public static void showMakersale() {

		ResultSet rs = null;
		Statement stmt = null;

		try {
			
			String sql = "SELECT Maker_Name AS MAKER, SUM(v.price) FROM ((((vehicle V JOIN order_info O On V.Vehicle_num = O.Vnum) JOIN Detailed_model D ON V.Dnum=Detail_id) JOIN MODEL ON MODEL.Model_id = D.Mno) JOIN MAKER M ON M.Maker_id = MODEL.Maker_no) GROUP BY M.Maker_Name ORDER BY M.Maker_Name ASC";
			stmt = Main.conn.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.println("[�⺰ ����� �Դϴ�.]");
			while (rs.next()) {

				String Maker = rs.getString(1);
				int totlaPrice = (int) rs.getLong(2);

				System.out.println("������ = " + Maker + ", ���� = " + totlaPrice + "��");
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
