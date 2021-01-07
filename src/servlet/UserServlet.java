package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.IUserBIZ;
import bizimpl.UserBIZImpl;
import dao.IUserDAO;
import daoimpl.UserDAOImpl;
import entity.User;
import enums.UserLoginEnum;
import enums.UserRegisterEnum;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getParameter("action");
		String result = null;
		//根据request里的action判断要执行什么动作
		try{
			switch (action) {
				case "login":
					try {
						IUserBIZ userBIZ = new UserBIZImpl();
						result = userBIZ.userLogin(request);
						request.getRequestDispatcher(result).forward(request, response);					
					} catch (Exception e) {
						e.printStackTrace();
					}	
					break;
				case "register":
					IUserBIZ userBIZ = new UserBIZImpl();
					result = userBIZ.userRegister(request);
					request.getRequestDispatcher(result).forward(request, response);
					break;
				case "recover":
			        response.setContentType("text/html; charset=utf-8");
			        String id = request.getParameter("username");
			        IUserDAO userDAO = new UserDAOImpl();
			        // 调用UserDAO的删除方法.
			        userDAO.userRecover(id);
			        response.sendRedirect("admin.jsp");
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
