package bizimpl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import biz.IUserBIZ;
import dao.IUserDAO;
import dao.IUserStateDAO;
import daoimpl.UserDAOImpl;
import daoimpl.UserStateDAOImpl;
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
	IUserStateDAO userStateDAO = new UserStateDAOImpl();
	/*
     * @methodsName: userLogin
     * @description: 用户登录
     * @param:  request      
     * @return: String           如果以$开头，则显示为显示的警告信息；否则是页面将要跳转的地址
	 */
	public String userLogin(HttpServletRequest request) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validatecode = request.getParameter("validatecode");
		String syscode = CastUtil.cast(request.getSession().getAttribute("syscode"));
		int chance = userStateDAO.userGetChance(username);
		String value = null;
		if (StringUtil.isEmpty(username)) {                                          //用户名为空
			value = UserLoginEnum.USER_NAME_IS_NUll.getValue();
		}else if (StringUtil.isEmpty(password)) {									 //密码为空
			value =  UserLoginEnum.USER_PASSWORD_IS_NULL.getValue();
		}else if (StringUtil.isEmpty(validatecode) || StringUtil.isEmpty(syscode)) { //验证码为空
			value =  UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}else if (!validatecode.equals(syscode)) {									 //验证码不一致
			value = UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue();
		}else if (chance == 0 & !"admin".equals(username)) {						 //验用户已被禁用
			value =  UserLoginEnum.USER_FORBIDDEN.getValue();
		}else{
			int result = userDAO.userLogin(username,password);
			//根据返回的状态码判断是否成功登录
			try{
				switch (result) {
					case 0:                 //用户名密码不匹配
						userStateDAO.userUpdateChance(username);
						value =  UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getValue();
						break;
					case 1:					//用户名未激活
						value =  UserLoginEnum.USER_NOT_ACTIVATED.getValue();
						break;
					case 2:					//成功登录
						// 登录成功后 把当前登录成功后的用户 存入到SESSION中 
						request.getSession().setAttribute("username", username);
						request.getSession().setMaxInactiveInterval(300);
						userStateDAO.userRecover(username);
						if ("admin".equals(username)) {
							//对于admin，把被禁用用户存到session里，以便及时更新
							IUserBIZ userBIZ = new UserBIZImpl();
							List<User> forbidden_users = userBIZ.getForbiddenUsers();
							request.getSession().setAttribute("forbidden_users", forbidden_users);
							return "admin.jsp";
						}else {
							return "index.jsp";
						}
					default:
						throw new RuntimeException("登陆返回的状态码无法识别");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "$"+value;
	}
	
	/*
     * @methodsName: userRegister
     * @description: 用户注册
     * @param:  request      
     * @return: String          如果以$开头，则显示为显示的警告信息；否则是页面将要跳转的地址
	 */
	public String userRegister(HttpServletRequest request)  {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String againpassword = request.getParameter("againpassword");
		String email = request.getParameter("email");
		
		String code = UuidUtil.getUuid();                 //生成随机校验码uuid
		String encyptedCode = "";
		String value = "";
		try {
			encyptedCode = Md5.EncoderByMd5(code);
			
			if (!ValUtil.checkUsername(username)) {							//用户名不合法
				value = UserRegisterEnum.USER_NAME_INVALID.getValue();
			}else if (!ValUtil.checkPassword(password)) {					//密码不合法
				value = UserRegisterEnum.USER_PASSWORD_INVALID.getValue();
			}else if (!ValUtil.checkEmail(email)) {							//邮箱不合法
				value = UserRegisterEnum.USER_EMAIL_INVALID.getValue();
			}else if(!password.equals(againpassword)){						//密码不一致
				value = UserRegisterEnum.USER_PASSWORDS_DISMATCH.getValue();
			}else {													
				User user = userDAO.usernameRegisterCheck(username);
				if (user != null) {											//注册的用户名已存在且已激活
					value =  UserRegisterEnum.USER_ALREADY_EXIST.getValue();
				}else {
					MailUtil.sendMail(email, code);
					Integer executeCount = userDAO.userRegister(username, password, email, encyptedCode);
					if(executeCount > 0){
						return "waiting.jsp";
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "$"+value;
	}

	/*
     * @methodsName: activateUser
     * @description: 激活用户
     * @param:  request      
     * @return: String
     * @throws:
	 */
	public String activateUser(HttpServletRequest request){
		String code = request.getParameter("code");      //从网页过来的未加密的信息
		String destination = "";
		try {
			code = Md5.EncoderByMd5(code);              //重新加密，看能否跟储存的信息匹配
			String username = userStateDAO.checkCode(code);
			if (username != null) {
				userStateDAO.activateUser(username);
				destination =  "user_login.jsp?msg=success";
			}else {
				destination = "user_register.jsp?msg=failed";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}
	
	
	/*
     * @methodsName: userRecover
     * @description: 用户接触禁用状态
     * @param:  request      
     * @return: String
     * @throws:
	 */
	public String userRecover(HttpServletRequest request) {
		String username = request.getParameter("username");
		userStateDAO.userRecover(username);
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
     * @methodsName: getForbiddenUsers
     * @description: 得到所有被禁用用户     
     * @return: List<User>
     * @throws:
	 */
	public List<User> getForbiddenUsers(){
		return userStateDAO.getForbiddenUsers();
	}
	


}
