package daoimpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import dao.IAdminDAO;
import utils.JDBCUtil;

public class AdminDAOImpl implements IAdminDAO{
	private int chance;
	private String admin_username;
	private String admin_password;
	private String admin_email;
	public AdminDAOImpl() {
		Properties properties = new Properties(); 
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/Config.properties");
		try {
			properties.load(in);
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.chance = Integer.valueOf(properties.getProperty("chance")) ;
		this.admin_username = properties.getProperty("adminUsername");
		this.admin_password = properties.getProperty("adminPassword");
		this.admin_email = properties.getProperty("adminEmail");
	}
	
	/*
	 * 管理员注册
	 */
	public int adminInit() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int executeCount = 0;
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select username from users where username=?");
			preparedStatement.setObject(1, this.admin_username);
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.next()) {
				try {
					connection = JDBCUtil.getConnection();
					preparedStatement = connection.prepareStatement("insert into users (username,password,"
							+ "email,chance,activated) values(?,?,?,?,?)");
					preparedStatement.setObject(1, this.admin_username);
					preparedStatement.setObject(2, this.admin_password);
					preparedStatement.setObject(3, this.admin_email);
					preparedStatement.setObject(4, this.chance);
					preparedStatement.setObject(5, 1);
					executeCount = preparedStatement.executeUpdate();
					
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					JDBCUtil.close(null, preparedStatement, connection);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeCount;
	}
}
