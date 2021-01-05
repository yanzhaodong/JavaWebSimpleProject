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
		
		//根据request里的action判断要执行什么动作
		if ("login".equals(action)){        //执行登陆操作
			String result = null;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String validatecode = request.getParameter("validatecode");
			String syscode = (String) request.getSession().getAttribute("syscode");

			try {
				IUserBIZ userBIZ = new UserBIZImpl();
				result = userBIZ.userLogin(username, password, validatecode, syscode,request);
				
				//判断去管理员界面，去用户界面, 还是登陆失败返回登陆界面
				if (UserLoginEnum.USER_LOGIN_SUCCESS.getDesc().equals(result)) {
					if ("admin".equals(username)) {
						request.getRequestDispatcher("UserServlet?action=list").forward(
								request, response);					
					}else{
						request.getRequestDispatcher("index.jsp").forward(
								request, response);					
					}
				} else {
					request.setAttribute("info", result);
					request.getRequestDispatcher("user_login.jsp?msg=" + result + "")
							.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}else if ("recover".equals(action)) {     //执行用户恢复注册操作
	        response.setContentType("text/html; charset=utf-8");
	        String id = request.getParameter("username");
	        IUserDAO userDAO = new UserDAOImpl();
	        // 调用UserDAO的删除方法.
	        userDAO.userRecover(id);
	        response.sendRedirect("admin.jsp");
		}
		else if ("register".equals(action)){      //执行注册操作
			String result = null;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String againpassword = request.getParameter("againpassword");
			String email = request.getParameter("email");
			try {
				IUserBIZ userBIZ = new UserBIZImpl();
				result = userBIZ.userRegister(username, password, againpassword,email, request);
				if (UserRegisterEnum.USER_REGISTER_SUCCESS.getDesc().equals(result)) {
					request.getRequestDispatcher("user_login.jsp?msg=" + result + "").forward(
							request, response);
				} else {
					request.getRequestDispatcher("user_register.jsp?msg=" + result + "")
							.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("list".equals(action)){
			try {
				IUserBIZ userBIZ = new UserBIZImpl();
				List<User> users = userBIZ.getForbiddenUsers(request);
				request.setAttribute("users", users);
				request.getRequestDispatcher("admin.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			System.out.println("Error: action not found");
		}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
