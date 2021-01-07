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
import utils.ValUtil;

public class UserBIZImpl implements IUserBIZ {
	IUserDAO userDAO = new UserDAOImpl();
	
	//用户登录
	public String userLogin(HttpServletRequest request) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validatecode = request.getParameter("validatecode");
		String syscode = (String) request.getSession().getAttribute("syscode");
		String chance = userDAO.userGetChance(username);
		String desc = null;
		if (StringUtil.isEmpty(username)) {
			desc = UserLoginEnum.USER_NAME_IS_NUll.getDesc();
		}else if (StringUtil.isEmpty(password)) {
			desc =  UserLoginEnum.USER_PASSWORD_IS_NULL.getDesc();
		}else if (StringUtil.isEmpty(validatecode) || StringUtil.isEmpty(syscode)) {
			desc =  UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getDesc();
		}else if (!validatecode.equals(syscode)) {
			desc = UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getDesc();
		}else if ("0".equals(chance) & !"admin".equals(username)) {
			desc =  UserLoginEnum.USER_FORBIDDEN.getDesc();
		}else{
			User user = userDAO.userLogin(username,password);
			if (user == null) {
				userDAO.userUpdateChance(username,chance);
				desc =  UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getDesc();
			}else {
				// 登录成功后 把当前登录成功后的用户 存入到SESSION中 
				request.getSession().setAttribute("user", user);
				request.getSession().setMaxInactiveInterval(300);
				userDAO.userRecover(username);
				if ("admin".equals(username)) {
					IUserBIZ userBIZ = new UserBIZImpl();
					List<User> users = userBIZ.getForbiddenUsers(request);
					request.setAttribute("users", users);
					return "admin.jsp?msg="+UserLoginEnum.USER_LOGIN_SUCCESS.getDesc();
				}else {
					return "index.jsp?msg="+UserLoginEnum.USER_LOGIN_SUCCESS.getDesc();
				}
			}
		}
		return "user_login.jsp?msg=" + desc;
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
	public String userRegister(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String againpassword = request.getParameter("againpassword");
		String email = request.getParameter("email");
		
		String desc = null;
		if (StringUtil.isEmpty(username)) {
			desc = UserRegisterEnum.USER_REGISTER_NAME_IS_NULL.getDesc();
		}else if (StringUtil.isEmpty(password)) {
			desc = UserRegisterEnum.USER_REGISTER_PASSWORD_IS_NULL.getDesc();
		}else if (!ValUtil.checkPassword(password)) {
			desc = UserRegisterEnum.USER_PASSWORD_LENGTH_INVALID.getDesc();
		}else if (!ValUtil.checkEmail(email)) {
			desc = UserRegisterEnum.USER_REGISTER_EMAIL_IS_NULL.getDesc();
		}else if(!password.equals(againpassword)){
			desc = UserRegisterEnum.USER_REGISTER_PASSWORDS_DISMATCH.getDesc();
		}else {
			User user = userDAO.userToRegister(username);
			if (user != null) {
				desc =  UserRegisterEnum.USER_ALREADY_EXIST.getDesc();
			}else {
				Integer executeCount = userDAO.userRegister(username, password,email);
				if(executeCount > 0){
					return "user_login.jsp?msg=" + UserRegisterEnum.USER_REGISTER_SUCCESS.getDesc();
				}
			}
		}
		return "user_register.jsp?msg=" + desc;
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
