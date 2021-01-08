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
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	
	<% 
	//检测是否有用户要登出
	String isOut = request.getParameter("logout");
	if ("1".equals(isOut)){
		session.invalidate();}
	%>
	<script>
	<%String msg = request.getParameter("msg");%>
		<%if ("success".equals(msg)) {%>
		 alert("<%="注册成功"%>");
	<%}%>	
	</script>
	<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script>
	    // 显示 loadingS
		function login_submit(){
		    $.ajax({
		        url: 'UserServlet?action=login',
		        type: 'post',
		        data: $('#login_form').serialize(), 
		        success: function (res) {
		            // Servlet界面不跳转时 
		            if (res.substring(0,5)=="alert"){
		            	alert(res.substring(5,res.length));
		            }else{
		            	location.href=res;
		            }
		        },
		    })	    	
	    }
	</script>
</head>
<body>
  	<div style="position: absolute;top:0;bottom: 0;left: 0;right: 0;height: 300px;width: 500px;margin:auto;">
  	<form action="UserServlet?action=login" method="post" id="login_form">
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
	  			<td><button type="button" onclick="login_submit()">登录</button></td>
	  			<td><input type="button" value="注册" onclick="location='user_register.jsp'"/></td>
  			</tr>
  		</table>
  	</form>
  	</div>
</body>
</html>