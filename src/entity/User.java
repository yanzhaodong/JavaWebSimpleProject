package entity;

public class User {
	private int userid;
	private String username;
	private String password;
	private String email;
	private int chance;
	
	public User() {
		
	}

	public User(int userid, String username, String email) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
	}

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int chance) {
		this.chance = chance;
	}

	
}
