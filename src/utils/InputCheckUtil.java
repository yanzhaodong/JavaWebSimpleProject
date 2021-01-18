package utils;

import enums.UserLoginEnum;
import enums.UserRegisterEnum;

public class InputCheckUtil {
	/**
	 * 简单验证登录界面的输入是否规范
     * @methodsName: registerCheck
     * @param: username          输入的用户名
     * @param: password          输入的密码
     * @param: validatecode      输入的验证码
     * @param: syscode           真实的验证码
     * @return: String		     应当返回的信息
	 */
	public static String loginCheck(String username, String password, String validatecode, String syscode) {
		String value = null;
		if (StringUtil.isEmpty(username)) {                                         
			value = UserLoginEnum.USER_NAME_IS_NUll.getValue();
		}else if (StringUtil.isEmpty(password)) {									 
			value =  UserLoginEnum.USER_PASSWORD_IS_NULL.getValue();
		}else if (StringUtil.isEmpty(validatecode) || StringUtil.isEmpty(syscode)) { 
			value =  UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}else if (!validatecode.equals(syscode)) {									 
			value = UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}
		return value;
	}
	
	/**
	 * 简单验证注册界面的输入是否规范
     * @methodsName: registerCheck
     * @param: username          输入的用户名
     * @param: password          输入的密码
     * @param: email             输入的邮箱
     * @param: againpassword     输入的确认密码
     * @return: String		     应当返回的信息
	 */
	public static String registerCheck(String username, String password, String email, String againpassword) {
		String value = null;
		if (!FormatValidationUtil.checkEmail(email)) {
			value = UserRegisterEnum.USER_EMAIL_INVALID.getValue();
		} else if (!FormatValidationUtil.checkUsername(username)) {
			value = UserRegisterEnum.USER_NAME_INVALID.getValue();
		}else if (!FormatValidationUtil.checkPassword(password)) {					
			value = UserRegisterEnum.USER_PASSWORD_INVALID.getValue();
		}else if(!password.equals(againpassword)){						
			value = UserRegisterEnum.USER_PASSWORDS_DISMATCH.getValue();
		}
		return value;
	}
	

}
