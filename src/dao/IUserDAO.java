package dao;
import entity.User;

/**
* 包含跟用户登录注册相关, 数据层面方法的接口
* @author 严照东
*/


public interface IUserDAO {
	//用户登录
	int userLogin(String username, String password);

	//判断注册用户名存在否
	User usernameRegisterCheck(String username);
	
	//用户注册
	int userRegister(String username, String password, String email, String code);
	

	

}
