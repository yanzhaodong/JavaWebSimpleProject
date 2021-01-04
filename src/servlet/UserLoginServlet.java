package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.IUserBIZ;
import enums.UserLoginEnum;
import bizimpl.UserBIZImpl;
/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validatecode = request.getParameter("validatecode");
		String syscode = (String) request.getSession().getAttribute("syscode");

		try {
			IUserBIZ userBIZ = new UserBIZImpl();
			result = userBIZ.userLogin(username, password, validatecode, syscode,request);
			
			//判断去管理员界面，去用户界面, 还是登陆失败返回登陆界面
			if (result.equals(UserLoginEnum.USER_LOGIN_SUCCESS.getValue())) {
				if (username.equals("admin")) {
					request.getRequestDispatcher("admin.jsp").forward(
							request, response);					
				}else{
					request.getRequestDispatcher("index.jsp").forward(
							request, response);					
				}
			} else {
				request.getRequestDispatcher("user_login.jsp?msg=" + result + "")
						.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
