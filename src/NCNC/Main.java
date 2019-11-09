package NCNC;

import java.util.Scanner;
import java.sql.*;

public class Main {

	// to get JDBC Connection
	public static final String URL = "jdbc:oracle:thin:@localhost:1600:xe";
	public static final String USER_NAME = "nicar";
	public static final String USER_PASSWD = "car";

	// Connection
	public static Connection conn = null;

	// user info
	public static String id = null;
	public static boolean admin = false;

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, USER_PASSWD);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println("Cannot get a connection: " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			// menu
			System.out.println("[��ī��ī]");
			System.out.println("1. �α���");
			System.out.println("2. ȸ������");
			System.out.println("3. ����");

			Scanner scan = new Scanner(System.in);
			int menuNum;
			int subMenuNum;

			// �޴� ��ȣ �Է�
			System.out.print("\n�޴��� �Է��ϼ��� : ");
			menuNum = scan.nextInt();
			scan.nextLine();

			if (menuNum == 1) {
				boolean res = login();

				if (res == true && admin == false) {
					// menu 1-1 ����
					while(true) {
						
						System.out.println("1. ȸ�� ���� ����");
						System.out.println("2. ���� ����");
						System.out.println("3. �ŷ� ����");
						System.out.println("4. �α׾ƿ�");
						System.out.print("\n�޴��� �Է��ϼ��� : ");

						subMenuNum = scan.nextInt();
						scan.nextLine();

						if (subMenuNum == 1) {

						} else if (subMenuNum == 2) {

						} else if (subMenuNum == 3) {

						} else if (subMenuNum == 4) {
							Main.id = null;
							Main.admin = false;
							res = false;
							System.out.println("�α׾ƿ� �ϼ̽��ϴ�!");
							break;
						} else {
							System.out.println("�߸��� �Է��Դϴ�!");
						}
						
					}

				} else if (res == true && admin == true) {

					while (true) {
						// menu 1-2 ����
						System.out.println("1. ȸ�� ���� ����");
						System.out.println("2. ���� ����");
						System.out.println("3. �ŷ� ����");
						System.out.println("4. ������ ���");
						System.out.println("5. �α׾ƿ�");
						System.out.print("\n�޴��� �Է��ϼ��� : ");

						subMenuNum = scan.nextInt();
						scan.nextLine();

						if (subMenuNum == 1) {

						} else if (subMenuNum == 2) {

						} else if (subMenuNum == 3) {

						} else if (subMenuNum == 4) {
							Menu4.main();
						} else if (subMenuNum == 5) {
							Main.id = null;
							Main.admin = false;
							res = false;
							System.out.println("�α׾ƿ� �ϼ̽��ϴ�!");
							break;

						} else {
							System.out.println("�߸��� �Է��Դϴ�!");
						}
					}
				}

			} else if (menuNum == 2) {
				signup();
			} else if (menuNum == 3) {
				System.out.println("���񽺸� �����մϴ�.");
				System.exit(0);
			} else {
				System.out.println("�߸��� �Է��Դϴ�!");
			}

		}
	}

	public static boolean login() {
		String loginID = null;
		String loginPW = null;
		String sql = null;
		boolean result = false;

		ResultSet rs = null;
		Statement stmt = null;

		Account userinfo = new Account();
		Scanner scan = new Scanner(System.in);

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println("[�α���]");

			while (true) {
				System.out.print("���̵� �Է��ϼ���! : ");
				loginID = scan.nextLine();

				System.out.print("��й�ȣ�� �Է��ϼ���! : ");
				loginPW = scan.nextLine();

				sql = "select * from Account where id = '" + loginID + "' and password = '" + loginPW + "'";
				// System.out.println(sql);

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					Main.id = loginID;
					result = true;
					break;
				}
				if (result == true) {
					if (rs.getString(10).equals("T")) {
						System.out.println("�����ڴ��� �α��� �ϼ̽��ϴ�!\n");
						Main.admin = true;
					} else {
						System.out.println(Main.id + "���� �α��� �ϼ̽��ϴ�!\n");
						Main.admin = false;
					}
					return result;
				} else {
					System.out.println("�α��� ������ �ٽ� �Է��ϼ���!");
				}
			}
		} catch (Exception e) {
			System.err.println("login error : " + e.getMessage());
			System.exit(1);
		}

		return false;
	}

	public static void signup() {
		String sql = null;
		StringBuffer sb = new StringBuffer();
		Account userinfo = new Account();
		Scanner scan = new Scanner(System.in);
		String temp = null;
		boolean ad = false;

		ResultSet rs = null;
		Statement stmt = null;

		try {

			stmt = conn.createStatement();

			System.out.println("[ȸ������]");
			System.out.println("�����ڿ� ȸ�������̸� ADMIN�� �Է��ϼ��� : ");
			temp = scan.nextLine();

			if (temp.equals("ADMIN")) {
				ad = true;
				System.out.println("[������ ȸ������]");
			} else {
				ad = false;
				System.out.println("[ȸ������]");
			}

			System.out.println("�Ʒ� ������ ���ʷ� �����ϼ���.(1~9)");
			System.out.println("'*'�� �ʼ� �Է� �����Դϴ�.");

			while (true) {
				sb.setLength(0);
				boolean check = false;

				System.out.println();
				System.out.print("1.*���̵� : ");
				userinfo.setAccountID(scan.nextLine().trim());

				if (userinfo.getAccountId().equals("")) {
					System.out.println("�ʼ� �Է� �����Դϴ�!");
					continue;
				}

				sql = "select * from Account where id = '" + userinfo.getAccountId() + "'";
				// System.out.println(sql);

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					check = true;
				}

				if (check == true) {
					System.out.println("�ߺ��� ���̵� �Դϴ�!");
					continue;
				} else {
					System.out.println("��� ������ ���̵� �Դϴ�.");
				}

				sb.append("insert into account values (" + "'" + userinfo.getAccountId() + "', ");

				System.out.print("2.*��й�ȣ : ");
				userinfo.setAccountPW(scan.nextLine().trim());

				if (userinfo.getAccountPW().equals("")) {
					System.out.println("�ʼ� �Է� �����Դϴ�!");
					continue;
				}

				sb.append("'" + userinfo.getAccountPW() + "', ");

				System.out.print("3.*�̸�(������ ǥ��, �� : HongGilDong) : ");
				userinfo.setName(scan.nextLine().trim());

				if (userinfo.getName().equals("")) {
					System.out.println("�ʼ� �Է� �����Դϴ�!");
					continue;
				}

				sb.append("'" + userinfo.getName() + "', ");

				System.out.print("4.*��ȭ��ȣ('-'�����ϰ� �Է�) : ");
				userinfo.setPhone(scan.nextLine().trim());

				if (userinfo.getPhone().equals("")) {
					System.out.println("�ʼ� �Է� �����Դϴ�!");
					continue;
				}

				sb.append("'" + userinfo.getPhone() + "', ");

				System.out.print("5.*�̸��� : ");
				userinfo.setEmail(scan.nextLine().trim());

				if (userinfo.getEmail().equals("")) {
					System.out.println("�ʼ� �Է� �����Դϴ�!");
					continue;
				}

				sb.append("'" + userinfo.getEmail() + "', ");

				System.out.print("6.*�ּ�(���θ� �ּ� ����ǥ��, �� : GwanCheonro 17gil 2) : ");
				userinfo.setAddress(scan.nextLine().trim());

				if (userinfo.getAddress().equals("")) {
					System.out.println("�ʼ� �Է� �����Դϴ�!");
					continue;
				}

				sb.append("'" + userinfo.getAddress() + "', ");

				System.out.print("7.����(M/F) : ");
				userinfo.setGender(scan.nextLine().trim());

				if (!(userinfo.getGender().equals("M") || userinfo.getGender().equals("F")
						|| userinfo.getGender().equals(""))) {
					System.out.println("������ �ٽ� �Է��ϼ���!");
					continue;
				}

				if (userinfo.getGender().equals("")) {
					sb.append("null, ");
				} else {
					sb.append("'" + userinfo.getGender() + "', ");
				}

				System.out.print("8.�������(�� : yyyy-mm-dd) : ");
				userinfo.setBirth(scan.nextLine().trim());

				if (userinfo.getBirth().equals("")) {
					sb.append("null, ");
				} else {
					sb.append("to_date('" + userinfo.getBirth() + "', 'yyyy-mm-dd'), ");
				}

				System.out.print("9.���� : ");
				userinfo.setJob(scan.nextLine().trim());

				if (userinfo.getJob().equals("")) {
					sb.append("null, ");
				} else {
					sb.append("'" + userinfo.getJob() + "', ");
				}

				if (ad == true) {
					sb.append("'T')");
				} else {
					sb.append("'F')");
				}

				int res = stmt.executeUpdate(sb.toString());
				// System.out.println(sb.toString());

				if (res == 1) {
					System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�!");
					conn.commit();
					break;
				} else {
					System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�!");
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("connection error : " + e.getMessage());
			System.exit(1);
		}
	}
}
