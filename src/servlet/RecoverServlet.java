package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IUserDAO;
import daoimpl.UserDAOImpl;

@WebServlet("/RecoverServlet")
public class RecoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        String id = request.getParameter("id");
        IUserDAO userDAO = new UserDAOImpl();
        // 调用UserDAO的删除方法.
        userDAO.userRecover(id);
        // 恢复删除成功之后 , 重定向到findAll页面 , 更新.
        //response.sendRedirect(request.getContextPath()+"/findAll");
        response.sendRedirect("admin.jsp");

    }
}