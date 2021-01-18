package enums;

public enum LoginStateEnum {
	/**
	 * 输入的用户名或者密码错误
	 */
	MISMATCH,
	/**
	 * 用户还未激活
	 */
	INACTIVATED,
	/**
	 * 用户可以成功登陆
	 */
	SUCCEED;
}
