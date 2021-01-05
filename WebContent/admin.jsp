<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="utils.*" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>被禁用用户名单</title>
</head>

  <body>
	<input type="button" value="登出" style="float: right;" onclick="location='user_login.jsp'">
	<table  border="1"  >
		<tr><td>ID</td><td>用户</td><td>邮箱</td><td>操作</td>
		<%
			List<User> users = (List<User>) request.getAttribute("users"); 
			for (User user:users){
				String username = user.getUsername();
		%>
			<tr>
				<td><%=user.getUserid() %></td>
				<td><%=username %></td>
				<td><%=user.getEmail() %></td>
                <td><a href="UserServlet?username=<%=username%>&action=recover" class="tablelink">恢复</a></td>
			</tr>
		<%
			}
		%>
	</table>
  </body>
</html>