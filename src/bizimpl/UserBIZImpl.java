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
import utils.UuidUtil;
import utils.ValUtil;
import utils.CastUtil;
import utils.MailUtil;
import utils.Md5;

public class UserBIZImpl implements IUserBIZ {
	IUserDAO userDAO = new UserDAOImpl();
	
	/*
	 * 用户登录
	 */
	public String userLogin(HttpServletRequest request) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validatecode = request.getParameter("validatecode");
		String syscode = CastUtil.cast(request.getSession().getAttribute("syscode"));
		int chance = userDAO.userGetChance(username);
		String value = null;
		if (StringUtil.isEmpty(username)) {
			value = UserLoginEnum.USER_NAME_IS_NUll.getValue();
		}else if (StringUtil.isEmpty(password)) {
			value =  UserLoginEnum.USER_PASSWORD_IS_NULL.getValue();
		}else if (StringUtil.isEmpty(validatecode) || StringUtil.isEmpty(syscode)) {
			value =  UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}else if (!validatecode.equals(syscode)) {
			value = UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}else if (chance == 0 & !"admin".equals(username)) {
			value =  UserLoginEnum.USER_FORBIDDEN.getValue();
		}else{
			User user = userDAO.userLogin(username,password);
			if (user == null) {
				userDAO.userUpdateChance(username,chance);
				value =  UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getValue();
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
		return "alert"+value;
	}
	
	/*
	 * 用户注册
	 */
	public String userRegister(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String againpassword = request.getParameter("againpassword");
		String email = request.getParameter("email");
		
		String code = UuidUtil.getUuid();                 //生成随机校验码uuid
		String encyptedCode = "";
		System.out.println("uuid code is: "+code);
		try {
			encyptedCode = Md5.EncoderByMd5(code);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("saved code is: "+encyptedCode);
		
		String value = "";
		if (!ValUtil.checkUsername(username)) {
			value = UserRegisterEnum.USER_NAME_INVALID.getValue();
		}else if (!ValUtil.checkPassword(password)) {
			value = UserRegisterEnum.USER_PASSWORD_INVALID.getValue();
		}else if (!ValUtil.checkEmail(email)) {
			value = UserRegisterEnum.USER_EMAIL_INVALID.getValue();
		}else if(!password.equals(againpassword)){
			value = UserRegisterEnum.USER_PASSWORDS_DISMATCH.getValue();
		}else {
			User user = userDAO.usernameRegisterCheck(username);
			if (user != null) {
				value =  UserRegisterEnum.USER_ALREADY_EXIST.getValue();
			}else {
				//request.getSession().setAttribute("username", username);
				MailUtil.sendMail(email, code, username);
				Integer executeCount = userDAO.userRegister(username, password, email, encyptedCode);
				if(executeCount > 0){
					return "user_login.jsp?msg=success";
				}
			}
		}
		return "$"+value;
	}

	/*
	 *  激活用户
	 */
	public String activateUser(HttpServletRequest request){
		String code = request.getParameter("code");      //从网页过来的未加密的信息
		String destination = "";
		try {
			System.out.println("html raw code is: "+code);
			code = Md5.EncoderByMd5(code);              //重新加密，看能否跟储存的信息匹配
			System.out.println("html encrypted code is: "+code);
			String username = userDAO.checkCode(code);
			if (username != null) {
				userDAO.activateUser(username);
				destination =  "user_login.jsp";
			}else {
				destination = "user_register.jsp?msg=failed";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}
	
	
	/*
	 * 用户恢复注册
	 */
	public String userRecover(HttpServletRequest request) {
		String username = request.getParameter("username");
		userDAO.userRecover(username);
		List<User> users = CastUtil.cast(request.getSession().getAttribute("forbidden_users"));
		for (User user:users) {
			if (username != null & user.getUsername().equals(username)){
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
	


}
