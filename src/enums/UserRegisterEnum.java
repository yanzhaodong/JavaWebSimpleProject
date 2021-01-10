package enums;

public enum UserRegisterEnum {
	USER_NAME_INVALID("用户名格式不规范:长度要在3-20之间且要以字母开头"),
	USER_PASSWORD_INVALID("注册密码格式不规范:长度要在6-20之间"),
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
