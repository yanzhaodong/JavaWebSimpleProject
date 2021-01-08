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
import utils.CastUtil;

public class UserBIZImpl implements IUserBIZ {
	IUserDAO userDAO = new UserDAOImpl();
	
	/*
	 * 用户登录
	 */
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
				request.getSession().setAttribute("username", username);
				request.getSession().setMaxInactiveInterval(300);
				userDAO.userRecover(username);
				if ("admin".equals(username)) {
					IUserBIZ userBIZ = new UserBIZImpl();
					List<User> forbidden_users = userBIZ.getForbiddenUsers(request);
					request.getSession().setAttribute("forbidden_users", forbidden_users);
					return "admin.jsp";
				}else {
					return "index.jsp";
				}
			}
		}
		return "alert"+desc;
	}
	
	/*
	 * 用户注册
	 */
	public String userRegister(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String againpassword = request.getParameter("againpassword");
		String email = request.getParameter("email");
		
		String desc = null;
		if (StringUtil.isEmpty(username)) {
			desc = UserRegisterEnum.USER_NAME_INVALID.getDesc();
		}else if (StringUtil.isEmpty(password)) {
			desc = UserRegisterEnum.USER_PASSWORD_INVALID.getDesc();
		}else if (!ValUtil.checkEmail(email)) {
			desc = UserRegisterEnum.USER_EMAIL_INVALID.getDesc();
		}else if(!password.equals(againpassword)){
			desc = UserRegisterEnum.USER_PASSWORDS_DISMATCH.getDesc();
		}else {
			User user = userDAO.userToRegister(username);
			if (user != null) {
				desc =  UserRegisterEnum.USER_ALREADY_EXIST.getDesc();
			}else {
				Integer executeCount = userDAO.userRegister(username, password,email);
				if(executeCount > 0){
					return "user_login.jsp?msg="+UserRegisterEnum.USER_REGISTER_SUCCESS.getDesc();
				}
			}
		}
		return "alert"+desc;
	}

	/*
	 * 用户恢复注册
	 */
	public String userRecover(HttpServletRequest request) {
		String username = request.getParameter("username");
		userDAO.userRecover(username);
		List<User> users = CastUtil.cast(request.getSession().getAttribute("forbidden_users"));
		for (User user:users) {
			if (username != null & username.equals(user.getUsername())){
				users.remove(user);						
				request.getSession().setAttribute("forbidden_users", users); //恢复后更新被禁止的用户
				break;
			}
		}
		return "admin.jsp";
	}
	
	/*
	 * 得到所有用户
	 */
	public List<User> getForbiddenUsers(HttpServletRequest req){
		return userDAO.getForbiddenUsers();
	}
	
	/*
	 * 初始化管理员
	 */
	public void adminInit(HttpServletRequest req) {
		userDAO.adminInit();
	}
}
