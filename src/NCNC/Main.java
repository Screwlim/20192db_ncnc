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
			System.out.println("[니카내카]");
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 종료");

			Scanner scan = new Scanner(System.in);
			int menuNum;

			// 메뉴 번호 입력
			System.out.print("\n메뉴를 입력하세요 : ");
			menuNum = scan.nextInt();

			switch (menuNum) {
			case 1:
				login();
				break;
			case 2:
				signup();
				break;
			case 3:
				System.out.println("서비스를 종료합니다.");
				System.exit(0);
				break;
			default:
				System.out.println("잘못된 입력입니다!");
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
			System.out.println("[로그인]");

			while (true) {

				System.out.print("아이디를 입력하세요! : ");
				loginID = scan.nextLine();

				System.out.print("비밀번호를 입력하세요! : ");
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
		// 아이디, 비밀번호 검사용
		for (int i = 0; i < accounts.size(); i++) {
			map.put(accounts.get(i).getAccountId(), accounts.get(i).getAccountPW());
		}

		try {

			System.out.println("[회원가입]");
			System.out.println("아래 사항을 차례로 기입하세요.(1~9)");
			System.out.println("'*'는 필수 입력 사항입니다.");

			while (true) {

				while (true) {
					System.out.println();
					System.out.print("1.*아이디 : ");
					userID = scan.nextLine();

					if (map.containsKey(userID)) {
						System.out.println("중복된 아이디 입니다!");
					} else {
						System.out.println("사용 가능한 아이디 입니다.");
						break;
					}
				}

				System.out.print("2.*비밀번호 : ");
				userPW = scan.nextLine();

				System.out.print("3.*이름(영문자 표기, 예 : HongGilDong) : ");
				Name = scan.nextLine();

				System.out.print("4.*전화번호('-'제외하고 입력) : ");
				Phone = scan.nextLine();

				System.out.print("5.*이메일 : ");
				Email = scan.nextLine();

				System.out.print("6.*주소(도로명 주소 영문표기, 예 : GwanCheonro 17gil 2) : ");
				Address = scan.nextLine();

				System.out.print("7.성별(M/F) : ");
				Gender = scan.nextLine();

				System.out.print("8.생년월일(년,월,일 순으로, 예 : 97/7/12) : ");
				Birth = scan.nextLine();

				System.out.print("9.직업 : ");
				Job = scan.nextLine();

				try {
					sql = "INSERT INTO ACCOUNT VALUES ('" + userID + "'" + userPW + "'" + Name + "'" + Phone + "'"
							+ Email + "'" + Address + "'" + Gender + "'" + Birth + "'" + Job + ")";
				} catch (Exception e) {
					System.err.println("insert error : " + e.getMessage());
					System.exit(1);
				}

				System.out.println("회원가입에 성공하셨습니다!");

			}
		} catch (Exception e) {
			System.err.println("insert error : " + e.getMessage());
			System.exit(1);
		}
		
		*/
	}
}
