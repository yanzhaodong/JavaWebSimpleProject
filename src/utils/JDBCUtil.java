package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection() throws Exception {
        Connection conn;                                           //声明connection对象
        String driver = "com.mysql.cj.jdbc.Driver";                //驱动程序名
        String url =  "jdbc:mysql://localhost:3306/db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";     
        														   //url指向访问的数据库名db
        String user = "mydb";                                      //MySQL配置的用户名
        String password = "secret";                                //MySQL配置里的密码

        Class.forName(driver);                                     //加载驱动程序
        conn = DriverManager.getConnection(url,user,password);     //连接驱动程序
        return conn;
    }
    
	public static void close(ResultSet resultSet,
			PreparedStatement preparedStatement, Connection connection) {
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
