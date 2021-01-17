package biz;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import entity.User;

/**
* 业务层：包含跟用户相关，业务逻辑的接口
* @author 严照东
*/
public interface IUserBIZ {
	//用户登录
	String userLogin(HttpServletRequest req);
	
	//用户注册
	String userRegister(HttpServletRequest req);
	
	//激活用户
	String activateUser(HttpServletRequest req);
	
	//用户恢复
	String userRecover(HttpServletRequest req);
	
	//得到所有用户
	List<User> getForbiddenUsers();


}
