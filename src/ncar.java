package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import project3.ncar_account;
import project3.account_DB;


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
				map.put(accounts.get(i).getAccountId(), accounts.get(i).getAccountPW());				
			}
			
			System.out.println("[�α���]");
			
			while(true) {
				
				System.out.print("���̵� : ");
				loginID = scan.nextLine();
				
				System.out.print("��й�ȣ : ");
				loginPW = scan.nextLine();
				
				if(map.containsKey(loginID) && (map.get(loginID)).equals(loginPW)) {
					System.out.println("�α��ο� �����Ͽ����ϴ�.");
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
		
		//���̵�, ��й�ȣ �˻��
		for(int i = 0; i<accounts.size(); i++) {				
			map.put(accounts.get(i).getAccountId(), accounts.get(i).getAccountPW());				
		}
		
		try {
			
			System.out.println("[ȸ������]");
			System.out.println("�Ʒ� ������ ���ʷ� �����ϼ���.(1~9)");
			System.out.println("'*'�� �ʼ� �Է� �����Դϴ�.");
			
			while(true) {
				
				while(true) {
					System.out.println();
					System.out.print("1.*���̵� : ");
					userID = scan.nextLine();
					
					if(map.containsKey(userID)){
						System.out.println("�ߺ��� ���̵� �Դϴ�!");
					}
					else {
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
					sql = "INSERT INTO ACCOUNT VALUES ('" + userID + "'" + userPW + "'" + Name + "'" + 
							Phone + "'" + Email + "'" + Address + "'" + Gender + "'" + Birth + "'" + Job + ")";
				}catch (Exception e) {
			        System.err.println("insert error : " + e.getMessage());
			        System.exit(1);
				}
				
				System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�!");
				
			}
	}catch (Exception e) {
        System.err.println("insert error : " + e.getMessage());
        System.exit(1);
	}
	}
}
