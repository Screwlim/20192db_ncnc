package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ncar {

	public static void main(String[] args) {

		//�޴�
		System.out.println("[��ī��ī]");
		System.out.println("1. �α���");
		System.out.println("2. ȸ������");
		System.out.println("3. ����");
		
		Scanner scan = new Scanner(System.in);
		int menuNum;
		
		while(true) {
			
			//�޴� ��ȣ �Է�
			System.out.print("\n�޴��� �Է��ϼ��� : ");
			menuNum = scan.nextInt();
			
			switch(menuNum) {
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
				default :
					System.out.println("�߸��� �Է��Դϴ�!");					
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
			
			System.out.println("[�α���]");
			
			while(true) {
				
				System.out.print("���̵� : ");
				loginID = scan.nextLine();
				
				System.out.print("��й�ȣ : ");
				loginPW = scan.nextLine();
				
				if(map.containsKey(loginID) == true && map.get(loginID).equals(loginPW) == true) {
					System.out.println("���������� �α��� �Ǿ����ϴ�.");
					break;
				}
				else {
					System.out.println("�������� ���� ���̵��̰ų�, �߸��� ��й�ȣ�Դϴ�.");
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
