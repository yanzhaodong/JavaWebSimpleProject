package dao;

import java.util.List;

import entity.User;

public interface IUserDAO {
	//用户登录
	User userLogin(String username, String password);
	
	//用户注册
	int userRegister(String username, String password, String email);
	
	//判断注册用户名存在否
	User userToRegister(String username);
	
	//解除用户禁用状态
	int userRecover(String id);

	//查询用户剩余机会
	String userGetChance(String username);
		
	//减少用户输错密码机会
	int userUpdateChance(String username, String chance);
	

}
