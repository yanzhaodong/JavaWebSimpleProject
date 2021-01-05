package bizimpl;

import java.util.List;

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

		if (StringUtil.isEmpty(username)) {
			return UserLoginEnum.USER_NAME_IS_NUll.getDesc();
		}
		if (StringUtil.isEmpty(password)) {
			return UserLoginEnum.USER_PASSWORD_IS_NULL.getDesc();
		}
		if (StringUtil.isEmpty(validatecode) || StringUtil.isEmpty(syscode)) {
			return UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getDesc();
		}
		if (!validatecode.equals(syscode)) {
			return UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getDesc();
		}
		
		if ("0".equals(chance) & !"admin".equals(username)) {
			return UserLoginEnum.USER_FORBIDDEN.getDesc();
		}

		User user = userDAO.userLogin(username,password);
		
		if (user == null) {
			userDAO.userUpdateChance(username,chance);
			return UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getDesc();
		}
		// 登录成功后 把当前登录成功后的用户 存入到SESSION中 
		request.getSession().setAttribute("user", user);
		request.getSession().setMaxInactiveInterval(300);
		userDAO.userRecover(username);
		
		return UserLoginEnum.USER_LOGIN_SUCCESS.getDesc();
	}

	//用户检测
	public String userCheck(String username, String password, HttpServletRequest request) {
		if (StringUtil.isEmpty(username)) {
			return UserLoginEnum.USER_NAME_IS_NUll.getDesc();
		}
		if (StringUtil.isEmpty(password)) {
			return UserLoginEnum.USER_PASSWORD_IS_NULL.getDesc();
		}
		userDAO.userLogin(username,password);
		return UserLoginEnum.USER_LOGIN_SUCCESS.getDesc();
		
	}
	
	//用户注册
	public String userRegister(String username, String password, String againpassword,
			String email, HttpServletRequest req) {
		
		if (StringUtil.isEmpty(username)) {
			return UserRegisterEnum.USER_REGISTER_NAME_IS_NULL.getDesc();
		}
		if (StringUtil.isEmpty(password)) {
			return UserRegisterEnum.USER_REGISTER_PASSWORD_IS_NULL.getDesc();
		}
		if (password.length() < 6 | password.length() > 20) {
			return UserRegisterEnum.USER_PASSWORD_LENGTH_INVALID.getDesc();
		}
		if (StringUtil.isEmpty(email)) {
			return UserRegisterEnum.USER_REGISTER_EMAIL_IS_NULL.getDesc();
		}
		if(!password.equals(againpassword)){
			return UserRegisterEnum.USER_REGISTER_PASSWORDS_DISMATCH.getDesc();
		}
		
		User user = null;
		user = userDAO.userToRegister(username);
		if (user != null) {
			return UserRegisterEnum.USER_ALREADY_EXIST.getDesc();
		}
		
		Integer executeCount =  null;
		executeCount = userDAO.userRegister(username, password,email);
		if(executeCount > 0){
			return UserRegisterEnum.USER_REGISTER_SUCCESS.getDesc();
		}
		return null;
	}


	//用户恢复注册
	public void userRecover(String username, HttpServletRequest req) {
		userDAO.userRecover(username);
	}
	
	//得到所有用户
	public List<User> getForbiddenUsers(HttpServletRequest req){
		return userDAO.getForbiddenUsers();
	}
	
	//初始化管理员
	public void adminInit(HttpServletRequest req) {
		userDAO.adminInit();
	}
}
