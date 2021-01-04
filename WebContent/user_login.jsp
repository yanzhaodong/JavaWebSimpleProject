<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="utils.ValidateUtil" %>
<%@ page import="dao.IAdminDAO" %>
<%@ page import="daoimpl.AdminDAOImpl" %>
<%@ page import="enums.UserLoginEnum" %>
<%@ page import="java.awt.image.BufferedImage" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	

	
	<script type="text/javascript">
	
		<%String msg = request.getParameter("msg") == null ? "" : request
						.getParameter("msg");%>
						
		<%if (msg.equals(UserLoginEnum.USER_NAME_IS_NUll.getValue())) {%>
		 alert("<%=UserLoginEnum.USER_NAME_IS_NUll.getDesc()%>");
		<%}%>			
				
		<%if (msg.equals(UserLoginEnum.USER_PASSWORD_IS_NULL.getValue())) {%>
		 alert("<%=UserLoginEnum.USER_PASSWORD_IS_NULL.getDesc()%>");
		<%}%>
			
		<%if (msg.equals(UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getValue())) {%>
		 alert("<%=UserLoginEnum.USER_VALIDATE_CODE_IS_FAIL.getDesc()%>");
		<%}%>
						
		<%if (msg.equals(UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getValue())) {%>
		 alert("<%=UserLoginEnum.USER_NAME_OR_PASSWORD_IS_FAIL.getDesc()%>");
		<%}%>
		
		<%if (msg.equals(UserLoginEnum.USER_FORBIDDEN.getValue())) {%>
		 alert("<%=UserLoginEnum.USER_FORBIDDEN.getDesc()%>");
		<%}%>	
		
		<%if (msg.equals(UserLoginEnum.USER_LOGIN_SUCCESS.getValue())) {%>
		 alert("<%=UserLoginEnum.USER_LOGIN_SUCCESS.getDesc()%>");
	<%}%>
		
	</script>
	<%
		IAdminDAO adminDAO = new AdminDAOImpl();
		adminDAO.adminInit();
	%>
</head>
<body>
	
  	<div style="position: absolute;top:0;bottom: 0;left: 0;right: 0;height: 300px;width: 500px;margin:auto;">
  	<form action="UserLoginServlet" method="post">
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