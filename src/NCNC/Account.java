package NCNC;


//계정 아이디, 비번 getter,setter 클래스
public class Account {
	private String AccountID;
	private String AccountPW;
	private String Name;
	private String Phone;
	private String Email;
	private String Address;
	private String Gender;
	private String Birth;
	private String Job;
	private String IsAdmin;
	
	// 1. 아이디
	public String getAccountId() {
		return AccountID;
	}

	public void setAccountID(String AccountID) {
		this.AccountID = AccountID;
	}
	
	// 2. 비밀번호
	public String getAccountPW() {
		return AccountPW;
	}
	
	public void setAccountPW(String AccountPW) {
		this.AccountPW = AccountPW;
	}
		
	// 3. 이름
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	
	// 4. 전화번호
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String Phone) {
		this.Phone = Phone;
	}
	
	// 5. 이메일
	public String getEmail() {
		return Email;
	}
	public void setEmail(String Email) {
		this.Email = Email;
	}
		
	// 6. 주소
	public String getAddress() {
		return Address;
	}
	public void setAddress(String Address) {
		this.Address = Address;
	}
	
	// 7. 성별
	public String getGender() {
		return Gender;
	}
	public void setGender(String Gender) {
		this.Gender = Gender;
	}
	
	// 8. 생일
	public String getBirth() {
		return Birth;
	}
	public void setBirth(String Birth) {
		this.Birth = Birth;
	}
	
	// 9. 직업
	public String getJob() {
		return Job;
	}
	public void setJob(String Job) {
		this.Job = Job;
	}
	
	// 10. 관리자
	public String getIsAdmin() {
		return IsAdmin;
	}
	public void setAdmin(String IsAdmin) {
		this.IsAdmin = IsAdmin;
	}
}
