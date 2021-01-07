package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.IUserBIZ;
import bizimpl.UserBIZImpl;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getParameter("action");
		String result = null;
		IUserBIZ userBIZ = new UserBIZImpl();
		//根据request里的action判断要执行什么动作
		try{
			switch (action) {
				case "login":
					result = userBIZ.userLogin(request);				
					break;
				case "register":
					result = userBIZ.userRegister(request);
					break;
				case "recover":
			        result = userBIZ.userRecover(request);
			        break;
				default:
					throw new RuntimeException("action名字不合法");
			}
			request.getRequestDispatcher(result).forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
