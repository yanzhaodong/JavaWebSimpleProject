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
	<input type="button" value="登出" style="float:right;" onclick="location='user_login.jsp?logout=1'">
	<table  border="1"  >
		<tr><td>ID</td><td>用户</td><td>邮箱</td><td>操作</td>
		<%
			List<User> users = Cast.cast(request.getSession().getAttribute("forbidden_users")); 
			if (users!=null){
				for (User user:users){
					String username = user.getUsername();	
				%>
					<tr>
						<td><%=user.getUserid() %></td>
						<td><%=username %></td>
						<td><%=user.getEmail() %></td>
						<td><input type="button" value="恢复" onclick="if(confirm('此举动将恢复用户，确认吗？')==false)return false;location.href='UserServlet?username=<%=username%>&action=recover'"/></td>
					</tr>
				<%
				}
			}
		%>
	</table>
  </body>
</html>