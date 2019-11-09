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
				boolean res = login();
				if (res == true && admin == false) {
					// menu 1-1 수행
					System.out.println("1. 회원 정보 관리");
					System.out.println("2. 차량 관리");
					System.out.println("3. 거래 내역");
					System.out.println("4. 돌아가기");
					System.out.println("\n메뉴를 입력하세요 : ");

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
						System.out.println("잘못된 입력입니다!");
					}
				} else if (res == true && admin == true) {
					// menu 1-2 수헹
					System.out.println("1. 회원 정보 관리");
					System.out.println("2. 차량 관리");
					System.out.println("3. 거래 내역");
					System.out.println("4. 관리자 기능");
					System.out.println("5. 돌아가기");
					System.out.println("\n메뉴를 입력하세요 : ");

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
						System.out.println("잘못된 입력입니다!");
					}

				}
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
			System.out.println("[로그인]");

			while (true) {
				System.out.print("아이디를 입력하세요! : ");
				loginID = scan.nextLine();

				System.out.print("비밀번호를 입력하세요! : ");
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
						System.out.println("관리자님이 로그인 하셨습니다!\n");
						Main.admin = true;
					} else {
						System.out.println(Main.id + "님이 로그인 하셨습니다!\n");
						Main.admin = false;
					}
					return result;
				} else {
					System.out.println("로그인 정보를 다시 입력하세요!");
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
			
			System.out.println("[회원가입]");
			System.out.println("아래 사항을 차례로 기입하세요.(1~9)");
			System.out.println("'*'는 필수 입력 사항입니다.");

			while (true) {

				System.out.println();
				System.out.print("1.*아이디 : ");
				userinfo.setAccountID(scan.nextLine().trim());
				
				if(userinfo.getAccountId().equals("")) {
					System.out.println("필수 입력 사항입니다!");
					continue;
				}
			
				//sql = "select * from Account where id = '" + userinfo.getAccountId() + "'";
				System.out.println(sql);
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					check = true;
				}
				
				if (check == true) {
					System.out.println("중복된 아이디 입니다!");
					continue;
				} 
				else {
					System.out.println("사용 가능한 아이디 입니다.");
				}
				
				/*
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

				System.out.println("회원가입에 성공하셨습니다!");

			}
		} catch (Exception e) {
			System.err.println("insert error : " + e.getMessage());
			System.exit(1);
		}
	}
}
