package enums;

public enum UserRegisterEnum {
	USER_NAME_INVALID("用户注册名格式不规范"),
	USER_PASSWORD_INVALID("注册密码格式不规范"),
	USER_EMAIL_INVALID("注册邮箱格式不规范"),
	USER_PASSWORDS_DISMATCH("密码不一致"),
	USER_ALREADY_EXIST("用户已存在");
	
	
	private String value;
	
	private UserRegisterEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
