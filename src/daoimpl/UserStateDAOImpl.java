package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.IUserStateDAO;
import entity.User;
import utils.JDBCUtil;

/**
* 包含跟用户状态（激活,剩余连续输错密码次数）相关, 数据层面方法的具体实现
* @author 严照东
*/
public class UserStateDAOImpl implements IUserStateDAO{
	/**
     * @methodsName: userRecover
     * @description: 解除用户禁用状态
     * @param:  username     用户输入的注册用户名
     * @return: int          执行变更的行数
     * @throws:
	 */
	public int userRecover(String username){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int executeCount = 0;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE users SET chance = '3' WHERE username=?");
			preparedStatement.setObject(1, username);
			executeCount = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, preparedStatement, connection);
		}
		return executeCount;
	}

	/**
     * @methodsName: userGetChance
     * @description: 查询用户剩余机会
     * @param:  username     用户输入的注册用户名
     * @return: int          用户的剩余机会
     * @throws:
	 */
	public int userGetChance(String username){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int chance = -1;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select chance from users where username=?");
			preparedStatement.setObject(1, username);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				chance = resultSet.getInt("chance");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return chance;
	}
	
	/**
     * @methodsName: userUpdateChance
     * @description: 减少用户剩余连续输错密码机会
     * @param:  username     需要减少机会的用户名
     * @return: int			 执行变更的行数
     * @throws:
	 */
	public int userUpdateChance(String username){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int executeCount = 0;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE users SET chance = chance-'1' WHERE username=?");
			preparedStatement.setObject(1, username);
			executeCount = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, connection);
		}
		return executeCount;
	}
	
	/**
     * @methodsName: userGetChance
     * @description: 得到所有被禁用的用户
     * @return: List<User>   所有被禁用的用户
     * @throws:
	 */
	public List<User> getForbiddenUsers() {
		List<User> users = new ArrayList<User>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select id,username,email from users where chance=?");
			preparedStatement.setObject(1, 0);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				users.add(new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("email")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, connection);
		}
		return users;
	}
	
	/**
     * @methodsName: checkCode
     * @description: 通过激活验证码来查询用户
     * @param:  code      用户的激活验证码
     * @return: String	  得到的用户名
     * @throws:
	 */
	public String checkCode(String code) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String username = null;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select username from users where uuid=?");
			preparedStatement.setObject(1, code);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				username = resultSet.getString("username");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}
	
	/**
     * @methodsName: checkCode
     * @description: 激活用户
     * @param:  username      需要被激活用户的用户名
     * @return: int           执行变更的行数
     * @throws:
	 */
	public int activateUser(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int executeCount = 0;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE users SET activated = ? WHERE username=?");
			preparedStatement.setObject(1, 1);
			preparedStatement.setObject(2, username);
			executeCount = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, connection);
		}
		return executeCount;
	}
}
