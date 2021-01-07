package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/index.jsp","/admin.jsp"}, dispatcherTypes = {})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("LoginFilter doFilter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (!isLogin(request)) {
            response.sendRedirect("user_login.jsp");
        } else {
            chain.doFilter(req, resp);
        }       	
    }

    private boolean isLogin(HttpServletRequest request) {
    	if ((request.getSession().getAttribute("user")==null)) {
    		return false;
    	}
    	return true;
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("LoginFilter  init");
    }

}
