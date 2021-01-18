package enums;

public enum UserLoginEnum {
	USER_NAME_IS_NUll("用户名不能为空"),
	USER_PASSWORD_IS_NULL("密码不能为空"),
	USER_NAME_OR_PASSWORD_IS_FAIL("用户名或密码错误"),
	USER_FORBIDDEN("用户已被禁用"),
	USER_NOT_EXIST("用户不存在"),
	USER_VALIDATE_CODE_IS_FAIL("验证码错误"),
	USER_LOGIN_SUCCESS("登录成功"),
	USER_NOT_ACTIVATED("用户未激活");
	
	public String value;
	
	private UserLoginEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
