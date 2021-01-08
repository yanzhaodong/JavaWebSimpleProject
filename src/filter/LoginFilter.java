package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.CastUtil;

import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/index.jsp","/admin.jsp"}, dispatcherTypes = {})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String username = CastUtil.cast(request.getSession().getAttribute("username"));
    	if(request.getSession().getAttribute("init")==null){
    		System.out.println("Project Init...");
    		request.getSession().setAttribute("init",1);
    		response.sendRedirect("UserServlet?action=init");
    	}else {
            String url = request.getRequestURI();
            int idx = url.lastIndexOf("/");
            String endWith = url.substring(idx + 1);
            
            //只对admin和普通用户分别登录成功后的情况继续执行
            if (("admin.jsp".equals(endWith) & "admin".equals(username)) 
            		|| ("index.jsp".equals(endWith) & username != null & !"admin".equals(username))) {
            	chain.doFilter(req, resp);
            }else {
            	response.sendRedirect("user_login.jsp"); 	
            } 
    	}
    }

}
