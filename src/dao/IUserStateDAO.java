package dao;

import java.util.List;

import entity.User;

/**
* 包含跟用户状态（激活,剩余连续输错密码次数）相关, 数据层面方法的接口
* @author 严照东
*/


public interface IUserStateDAO {
	//解除用户禁用状态
	int userRecover(String username);

	//查询用户剩余机会
	int userGetChance(String username);
		
	//减少用户输错密码机会
	int userUpdateChance(String username);
	
	//得到所有被禁用用户
	List<User> getForbiddenUsers();
	
	//检查激活码
	String checkCode(String code);
	
	//激活用户
	int activateUser(String username);
}
