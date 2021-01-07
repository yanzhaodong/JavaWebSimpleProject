package utils;

public class ValUtil {
	public static boolean checkPassword(String password) {
		if (password.length() < 6 || password.length()>20) {
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean checkEmail(String email) {
		String regex = "[a-zA-Z]{1,10}@[a-zA-Z0-9]{1,}[.][c][o][m]";                            //.com结尾的邮箱
		String regex1 = "[a-zA-Z]{1,10}@[a-zA-Z0-9]{1,}[.][c][o][m][a-zA-Z0-9]{0,}[.][c][n]";	//.cn结尾的邮箱

		if (email.matches(regex) || email.matches(regex1)) {
			return true;
		} else {
			return false;
		}

	}
}
