package bizimpl;

import javax.servlet.http.HttpServletRequest;

import biz.IUserBIZ;
import dao.IUserDAO;
import daoimpl.UserDAOImpl;
import entity.User;
import enums.UserLoginEnum;
import enums.UserRegisterEnum;
import utils.StringUtil;

public class UserBIZImpl implements IUserBIZ {
	IUserDAO userDAO = new UserDAOImpl();
	
	//用户登录
	public String userLogin(String username, String password,
			String validatecode, String syscode, HttpServletRequest request) {
		String chance = userDAO.userGetChance(username);
		if (chance==null) {
			chance = "0";}
		if (StringUtil.isEmpty(username)) {
			return UserLoginEnum.USER_NAME_IS_NUll.getValue();
		}
		if (StringUtil.isEmpty(password)) {
			return UserLoginEnum.USER_PASSWORD_IS_NULL.getValue();
		}
		if (StringUtil.isEmpty(validatecode) || StringUtil.isEmpty(syscode)) {
			return UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}
		if (!validatecode.equals(syscode)) {
			return UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}
		
		if (chance.equals("0")) {
			return UserLoginEnum.USER_FORBIDDEN.getValue();
		}
		
		User user = null;
		user = userDAO.userLogin(username,password);
		
		if (user == null) {
			userDAO.userUpdateChance(username,chance);
			return UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getValue();
		}
		// 登录成功后 把当前登录成功后的用户 存入到SESSION中 基本是 所有后台的必备功能
		request.getSession().setAttribute("user", user);
		
		return UserLoginEnum.USER_LOGIN_SUCCESS.getValue();
	}

	//用户检测
	public String userCheck(String username, String password, HttpServletRequest request) {
		if (StringUtil.isEmpty(username)) {
			return UserLoginEnum.USER_NAME_IS_NUll.getValue();
		}
		if (StringUtil.isEmpty(password)) {
			return UserLoginEnum.USER_PASSWORD_IS_NULL.getValue();
		}
		

		userDAO.userLogin(username,password);
		return UserLoginEnum.USER_LOGIN_SUCCESS.getValue();
		
	}
	
	//用户注册
	public String userRegister(String username, String password, String againpassword,
			String email, HttpServletRequest req) {
		
		if (StringUtil.isEmpty(username)) {
			return UserRegisterEnum.USER_REGISTER_NAME_IS_NULL.getValue();
		}
		if (StringUtil.isEmpty(password)) {
			return UserRegisterEnum.USER_REGISTER_PASSWORD_IS_NULL.getValue();
		}
		if (StringUtil.isEmpty(email)) {
			return UserRegisterEnum.USER_REGISTER_EMAIL_IS_NULL.getValue();
		}
		if(!password.equals(againpassword)){
			return UserRegisterEnum.USER_REGISTER_PASSWORDS_DISMATCH.getValue();
		}
		
		User user = null;
		user = userDAO.userToRegister(username);
		if (user != null) {
			return UserRegisterEnum.USER_ALREADY_EXIST.getValue();
		}
		
		Integer executeCount =  null;
		executeCount = userDAO.userRegister(username, password,email);
		if(executeCount > 0){
			return UserRegisterEnum.USER_REGISTER_SUCCESS.getValue();
		}
		return null;
	}


	//用户恢复注册
	public void userRecover(String id, HttpServletRequest req) {
		userDAO.userRecover(id);
	}
	

}
