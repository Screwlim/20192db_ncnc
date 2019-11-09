package project3;


//계정 아이디, 비번 getter,setter 클래스
public class ncar_account {

	private String login_id;
	private String login_pw;
	private String Name;
	private String Phone;
	private String Email;
	private String Address;
	private String Gender;
	private String Birth;
	private String Job;
	private String IsAdmin;
	
	// 1. 아이디
	public String getId() {
		return login_id;
	}

	public void setID(String login_id) {
		this.login_id = login_id;
	}
	
	// 2. 비밀번호
	public String getPW() {
		return login_pw;
	}
	
	public void setPW(String login_pw) {
		this.login_pw = login_pw;
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
