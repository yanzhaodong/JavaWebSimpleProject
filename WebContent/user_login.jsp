<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="enums.UserLoginEnum" %>
<%@ page import="java.awt.image.BufferedImage" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<% 
	session.invalidate();
	%>
	<script type="text/javascript">
		<%String msg = request.getParameter("msg");%>
		<%if (msg!=null && !"登陆成功".equals(msg)) {%>
		 alert("<%=msg%>");
		<%}%>	
	</script>
</head>
<body>
  	<div style="position: absolute;top:0;bottom: 0;left: 0;right: 0;height: 300px;width: 500px;margin:auto;">
  	<form action="UserServlet?action=login" method="post">
  		<h1 style="color:red">登录系统</h1>
  		<table style="text-align:justify;text-align-last: justify;">
  			<tr>
  				<td>用 户 名：</td>
  				<td><input name="username"></td>
  			</tr>
  			<tr>
  				<td>密  码：</td>
  				<td><input type="password" name="password"></td>
  			</tr>
  			<tr>
  				<td>验 证 码：</td>
  				<td><input name="validatecode"></td>
  				<td><img alt="验证码看不清，换一张" src="${pageContext.request.contextPath}/DrawImageServlet" id="validateCodeImg" ></td>
  			</tr>
  			<tr>	
	  			<td><button type="submit">登录</button></td>
	  			<td><input type="button" value="注册" onclick="location='user_register.jsp'"/></td>
  			</tr>
  		</table>
  	</form>
  	</div>
</body>
</html>