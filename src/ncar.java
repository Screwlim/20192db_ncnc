package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import project3.ncar_account;
import project3.account_DB;


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
				map.put(accounts.get(i).getAccountId(), accounts.get(i).getAccountPW());				
			}
			
			System.out.println("[로그인]");
			
			while(true) {
				
				System.out.print("아이디 : ");
				loginID = scan.nextLine();
				
				System.out.print("비밀번호 : ");
				loginPW = scan.nextLine();
				
				if(map.containsKey(loginID) && (map.get(loginID)).equals(loginPW)) {
					System.out.println("로그인에 성공하였습니다.");
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
		
		String userID;
		String userPW;
		String Name;
		String Phone;
		String Email;
		String Address;
		String Gender;
		String Birth;
		String Job;
		String IsAdmin;
		String sql = "";
		
		HashMap<String, String> map = new HashMap<String, String>();		
		account_DB adb = new account_DB();
		ArrayList<ncar_account> accounts = new ArrayList<ncar_account>();

		accounts = adb.getAccountLogin();
		Scanner scan = new Scanner(System.in);
		
		//아이디, 비밀번호 검사용
		for(int i = 0; i<accounts.size(); i++) {				
			map.put(accounts.get(i).getAccountId(), accounts.get(i).getAccountPW());				
		}
		
		try {
			
			System.out.println("[회원가입]");
			System.out.println("아래 사항을 차례로 기입하세요.(1~9)");
			System.out.println("'*'는 필수 입력 사항입니다.");
			
			while(true) {
				
				while(true) {
					System.out.println();
					System.out.print("1.*아이디 : ");
					userID = scan.nextLine();
					
					if(map.containsKey(userID)){
						System.out.println("중복된 아이디 입니다!");
					}
					else {
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
					sql = "INSERT INTO ACCOUNT VALUES ('" + userID + "'" + userPW + "'" + Name + "'" + 
							Phone + "'" + Email + "'" + Address + "'" + Gender + "'" + Birth + "'" + Job + ")";
				}catch (Exception e) {
			        System.err.println("insert error : " + e.getMessage());
			        System.exit(1);
				}
				
				System.out.println("회원가입에 성공하셨습니다!");
				
			}
	}catch (Exception e) {
        System.err.println("insert error : " + e.getMessage());
        System.exit(1);
	}
	}
}
