package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.IUserDAO;
import entity.User;
import utils.JDBCUtil;

public class UserDAOImpl implements IUserDAO{
	//用户登录，查询用户是否存在
	public User userLogin(String username, String password) {
		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select id,username,password from users where username=? and password=?");
			preparedStatement.setObject(1, username);
			preparedStatement.setObject(2, password);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				user = new User();
				user.setUserid(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
			}
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, connection);
		}
		return user;
	}
	
	
	//用户注册过程，判断用户名存在否
	public User userToRegister(String username) {
		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select username from users where username=?");
			preparedStatement.setObject(1, username);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				user = new User();
				user.setUsername(resultSet.getString("USERNAME"));
			}
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, connection);
		}
		return user;
	}

	
	//用户注册过程，创建新用户
	public int userRegister(String username, String password, String email) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int executeCount = 0;
		String chance = "3";
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("insert into users (username,password,"
					+ "email,chance) values(?,?,?,?)");
			preparedStatement.setObject(1, username);
			preparedStatement.setObject(2, password);
			preparedStatement.setObject(3, email);
			preparedStatement.setObject(4, chance);
			executeCount = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(null, preparedStatement, connection);
		}
		return executeCount;
	}
	
	//解除用户禁用状态
	public int userRecover(String id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int executeCount = 0;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE users SET chance = '3' WHERE id=?");
			preparedStatement.setObject(1, id);
			executeCount = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(null, preparedStatement, connection);
		}
		return executeCount;
	}

	//查询用户剩余机会
	public String userGetChance(String username){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String chance = null;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select chance from users where username=?");
			preparedStatement.setObject(1, username);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				chance = resultSet.getString("chance");
			}
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return chance;
	}
	
	//减少用户输错密码机会
	public int userUpdateChance(String username, String chance){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int executeCount = 0;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select chance from users where username=?");
			preparedStatement.setObject(1, username);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				chance = resultSet.getString("chance");
			}
			preparedStatement.close();

			String newChance = String.valueOf(Integer.valueOf(chance)-1);
			preparedStatement = connection.prepareStatement("UPDATE users SET chance = ? WHERE username=?");
			preparedStatement.setObject(1, newChance);
			preparedStatement.setObject(2, username);
			executeCount = preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(null, preparedStatement, connection);
		}
		return executeCount;
	}
}
