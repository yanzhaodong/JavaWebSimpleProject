package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.CastUtil;

import java.io.IOException;
/**
* 对于未登录状态下进入admin.jsp与index.jsp的操作进行拦截
* @author 严照东
*/
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/index.jsp","/admin.jsp"}, dispatcherTypes = {})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String username = CastUtil.cast(request.getSession().getAttribute("username"));
        String url = request.getRequestURI();
        int idx = url.lastIndexOf("/");
        String endWith = url.substring(idx + 1);
        
        
        if (("admin.jsp".equals(endWith) & "admin".equals(username))  //只对admin和普通用户分别登录成功后的情况不拦截
        		|| ("index.jsp".equals(endWith) & username != null & !"admin".equals(username))) {
        	chain.doFilter(req, resp);
        }else {
        	response.sendRedirect("user_login.jsp");                  //拦截，回到user_login.jsp	
        } 
    }

}
