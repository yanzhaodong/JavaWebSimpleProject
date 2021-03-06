package utils;

/**
* 用户注册验证的工具类
* @author 严照东
*/
public class FormatValidationUtil {
	
	/**
	 * 验证邮箱是否规范
     * @methodsName: checkEmail
     * @param: password          输入的邮箱
     * @return: boolean		     邮箱格式是否规范
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+\\.?\\w*\\w@(\\w+\\.)+\\w+";                            //.com结尾的邮箱
		//String regex1 = "[a-zA-Z0-9]{1,}@[a-zA-Z0-9]{1,}[.][c][o][m][a-zA-Z0-9]{0,}[.][c][n]";	  //.cn结尾的邮箱
		if (email.matches(regex)) {
			return true;
		} 
		return false;
	}

	/**
	 * 验证用户名是否规范:长度要在3-20之间
     * @methodsName: checkUsername
     * @param: password          输入的用户名
     * @return: boolean		     用户名格式是否规范
	 */
	public static boolean checkUsername(String username) {
		String regex = "^[a-zA-Z][\\w_]{2,19}$";            
		if (username.matches(regex)) {
			return true;
		} 
		return false;

	}
	
	/**
	 * 验证密码是否规范：长度要在6到20之间
     * @methodsName: checkPassword
     * @param: password          输入的密码
     * @return: boolean		     密码格式是否规范
	 */
	public static boolean checkPassword(String password) {
		if (password.length() < 6 || password.length()>20) {
			return false;
		}
		return true;

	}
}
