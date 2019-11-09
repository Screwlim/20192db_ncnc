package project3;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class account_DB {
	
		public static final String URL = "jdbc:oracle:thin:@155.230.36.61:1521:orcl";
		public static final String USER_KNU = "s2015110533";
		public static final String USER_PASSWD = "2015110533";
	
		public ArrayList<ncar_account> getAccountLogin() {
			
			
			Connection conn = null;			
			PreparedStatement preState = null;	
			ResultSet result = null;			
			
			try {
		         // Load a JDBC driver for Oracle DBMS
		         Class.forName("oracle.jdbc.driver.OracleDriver");
		         // Get a Connection object
		         System.out.println("Success!");

		    } catch (ClassNotFoundException e) {
		         System.err.println("error = " + e.getMessage());
		         System.exit(1);
		    }

		    // Make a connection
		    try {
		    	conn = DriverManager.getConnection(URL, USER_KNU, USER_PASSWD);
		    } catch (SQLException e) {
		    	System.err.println("Cannot get a connection: " + e.getMessage());
		        System.exit(1);
		    }

		    		      
			ArrayList<ncar_account> accounts = new ArrayList<ncar_account>();
					
			
			String sql = "SELECT * FROM ACCOUNT";
			
			try {
				preState = conn.prepareStatement(sql);		//ĳ�ÿ� ��⶧���� �ݺ��۾����� statement���� ����
				result = preState.executeQuery();			//�׳� true,false�� ��ȯ�ϴ� excute�� �޸� ResultSet ��ü�� ������� ���� �� �ִ�.
				
				while(result.next()) {
					
					ncar_account account = new ncar_account();	
					
					account.setAccountID(result.getString("ID"));
					account.setAccountPW(result.getString("PASSWORD"));

					//System.out.println(result.getString("ID") + " " + result.getString("PASSWORD"));
					
					accounts.add(account);
					
					//System.out.println(accounts.get(0).getAccountId() + " " + accounts.get(0).getAccountPW());
				}
				
				try {
					conn.close();
					result.close();
					preState.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}catch (SQLException e) {
		         System.err.println("sql error = " + e.getMessage());
		         System.exit(1);
		    }
			
			return accounts;
		}
		
		public ArrayList<ncar_account> getAccountSignup() {
			
			
			Connection conn = null;			
			PreparedStatement preState = null;	
			ResultSet result = null;			
			
			try {
		         // Load a JDBC driver for Oracle DBMS
		         Class.forName("oracle.jdbc.driver.OracleDriver");
		         // Get a Connection object
		         System.out.println("Success!");

		    } catch (ClassNotFoundException e) {
		         System.err.println("error = " + e.getMessage());
		         System.exit(1);
		    }

		    // Make a connection
		    try {
		    	conn = DriverManager.getConnection(URL, USER_KNU, USER_PASSWD);
		    } catch (SQLException e) {
		    	System.err.println("Cannot get a connection: " + e.getMessage());
		        System.exit(1);
		    }
			
			return null;
		}
}
