package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
* 数据库连接相关的工具类
* @author 严照东
*/
public class JDBCUtil {
	/**
	 * 连接数据库，并返回connection
     * @methodsName: getConnection
     * @return: Connection  数据库的连接对象
     * @throws: Exception
	 */
	public static Connection getConnection() throws Exception {
		Connection conn; // 声明connection对象

		Properties properties = new Properties();                                     			//加载properties文件
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/Config.properties");
		properties.load(in);
		in.close();
		
		String driver = properties.getProperty("driver"); 										// 驱动程序名
		String url = properties.getProperty("url");                                 			// url指向访问的数据库名db
		String user = properties.getProperty("dbUser"); 										// MySQL配置的用户名
		String password = properties.getProperty("dbPassword"); 								// MySQL配置里的密码
		Class.forName(driver); 																	// 加载驱动程序
		conn = DriverManager.getConnection(url, user, password); 								// 连接驱动程序
		return conn;
	}
	
	/**
	 * 关闭数据库连接
     * @methodsName: close
     * @param: resultSet               数据库连接的ResultSet对象
     * @param: preparedStatement       数据库连接的PreparedStatementt对象
     * @param: connection              数据库连接的Connection对象
	 */
	public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
