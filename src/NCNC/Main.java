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
	public static String pw = null;
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

			// �޴� ��ȣ �Է�
			System.out.print("\n�޴��� �Է��ϼ��� : ");
			menuNum = scan.nextInt();

			switch (menuNum) {
			case 1:
				login();
				break;
			case 2:
				signup();
				break;
			case 3:
				System.out.println("���񽺸� �����մϴ�.");
				System.exit(0);
				break;
			default:
				System.out.println("�߸��� �Է��Դϴ�!");
			}
		}
	}

	public static void login() {
		String loginID = null;
		String loginPW = null;
		String sql = null;
		ResultSet rs = null;
		Statement stmt = null;

		Account userinfo = new Account();
		Scanner scan = new Scanner(System.in);
		
		stmt = conn.createStatement();

		try {
			System.out.println("[�α���]");

			while (true) {

				System.out.print("���̵� �Է��ϼ���! : ");
				loginID = scan.nextLine();

				System.out.print("��й�ȣ�� �Է��ϼ���! : ");
				loginPW = scan.nextLine();

				sql = "select * from Account where id = '" + loginID + "' and password = '" + loginPW + "')";
				
				
				
			}
		} catch (Exception e) {
			System.err.println("login error : " + e.getMessage());
			System.exit(1);
		}
	}

	public static void signup() {

		Account userinfo = new Account();

		Scanner scan = new Scanner(System.in);
/*
		// ���̵�, ��й�ȣ �˻��
		for (int i = 0; i < accounts.size(); i++) {
			map.put(accounts.get(i).getAccountId(), accounts.get(i).getAccountPW());
		}

		try {

			System.out.println("[ȸ������]");
			System.out.println("�Ʒ� ������ ���ʷ� �����ϼ���.(1~9)");
			System.out.println("'*'�� �ʼ� �Է� �����Դϴ�.");

			while (true) {

				while (true) {
					System.out.println();
					System.out.print("1.*���̵� : ");
					userID = scan.nextLine();

					if (map.containsKey(userID)) {
						System.out.println("�ߺ��� ���̵� �Դϴ�!");
					} else {
						System.out.println("��� ������ ���̵� �Դϴ�.");
						break;
					}
				}

				System.out.print("2.*��й�ȣ : ");
				userPW = scan.nextLine();

				System.out.print("3.*�̸�(������ ǥ��, �� : HongGilDong) : ");
				Name = scan.nextLine();

				System.out.print("4.*��ȭ��ȣ('-'�����ϰ� �Է�) : ");
				Phone = scan.nextLine();

				System.out.print("5.*�̸��� : ");
				Email = scan.nextLine();

				System.out.print("6.*�ּ�(���θ� �ּ� ����ǥ��, �� : GwanCheonro 17gil 2) : ");
				Address = scan.nextLine();

				System.out.print("7.����(M/F) : ");
				Gender = scan.nextLine();

				System.out.print("8.�������(��,��,�� ������, �� : 97/7/12) : ");
				Birth = scan.nextLine();

				System.out.print("9.���� : ");
				Job = scan.nextLine();

				try {
					sql = "INSERT INTO ACCOUNT VALUES ('" + userID + "'" + userPW + "'" + Name + "'" + Phone + "'"
							+ Email + "'" + Address + "'" + Gender + "'" + Birth + "'" + Job + ")";
				} catch (Exception e) {
					System.err.println("insert error : " + e.getMessage());
					System.exit(1);
				}

				System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�!");

			}
		} catch (Exception e) {
			System.err.println("insert error : " + e.getMessage());
			System.exit(1);
		}
		
		*/
	}
}
