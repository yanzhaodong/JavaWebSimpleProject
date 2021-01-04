<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="utils.*" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>被禁用用户名单</title>
</head>

  <body>
	<input type="button" value="登出" style="float: right;" onclick="location='user_login.jsp'">
    <%
    	Connection conn = JDBCUtil.getConnection();
    		Statement st = conn.createStatement(); 
    		String sql = "select * from users where chance = '0' ";
    		ResultSet rs = st.executeQuery(sql);
    %>
	<table  border="1"  >
		<tr><td>ID</td><td>用户</td><td>邮箱</td><td>操作</td>
		<%
			
			while (rs.next()) {
				String username = rs.getString("username");
				int id = rs.getInt("id");
		%>
			<tr>
				<td><%=rs.getString("id") %></td>
				<td><%=username %></td>
				<td><%=rs.getString("email") %></td>
                <td><a href="UserServlet?id=<%=String.valueOf(id)%>&action=recover" class="tablelink">恢复</a></td>
			</tr>
		<%
			}
		%>
	</table>
  </body>
</html>