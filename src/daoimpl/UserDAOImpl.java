package daoimpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import dao.IUserDAO;
import entity.User;
import enums.LoginStateEnum;
import utils.JDBCUtil;
import utils.Md5;

/**
* 包含跟用户登录注册相关, 数据层面方法的具体实现
* @author 严照东
*/

public class UserDAOImpl implements IUserDAO{
	private int chance;
	public UserDAOImpl() {
		Properties properties = new Properties(); 
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/Config.properties");
		try {
			properties.load(in);
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.chance = Integer.valueOf(properties.getProperty("chance")) ;
	}
	
	/**
     * @methodsName: usernameRegisterCheck
     * @description: 用户登录的时候，查询是否存在有指定用户名和密码,并已激活的用户
     * @param:  username     用户输入的用户名
     * @param:  password     用户输入的密码
     * @return: LoginStateEnum		不同的登录结果
     * @throws:
	 */
	public LoginStateEnum userLogin(String username, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		LoginStateEnum result = LoginStateEnum.MISMATCH;    
		
		try {
			String encryptedPassword = Md5.EncoderByMd5(password);
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select id,username,password,activated from users where username=? and password=?");
			preparedStatement.setObject(1, username);
			preparedStatement.setObject(2, encryptedPassword);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				if (resultSet.getInt("activated")==0) {
					result = LoginStateEnum.INACTIVATED;
				} else {
					result = LoginStateEnum.SUCCEED;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, connection);
		}
		return result;
	}
	
	
	/**
     * @methodsName: usernameRegisterCheck
     * @description: 注册的时候检查用户的名字，如果用户名已存在且已激活，则不能注册，反之可以注册
     * @param:  username     用户输入的注册用户名
     * @return: User         注册后的用户
	 */
	public User usernameRegisterCheck(String username) {
		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select username,activated from users where username=?");
			preparedStatement.setObject(1, username);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				if (resultSet.getInt("activated") == 0) {
					preparedStatement = connection.prepareStatement("delete from users where username=?");
					preparedStatement.setObject(1, resultSet.getString("USERNAME"));
					preparedStatement.executeUpdate();
				} else {
					user = new User();
					user.setUsername(resultSet.getString("USERNAME"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, connection);
		}
		return user;
	}

	
	/**
     * @methodsName: usernameRegister
     * @description: 用户注册成功后创建新用户
     * @param:  username     用户输入的注册用户名
     * @param:  password     用户输入的注册密码
     * @param:  email     用户输入的注册邮箱
     * @param:  code      系统生成的激活验证码
     * @return: int		  插入的用户数量
     * @throws:
	 */
	public int userRegister(String username, String password, String email, String code) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int executeCount = 0;
		
		try {
			String encryptedPassword = Md5.EncoderByMd5(password);
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("insert into users (username,password,"
					+ "email,chance,activated,uuid) values(?,?,?,?,?,?)");
			preparedStatement.setObject(1, username);
			preparedStatement.setObject(2, encryptedPassword);
			preparedStatement.setObject(3, email);
			preparedStatement.setObject(4, this.chance);
			preparedStatement.setObject(5, 0);
			preparedStatement.setObject(6, code);
			executeCount = preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, preparedStatement, connection);
		}
		return executeCount;
	}
}
