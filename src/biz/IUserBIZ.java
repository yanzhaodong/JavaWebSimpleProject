package biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import entity.User;

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
