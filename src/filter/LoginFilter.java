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
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String username = (String) request.getSession().getAttribute("username");
        
        String url = request.getRequestURI();
        int idx = url.lastIndexOf("/");
        String endWith = url.substring(idx + 1);
        
        if (("admin.jsp".equals(endWith)  & "admin".equals(username)) 
        		|| ("index.jsp".equals(endWith) & username != null & !"admin".equals(username))) {
        	chain.doFilter(req, resp);
        }else {
        	response.sendRedirect("user_login.jsp"); 	
        }   			
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("LoginFilter  init");
    }

}
