package enums;

public enum UserRegisterEnum {
	USER_NAME_INVALID("user_register_name_is_null","用户注册名格式不规范"),
	USER_PASSWORD_INVALID("user_register_password_is_null","注册密码格式不规范"),
	USER_EMAIL_INVALID("user_register_email_is_null","注册邮箱格式不规范"),
	USER_PASSWORDS_DISMATCH("user_register_passwords_dismatch","密码不一致"),
	USER_ALREADY_EXIST("user_already_exist","用户已存在"),
	USER_REGISTER_SUCCESS("user_register_success","success");
	
	
	private String value;
	private String desc;
	
	private UserRegisterEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
