package NCNC;

import java.util.Scanner;
import java.sql.*;

public class Main {

	// to get JDBC Connection
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER_NAME = "ncnc";
	public static final String USER_PASSWD = "ncnc";

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
		
		//FILTER 테이블 생성 유무 확인 후, 없으면 생성 아니면 넘어감.
		makeFilter();

		while (true) {
			// menu
			System.out.println("[니카내카]");
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 종료");

			Scanner scan = new Scanner(System.in);
			int menuNum;
			int subMenuNum;

			// 메뉴 번호 입력
			System.out.print("\n메뉴를 입력하세요 : ");
			menuNum = scan.nextInt();
			scan.nextLine();

			if (menuNum == 1) {
				boolean res = login();

				if (res == true && admin == false) {
					// menu 1-1 수행
					while(true) {
						
						System.out.println("1. 회원 정보 관리");
						System.out.println("2. 차량 검색");
						System.out.println("3. 거래 내역");
						System.out.println("4. 로그아웃");
						System.out.print("\n메뉴를 입력하세요 : ");

						subMenuNum = scan.nextInt();
						scan.nextLine();

						if (subMenuNum == 1) {

						} else if (subMenuNum == 2) {
							Menu2.main();

						} else if (subMenuNum == 3) {

						} else if (subMenuNum == 4) {
							Main.id = null;
							Main.admin = false;
							res = false;
							System.out.println("로그아웃 하셨습니다!");
							break;
						} else {
							System.out.println("잘못된 입력입니다!");
						}
						
					}

				} else if (res == true && admin == true) {

					while (true) {
						// menu 1-2 수헹
						System.out.println("1. 회원 정보 관리");
						System.out.println("2. 차량 검색");
						System.out.println("3. 거래 내역");
						System.out.println("4. 관리자 기능");
						System.out.println("5. 로그아웃");
						System.out.print("\n메뉴를 입력하세요 : ");

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
							System.out.println("로그아웃 하셨습니다!");
							break;

						} else {
							System.out.println("잘못된 입력입니다!");
						}
					}
				}

			} else if (menuNum == 2) {
				signup();
			} else if (menuNum == 3) {
				System.out.println("서비스를 종료합니다.");
				System.exit(0);
			} else {
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
					
					rs.close();
					stmt.close();
					
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
		StringBuffer sb = new StringBuffer();
		Account userinfo = new Account();
		Scanner scan = new Scanner(System.in);
		String temp = null;
		boolean ad = false;

		ResultSet rs = null;
		Statement stmt = null;

		try {

			stmt = conn.createStatement();

			System.out.println("[회원가입]");
			System.out.println("관리자용 회원가입이면 ADMIN을 입력하세요 : ");
			temp = scan.nextLine();

			if (temp.equals("ADMIN")) {
				ad = true;
				System.out.println("[관리자 회원가입]");
			} else {
				ad = false;
				System.out.println("[회원가입]");
			}

			System.out.println("아래 사항을 차례로 기입하세요.(1~9)");
			System.out.println("'*'는 필수 입력 사항입니다.");

			while (true) {
				sb.setLength(0);
				boolean check = false;

				System.out.println();
				System.out.print("1.*아이디 : ");
				userinfo.setAccountID(scan.nextLine().trim());

				if (userinfo.getAccountId().equals("")) {
					System.out.println("필수 입력 사항입니다!");
					continue;
				}

				sql = "select * from Account where id = '" + userinfo.getAccountId() + "'";
				// System.out.println(sql);

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					check = true;
				}

				if (check == true) {
					System.out.println("중복된 아이디 입니다!");
					continue;
				} else {
					System.out.println("사용 가능한 아이디 입니다.");
				}

				sb.append("insert into account values (" + "'" + userinfo.getAccountId() + "', ");

				System.out.print("2.*비밀번호 : ");
				userinfo.setAccountPW(scan.nextLine().trim());

				if (userinfo.getAccountPW().equals("")) {
					System.out.println("필수 입력 사항입니다!");
					continue;
				}

				sb.append("'" + userinfo.getAccountPW() + "', ");

				System.out.print("3.*이름(영문자 표기, 예 : HongGilDong) : ");
				userinfo.setName(scan.nextLine().trim());

				if (userinfo.getName().equals("")) {
					System.out.println("필수 입력 사항입니다!");
					continue;
				}

				sb.append("'" + userinfo.getName() + "', ");

				System.out.print("4.*전화번호('-'제외하고 입력) : ");
				userinfo.setPhone(scan.nextLine().trim());

				if (userinfo.getPhone().equals("")) {
					System.out.println("필수 입력 사항입니다!");
					continue;
				}

				sb.append("'" + userinfo.getPhone() + "', ");

				System.out.print("5.*이메일 : ");
				userinfo.setEmail(scan.nextLine().trim());

				if (userinfo.getEmail().equals("")) {
					System.out.println("필수 입력 사항입니다!");
					continue;
				}

				sb.append("'" + userinfo.getEmail() + "', ");

				System.out.print("6.*주소(도로명 주소 영문표기, 예 : GwanCheonro 17gil 2) : ");
				userinfo.setAddress(scan.nextLine().trim());

				if (userinfo.getAddress().equals("")) {
					System.out.println("필수 입력 사항입니다!");
					continue;
				}

				sb.append("'" + userinfo.getAddress() + "', ");

				System.out.print("7.성별(M/F) : ");
				userinfo.setGender(scan.nextLine().trim());

				if (!(userinfo.getGender().equals("M") || userinfo.getGender().equals("F")
						|| userinfo.getGender().equals(""))) {
					System.out.println("성별을 다시 입력하세요!");
					continue;
				}

				if (userinfo.getGender().equals("")) {
					sb.append("null, ");
				} else {
					sb.append("'" + userinfo.getGender() + "', ");
				}

				System.out.print("8.생년월일(예 : yyyy-mm-dd) : ");
				userinfo.setBirth(scan.nextLine().trim());

				if (userinfo.getBirth().equals("")) {
					sb.append("null, ");
				} else {
					sb.append("to_date('" + userinfo.getBirth() + "', 'yyyy-mm-dd'), ");
				}

				System.out.print("9.직업 : ");
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
					System.out.println("회원가입에 성공하셨습니다!");
					conn.commit();
					
					rs.close();
					stmt.close();
					
					break;
				} else {
					System.out.println("회원가입에 실패하셨습니다!");
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("connection error : " + e.getMessage());
			System.exit(1);
		}
	}

	public static void makeFilter() {
		String sql = null; 
		
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			sql = "select count(*) from all_tables where table_name = 'FILTER'";
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				if(rs.getInt("count(*)") == 0) {
					sql = "create table FILTER (ORDER_NUM number primary key, foreign key (ORDER_NUM) references ORDER_INFO(ORDER_NUM) on delete CASCADE)";
					
					int res = stmt.executeUpdate(sql);
					
					if( res == 0) {
						//System.out.println("정상적으로 filter가 만들어졌습니다.");
						conn.commit();
					}
					else {
						System.out.println("비정상적인 이유로 filter가 만들어지지않았습니다. table FILTER 유무를 확인해주세요.");
					}
				}
			}
			
			sql = "create or replace view Blind_info as select * from order_info O where not exists (select * from filter F where O.buyer is null and O.order_num = F.order_num) ";

			int res = stmt.executeUpdate(sql);
			
			if( res == 0 ) {
				//System.out.println("정상적으로 blind_info view가 만들어졌습니다.");
				conn.commit();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
