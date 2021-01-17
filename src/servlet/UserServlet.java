package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import biz.IUserBIZ;
import bizimpl.UserBIZImpl;

/**
* 根据不同指定的action调用不同的业务处理
* @author 严照东
*/
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String result = null;
		String temp = null;
		IUserBIZ userBIZ = new UserBIZImpl();
		//根据request里的action判断要执行什么动作
		try{
			switch (action) {
				case "login":								
					result = userBIZ.userLogin(request);
					response.getWriter().write(result);
					break;
				case "register":
					temp = userBIZ.userRegister(request);
					response.getWriter().write(temp); 
					break;
				case "recover":
			        temp = userBIZ.userRecover(request);
			        request.getRequestDispatcher(temp).forward(request, response);
			        break;
				case "activate":
					temp = userBIZ.activateUser(request);
					request.getRequestDispatcher(temp).forward(request, response);
					break;
				default:
					throw new RuntimeException("action名字不合法");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
