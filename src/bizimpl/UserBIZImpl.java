package bizimpl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import biz.IUserBIZ;
import dao.IUserDAO;
import dao.IUserStateDAO;
import daoimpl.UserDAOImpl;
import daoimpl.UserStateDAOImpl;
import entity.User;
import enums.LoginStateEnum;
import enums.UserLoginEnum;
import enums.UserRegisterEnum;
import utils.UuidUtil;
import utils.InputCheckUtil;
import utils.CastUtil;
import utils.MailUtil;
import utils.Md5;

/**
* 业务层：包含跟用户相关，业务逻辑具体实现
* @author 严照东
*/
public class UserBIZImpl implements IUserBIZ {
	IUserDAO userDAO = new UserDAOImpl();
	IUserStateDAO userStateDAO = new UserStateDAOImpl();
	
	/**
     * @methodsName: userLogin
     * @description: 用户登录
     * @param:  request      
     * @return: String           如果以$开头，则显示为显示的警告信息；否则是页面将要跳转的地址
	 */
	public String userLogin(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validatecode = request.getParameter("validatecode");
		String syscode = CastUtil.cast(request.getSession().getAttribute("syscode"));
		int chance = userStateDAO.userGetChance(username);
		String errorMsg = InputCheckUtil.loginCheck(username, password, validatecode, syscode);;
	
		if (errorMsg== null) {
			if (chance == 0 & !"admin".equals(username)) {						
				errorMsg =  UserLoginEnum.USER_FORBIDDEN.getValue();
			} else{
				LoginStateEnum state = userDAO.userLogin(username,password);
				//String state = "";
				//根据返回的状态码判断是否成功登录
				try{
					switch (state) {
						case MISMATCH:           
							userStateDAO.userUpdateChance(username);
							errorMsg = UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getValue();
							break;
						case INACTIVATED:	
							errorMsg = UserLoginEnum.USER_NOT_ACTIVATED.getValue();
							break;
						case SUCCEED:				
							// 登录成功后 把当前登录成功后的用户 存入到SESSION中 
							request.getSession().setAttribute("username", username);
							request.getSession().setMaxInactiveInterval(300);
							userStateDAO.userRecover(username);
							if ("admin".equals(username)) {
								//对于admin，把被禁用用户存到session里，以便及时更新
								IUserBIZ userBIZ = new UserBIZImpl();
								List<User> forbidden_users = userBIZ.getForbiddenUsers();
								request.getSession().setAttribute("forbidden_users", forbidden_users);
								result.put("ADDRESS", "admin.jsp");
							} else {
								result.put("ADDRESS", "index.jsp");
							}
							break;
						default:
							throw new RuntimeException("登陆返回的状态码无法识别");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (errorMsg != null) {
			result.put("ERRORMSG",errorMsg);
		}
		return result.toString();
	}
	
	/**
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
		String errorMsg = "";
		JSONObject result = new JSONObject();
		
		try {
			encyptedCode = Md5.EncoderByMd5(code);
			errorMsg = InputCheckUtil.registerCheck(username,password, email, againpassword);
			if(errorMsg == null) {
				User user = userDAO.usernameRegisterCheck(username);
				if (user != null) {											//注册的用户名已存在且已激活
					errorMsg =  UserRegisterEnum.USER_ALREADY_EXIST.getValue();
				} else {
					Integer executeCount = userDAO.userRegister(username, password, email, encyptedCode);
					if(executeCount > 0){
						MailUtil.sendMail(email, code);
						result.put("ADDRESS", "waiting.jsp");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (errorMsg != null) { 
			result.put("ERRORMSG", errorMsg);
		}
		return result.toString();
	}

	/**
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
			} else {
				destination = "user_register.jsp?msg=failed";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}
	
	
	/**
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
	
	/**
     * @methodsName: getForbiddenUsers
     * @description: 得到所有被禁用用户     
     * @return: List<User>
     * @throws:
	 */
	public List<User> getForbiddenUsers(){
		return userStateDAO.getForbiddenUsers();
	}
	


}
