package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.IAdminDAO;
import utils.JDBCUtil;

public class AdminDAOImpl implements IAdminDAO{
	//管理员注册
	public int adminInit() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int executeCount = 0;
		String chance = "3";
		String username="admin";
		String password="123";
		String email="email";
		
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement("select username from users where username=?");
			preparedStatement.setObject(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if (! resultSet.next()) {
				try {
					connection = JDBCUtil.getConnection();
					preparedStatement = connection.prepareStatement("insert into users (username,password,"
							+ "email,chance) values(?,?,?,?)");
					preparedStatement.setObject(1, username);
					preparedStatement.setObject(2, password);
					preparedStatement.setObject(3, email);
					preparedStatement.setObject(4, chance);
					executeCount = preparedStatement.executeUpdate();
					
				} catch (Exception e) {
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
