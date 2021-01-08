package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
	public static Connection getConnection() throws Exception {
		Connection conn; // 声明connection对象
		
		Properties properties = new Properties(); 
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/Config.properties");
		properties.load(in);
		in.close();
		

		String driver = "com.mysql.cj.jdbc.Driver"; // 驱动程序名
		String url = String.format("jdbc:mysql://%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", 
				properties.getProperty("dbPort"),properties.getProperty("dbName")); // url指向访问的数据库名db
		String user = properties.getProperty("dbUser"); // MySQL配置的用户名
		String password = properties.getProperty("dbPassword"); // MySQL配置里的密码
		Class.forName(driver); // 加载驱动程序
		conn = DriverManager.getConnection(url, user, password); // 连接驱动程序
		return conn;
	}

	public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
