package biz;

import javax.servlet.http.HttpServletRequest;

public interface IUserBIZ {
	//用户登录
	String userLogin(String username, String password,  String validatecode,
			String syscode, HttpServletRequest req);
	
	//用户注册
	String userRegister(String username, String password, String againpassword, String email,
			HttpServletRequest req);
	
	//用户检测
	String userCheck(String username, String password, HttpServletRequest request);
	
	//用户恢复
	void userRecover(String id, HttpServletRequest req);
}
