package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		String path = "config\\Config.properties";
		InputStreamReader in =new InputStreamReader(new FileInputStream(path),"utf-8");
		properties.load(in);
		in.close();
		System.out.println(properties.getProperty("dbPort"));
		//InputStream in = JDBCUtil.class.getResourceAsStream("Config.properties");

		
		/*
		 * try { // 加载属性文件 properties.load(in); } catch (IOException e) {
		 * System.out.println("配置文件加载失败"); }
		 */
		//System.out.println(pro.getProperty("dbPort"));

		String driver = "com.mysql.cj.jdbc.Driver"; // 驱动程序名
		String url = "jdbc:mysql://localhost:3306/db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		// url指向访问的数据库名db
		String user = "mydb"; // MySQL配置的用户名
		String password = "secret"; // MySQL配置里的密码

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
