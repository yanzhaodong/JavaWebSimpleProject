<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="enums.UserRegisterEnum" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<script type="text/javascript">

	</script>
	<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script>
	    // 显示 loading
		function register_submit(){
		    $.ajax({
		        url: 'UserServlet?action=register',
		        type: 'post',
		        data: $('#register_form').serialize(), 
		        success: function (res) {
		            // Servlet界面不跳转时实行
		            if (res[0] =="$"){
		            	alert(res.substring(1,res.length));
		            }else{
		            	location.href=res;
		            } 
		        },

		    })	    	
	    }
	</script>
  </head>
  
   <body >
  	<div style="position: absolute;top:0;bottom: 0;left: 0;right: 0;height: 300px;width: 500px;margin:auto;">
  	<form action="UserServlet?action=register" method="post" id="register_form">
  		<center><h1 style="color:red">用户注册</h1>
  		<table style="text-align:justify;text-align-last: justify;">
 			<tr>
  				<td>邮箱：</td>
  				<td><input name="email"></td>
  			</tr>
  			<tr>
  				<td>用户名：</td>
  				<td><input name="username"></td>
  			</tr>
  			<tr>
  				<td>密  码：</td>
  				<td><input type="password" name="password"></td>
  			</tr>
  			<tr>
  				<td>确认密码：</td>
  				<td><input type="password" name="againpassword"></td>
  			</tr>		
  			<tr>
	  			<td><button type="button" onclick="register_submit()">注册</button></td>
	  			<td><input type="button" value="返回" onclick="location='user_login.jsp'"/></td>
  			</tr>
  		</table></center>
  	</form>
  	</div>
  </body>
</html>