package NCNC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.sun.jdi.connect.spi.Connection;

public class Menu1 {

	static Scanner scan = new Scanner(System.in);

	public static ResultSet rs = null;
	public static ResultSet rs1 = null;

	public static Statement stmt = null;

	public static void main() {
		// TODO Auto-generated method stub

		scan = new Scanner(System.in);

		int menuNum;

		try {
			stmt = Main.conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("[ȸ�� ���� ���]");

		while (true) {

			System.out.println("\n1. ȸ�� ���� ����");
			System.out.println("2. ��й�ȣ ����");
			System.out.println("3. ȸ�� Ż��");
			System.out.println("4. ���ư���");
			System.out.println("\n�޴��� �Է��ϼ��� : ");

			menuNum = scan.nextInt();

			if (menuNum == 1) {
				adjustInfo();
			} else if (menuNum == 2) {
				adjustPW();
			} else if (menuNum == 3) {
				dropAccount();
			} else if (menuNum == 4) {
				System.out.println("ȸ�� ���� ���񽺸� �����մϴ�.");
				break;
			} else {
				System.out.println("�߸��� �Է��Դϴ�!");
			}
		}
	}

	// ȸ�� ���� ���� �޴�
	private static void adjustInfo() {

		Scanner scan = new Scanner(System.in);
		int menuNum;

		String sql = null;
		String sql1 = null;

		String Name = null;
		String Phonenum = null;
		String Email = null;
		String Address = null;
		String Gender = null;
		String Birth = null;
		String Job = null;

		String adName = null;
		String adPhonenum = null;
		String adEmail = null;
		String adAddress = null;
		String adBirth = null;
		String adJob = null;

		System.out.println("\n[ȸ�� ���� ���� ����]\n");

		// �켱 ���� ��ȣ �ް�, �ִ��� ������ ��, ������ ���� �޴´�.

		try {
			while (true) {

				stmt = Main.conn.createStatement();

				sql = "select * from account where ID = '" + Main.id + "'";
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					Name = rs.getString(3);
					Phonenum = rs.getString(4);
					Email = rs.getString(5);
					Address = rs.getString(6);
					Gender = rs.getString(7);
					Birth = rs.getString(8);
					Job = rs.getString(9);
				}

				System.out.println("\n[�����ϰ� ������ ������ ��ȣ�� �Է��ϼ���.]");
				System.out.println("1. �̸�");
				System.out.println("2. ��ȭ��ȣ");
				System.out.println("3. �̸��� �ּ�");
				System.out.println("4. ���ּ�");
				System.out.println("5. ����");
				System.out.println("6. ����");
				System.out.println("7. ����");
				System.out.println("8. ���ư���");
				System.out.println("\n�޴��� �Է��ϼ��� : ");

				menuNum = scan.nextInt();
				scan.nextLine();// ���� ���� ����

				rs.close();

				if (menuNum == 1) {

					System.out.print("���� ȸ������ �̸��Դϴ� :");
					System.out.println(Name);

					System.out.print("�����Ͻ� �̸��� �Է��� �ּ��� : ");
					adName = scan.nextLine();

					sql1 = "update account set name= '" + adName + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");

					rs1.close();

				} else if (menuNum == 2) {

					System.out.print("���� ȸ������ ��ȭ��ȣ�Դϴ� :");
					System.out.println(Phonenum);

					System.out.print("�����Ͻ� ��ȭ��ȣ�� �Է��� �ּ��� : ");
					adPhonenum = scan.nextLine();

					sql1 = "update account set phone_num = '" + adPhonenum + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");

					rs1.close();

				} else if (menuNum == 3) {

					System.out.print("���� ȸ������ �̸��� �ּ��Դϴ� :");
					System.out.println(Email);

					System.out.print("�����Ͻ� �̸��� �ּҸ� �Է��� �ּ��� : ");
					adEmail = scan.nextLine();

					sql1 = "update account set email_address = '" + adEmail + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");

					rs1.close();

				} else if (menuNum == 4) {

					System.out.print("���� ȸ������ ���ּ��Դϴ� :");
					System.out.println(Address);

					System.out.print("�����Ͻ� ���ּҸ� �Է��� �ּ��� : ");
					adAddress = scan.nextLine();

					sql1 = "update account set email_address = '" + adAddress + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");

					rs1.close();

				} else if (menuNum == 5) {

					System.out.print("���� ȸ������ ������ ");

					if (Gender.equals("M")) {
						System.out.println("'����' �Դϴ�.");

						sql1 = "update account set gender = 'F' where id = '" + Main.id + "'";
					} else if (Gender.equals("F")) {
						System.out.println("'����' �Դϴ�.");

						sql1 = "update account set gender = 'M' where id = '" + Main.id + "'";
					}

					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");
					rs1.close();

				} else if (menuNum == 6) {

					System.out.print("���� ȸ������ ���ϳ�¥�Դϴ� :");
					System.out.println(Birth);

					System.out.print("�����Ͻ� ���ϳ�¥�� �Է��� �ּ��� : ");
					adBirth = scan.nextLine();

					if (adBirth.equals("")) {
						sql1 = "update account set birth_date = null where id = '" + Main.id + "'";
					} else {
						sql1 = "update account set birth_date = '" + adBirth + "' where id = '" + Main.id + "'";
					}

					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");

					rs1.close();

				} else if (menuNum == 7) {

					System.out.print("���� ȸ������ �����Դϴ� :");
					System.out.println(Job);

					System.out.print("�����Ͻ� ������ �Է��� �ּ��� : ");
					adJob = scan.nextLine();

					if (adJob.equals("")) {
						sql1 = "update account set job = null where id = '" + Main.id + "'";
					} else {
						sql1 = "update account set job = '" + adJob + "' where id = '" + Main.id + "'";
					}

					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");

					rs1.close();

				} else if (menuNum == 8) {
					System.out.println("\nȸ�� ���� ���� ���񽺸� �����մϴ�.\n");
					break;
				} else {
					System.out.println("�߸��� �Է��Դϴ�!");
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ��й�ȣ �������
	private static void adjustPW() {

		Scanner scan = new Scanner(System.in);
		String newPW;

		String sql = null;

		try {
			stmt = Main.conn.createStatement();

			System.out.println("[��й�ȣ ���� ����]");
			System.out.print("�����Ͻ� ��й�ȣ�� �Է��� �ּ��� : ");
			newPW = scan.nextLine();

			sql = "update account set password= '" + newPW + "' where id = '" + Main.id + "'";
			rs = stmt.executeQuery(sql);
			Main.conn.commit();

			System.out.print("\n������ �Ϸ�Ǿ����ϴ�.\n");
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void dropAccount() {

		Scanner scan = new Scanner(System.in);
		int Count = 0;
		String answer;

		String sql = null;
		try {
			stmt = Main.conn.createStatement();

			System.out.println("[ȸ��Ż�� ����]");

			try {
				rs = stmt.executeQuery("SELECT COUNT(*) FROM ACCOUNT WHERE is_admin = 'T'");
				if (rs.next())
					Count = rs.getInt(1);
				System.out.println("���� ������ �� : " + Count);

				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Main.admin && Count == 1) {
				System.out.println("���� �����Ͻ� �����ڴ��� ������ ������ �Դϴ�!");
				System.out.println("Ż�� ����� �̿��Ͻ� �� �����ϴ�.");
			}

			else {
				System.out.println("������ ȸ���� Ż���Ͻðڽ��ϱ�?(y or n)");

				answer = scan.nextLine();

				if (answer.equals("y")) {
					sql = "delete from account where id='" + Main.id + "'";
					rs = stmt.executeQuery(sql);
					Main.conn.commit();
					rs.close();

					System.out.println("\nŻ�� �Ϸ�Ǿ����ϴ�.");
					System.out.println("���ݱ��� ���� ���� �̿����ּż� �����մϴ�.");
					System.out.println("���񽺸� �����մϴ�.");
					System.exit(0);

				} else if (answer.equals("n")) {
					System.out.println("�޴��� ���ư��ϴ�.");
				} else {
					System.out.println("�߸��� �Է��Դϴ�. �޴��� ���ư��ϴ�.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
