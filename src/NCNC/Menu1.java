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

		System.out.println("[회원 정보 기능]");

		while (true) {

			System.out.println("\n1. 회원 정보 수정");
			System.out.println("2. 비밀번호 수정");
			System.out.println("3. 회원 탈퇴");
			System.out.println("4. 돌아가기");
			System.out.println("\n메뉴를 입력하세요 : ");

			menuNum = scan.nextInt();

			if (menuNum == 1) {
				adjustInfo();
			} else if (menuNum == 2) {
				adjustPW();
			} else if (menuNum == 3) {
				dropAccount();
			} else if (menuNum == 4) {
				System.out.println("회원 정보 서비스를 종료합니다.");
				break;
			} else {
				System.out.println("잘못된 입력입니다!");
			}
		}
	}

	// 회원 정보 수정 메뉴
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

		System.out.println("\n[회원 정보 수정 서비스]\n");

		// 우선 차량 번호 받고, 있는지 조사한 후, 수정할 값을 받는다.

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

				System.out.println("\n[수정하고 싶으신 정보의 번호를 입력하세요.]");
				System.out.println("1. 이름");
				System.out.println("2. 전화번호");
				System.out.println("3. 이메일 주소");
				System.out.println("4. 집주소");
				System.out.println("5. 성별");
				System.out.println("6. 생일");
				System.out.println("7. 직업");
				System.out.println("8. 돌아가기");
				System.out.println("\n메뉴를 입력하세요 : ");

				menuNum = scan.nextInt();
				scan.nextLine();// 개행 문자 제가

				rs.close();

				if (menuNum == 1) {

					System.out.print("현재 회원님의 이름입니다 :");
					System.out.println(Name);

					System.out.print("변경하실 이름을 입력해 주세요 : ");
					adName = scan.nextLine();

					sql1 = "update account set name= '" + adName + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n변경이 완료되었습니다.\n");

					rs1.close();

				} else if (menuNum == 2) {

					System.out.print("현재 회원님의 전화번호입니다 :");
					System.out.println(Phonenum);

					System.out.print("변경하실 전화번호를 입력해 주세요 : ");
					adPhonenum = scan.nextLine();

					sql1 = "update account set phone_num = '" + adPhonenum + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n변경이 완료되었습니다.\n");

					rs1.close();

				} else if (menuNum == 3) {

					System.out.print("현재 회원님의 이메일 주소입니다 :");
					System.out.println(Email);

					System.out.print("변경하실 이메일 주소를 입력해 주세요 : ");
					adEmail = scan.nextLine();

					sql1 = "update account set email_address = '" + adEmail + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n변경이 완료되었습니다.\n");

					rs1.close();

				} else if (menuNum == 4) {

					System.out.print("현재 회원님의 집주소입니다 :");
					System.out.println(Address);

					System.out.print("변경하실 집주소를 입력해 주세요 : ");
					adAddress = scan.nextLine();

					sql1 = "update account set email_address = '" + adAddress + "' where id = '" + Main.id + "'";
					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n변경이 완료되었습니다.\n");

					rs1.close();

				} else if (menuNum == 5) {

					System.out.print("현재 회원님의 성별은 ");

					if (Gender.equals("M")) {
						System.out.println("'남성' 입니다.");

						sql1 = "update account set gender = 'F' where id = '" + Main.id + "'";
					} else if (Gender.equals("F")) {
						System.out.println("'여성' 입니다.");

						sql1 = "update account set gender = 'M' where id = '" + Main.id + "'";
					}

					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n변경이 완료되었습니다.\n");
					rs1.close();

				} else if (menuNum == 6) {

					System.out.print("현재 회원님의 생일날짜입니다 :");
					System.out.println(Birth);

					System.out.print("변경하실 생일날짜를 입력해 주세요 : ");
					adBirth = scan.nextLine();

					if (adBirth.equals("")) {
						sql1 = "update account set birth_date = null where id = '" + Main.id + "'";
					} else {
						sql1 = "update account set birth_date = '" + adBirth + "' where id = '" + Main.id + "'";
					}

					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n변경이 완료되었습니다.\n");

					rs1.close();

				} else if (menuNum == 7) {

					System.out.print("현재 회원님의 직업입니다 :");
					System.out.println(Job);

					System.out.print("변경하실 직업을 입력해 주세요 : ");
					adJob = scan.nextLine();

					if (adJob.equals("")) {
						sql1 = "update account set job = null where id = '" + Main.id + "'";
					} else {
						sql1 = "update account set job = '" + adJob + "' where id = '" + Main.id + "'";
					}

					rs1 = stmt.executeQuery(sql1);
					Main.conn.commit();

					System.out.print("\n변경이 완료되었습니다.\n");

					rs1.close();

				} else if (menuNum == 8) {
					System.out.println("\n회원 정보 수정 서비스를 종료합니다.\n");
					break;
				} else {
					System.out.println("잘못된 입력입니다!");
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 비밀번호 수정기능
	private static void adjustPW() {

		Scanner scan = new Scanner(System.in);
		String newPW;

		String sql = null;

		try {
			stmt = Main.conn.createStatement();

			System.out.println("[비밀번호 변경 서비스]");
			System.out.print("변경하실 비밀번호를 입력해 주세요 : ");
			newPW = scan.nextLine();

			sql = "update account set password= '" + newPW + "' where id = '" + Main.id + "'";
			rs = stmt.executeQuery(sql);
			Main.conn.commit();

			System.out.print("\n변경이 완료되었습니다.\n");
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

			System.out.println("[회원탈퇴 서비스]");

			try {
				rs = stmt.executeQuery("SELECT COUNT(*) FROM ACCOUNT WHERE is_admin = 'T'");
				if (rs.next())
					Count = rs.getInt(1);
				System.out.println("현재 관리자 수 : " + Count);

				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Main.admin && Count == 1) {
				System.out.println("지금 접속하신 관리자님은 유일한 관리자 입니다!");
				System.out.println("탈퇴 기능을 이용하실 수 없습니다.");
			}

			else {
				System.out.println("정말로 회원을 탈퇴하시겠습니까?(y or n)");

				answer = scan.nextLine();

				if (answer.equals("y")) {
					sql = "delete from account where id='" + Main.id + "'";
					rs = stmt.executeQuery(sql);
					Main.conn.commit();
					rs.close();

					System.out.println("\n탈퇴가 완료되었습니다.");
					System.out.println("지금까지 저희 서비를 이용해주셔서 감사합니다.");
					System.out.println("서비스를 종료합니다.");
					System.exit(0);

				} else if (answer.equals("n")) {
					System.out.println("메뉴로 돌아갑니다.");
				} else {
					System.out.println("잘못된 입력입니다. 메뉴로 돌아갑니다.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
