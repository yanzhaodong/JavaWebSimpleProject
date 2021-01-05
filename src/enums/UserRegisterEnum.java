package enums;

public enum UserRegisterEnum {
	USER_REGISTER_NAME_IS_NULL("user_register_name_is_null","用户注册名不能为空"),
	USER_REGISTER_PASSWORD_IS_NULL("user_register_password_is_null","注册密码不能为空"),
	USER_REGISTER_EMAIL_IS_NULL("user_register_email_is_null","注册邮箱不能为空"),
	USER_REGISTER_PASSWORDS_DISMATCH("user_register_passwords_dismatch","密码不一致"),
	USER_ALREADY_EXIST("user_already_exist","用户已存在"),
	USER_REGISTER_SUCCESS("user_register_success","注册成功");
	
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
