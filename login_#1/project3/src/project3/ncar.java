package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ncar {

	public static void main(String[] args) {

		//메뉴
		System.out.println("[니카내카]");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료");
		
		Scanner scan = new Scanner(System.in);
		int menuNum;
		
		while(true) {
			
			//메뉴 번호 입력
			System.out.print("\n메뉴를 입력하세요 : ");
			menuNum = scan.nextInt();
			
			switch(menuNum) {
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
				default :
					System.out.println("잘못된 입력입니다!");					
			}			
		}
	}

	public static void login() {
		
		HashMap<String, String> map = new HashMap<String, String>();		
		account_DB adb = new account_DB();
		ArrayList<ncar_account> accounts = new ArrayList<ncar_account>();

		accounts = adb.getAccountLogin();
		Scanner scan = new Scanner(System.in);
		
		String loginID;
		String loginPW;
		
		try {			
			for(int i = 0; i<accounts.size(); i++) {
				map.put(accounts.get(i).getId(), accounts.get(i).getPW());
			}
			
			System.out.println("[로그인]");
			
			while(true) {
				
				System.out.print("아이디 : ");
				loginID = scan.nextLine();
				
				System.out.print("비밀번호 : ");
				loginPW = scan.nextLine();
				
				if(map.containsKey(loginID) == true && map.get(loginID).equals(loginPW) == true) {
					System.out.println("정상적으로 로그인 되었습니다.");
					break;
				}
				else {
					System.out.println("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
					continue;
				}
			}
		}catch (Exception e) {
	         System.err.println("login error : " + e.getMessage());
	         System.exit(1);
		}
	}
	
	public static void signup() {
		
		HashMap<String, String> map = new HashMap<String, String>();		
		account_DB adb = new account_DB();
		ArrayList<ncar_account> accounts = new ArrayList<ncar_account>();

		accounts = adb.getAccountLogin();
		Scanner scan = new Scanner(System.in);
		
		
	}
}
