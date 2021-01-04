package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.IUserBIZ;
import bizimpl.UserBIZImpl;
import enums.UserRegisterEnum;

/**
 * Servlet implementation class UserRegisterServlet
 */
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = null;
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String againpassword = req.getParameter("againpassword");
		String email = req.getParameter("email");
		try {
			IUserBIZ userBIZ = new UserBIZImpl();
			result = userBIZ.userRegister(username, password, againpassword,email, req);
			if (result.equals(UserRegisterEnum.USER_REGISTER_SUCCESS.getValue())) {
				req.getRequestDispatcher("user_login.jsp?msg=" + result + "").forward(
						req, resp);
			} else {
				req.getRequestDispatcher("user_register.jsp?msg=" + result + "")
						.forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


