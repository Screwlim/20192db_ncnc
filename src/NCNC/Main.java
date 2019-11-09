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
				boolean res = login();
				if (res == true && admin == false) {
					// menu 1-1 ����
					System.out.println("1. ȸ�� ���� ����");
					System.out.println("2. ���� ����");
					System.out.println("3. �ŷ� ����");
					System.out.println("4. ���ư���");
					System.out.println("\n�޴��� �Է��ϼ��� : ");

					menuNum = scan.nextInt();

					switch (menuNum) {
					case 1:

						break;
					case 2:

						break;
					case 3:

						break;
					case 4:
						break;
					default:
						System.out.println("�߸��� �Է��Դϴ�!");
					}
				} else if (res == true && admin == true) {
					// menu 1-2 ����
					System.out.println("1. ȸ�� ���� ����");
					System.out.println("2. ���� ����");
					System.out.println("3. �ŷ� ����");
					System.out.println("4. ������ ���");
					System.out.println("5. ���ư���");
					System.out.println("\n�޴��� �Է��ϼ��� : ");

					menuNum = scan.nextInt();

					switch (menuNum) {
					case 1:

						break;
					case 2:

						break;
					case 3:

						break;
					case 4:
						break;
					case 5:
						break;
					default:
						System.out.println("�߸��� �Է��Դϴ�!");
					}

				}
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
		Account userinfo = new Account();
		Scanner scan = new Scanner(System.in);
		StringBuffer sb = new StringBuffer();
		boolean check = false;
		
		ResultSet rs = null;
		Statement stmt = null;

		try {
			
			stmt = conn.createStatement();
			
			System.out.println("[ȸ������]");
			System.out.println("�Ʒ� ������ ���ʷ� �����ϼ���.(1~9)");
			System.out.println("'*'�� �ʼ� �Է� �����Դϴ�.");

			while (true) {

				System.out.println();
				System.out.print("1.*���̵� : ");
				userinfo.setAccountID(scan.nextLine().trim());
				
				if(userinfo.getAccountId().equals("")) {
					System.out.println("�ʼ� �Է� �����Դϴ�!");
					continue;
				}
			
				//sql = "select * from Account where id = '" + userinfo.getAccountId() + "'";
				System.out.println(sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					check = true;
				}
				
				if (check == true) {
					System.out.println("�ߺ��� ���̵� �Դϴ�!");
					continue;
				} 
				else {
					System.out.println("��� ������ ���̵� �Դϴ�.");
				}
				
				/*
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
				*/
				/*
				try {
					sql = "INSERT INTO ACCOUNT VALUES ('" + userID + "'" + userPW + "'" + Name + "'" + Phone + "'"
							+ Email + "'" + Address + "'" + Gender + "'" + Birth + "'" + Job + ")";
				} catch (Exception e) {
					System.err.println("insert error : " + e.getMessage());
					System.exit(1);
				}
				*/

				System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�!");

			}
		} catch (Exception e) {
			System.err.println("insert error : " + e.getMessage());
			System.exit(1);
		}
	}
}
