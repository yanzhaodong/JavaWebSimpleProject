package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.IUserBIZ;
import bizimpl.UserBIZImpl;
import enums.UserLoginEnum;

import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "*.jsp", dispatcherTypes = {})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("LoginFilter doFilter");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String url = request.getRequestURI();

        System.out.println("请求的url：" + url);
        /*登录页面不需要过滤*/

        int idx = url.lastIndexOf("/");
        String endWith = url.substring(idx + 1);
        
        if ("user_login.jsp".equals(endWith)) {
            //新建管理员用户(如果已存在会跳过)
    		IUserBIZ userBIZ = new UserBIZImpl();
    		userBIZ.adminInit(request);
    		
            System.out.println("是登录页面，不进行拦截处理");
            chain.doFilter(req, resp);       	
        } else if ("user_register.jsp".equals(endWith)) {
        	System.out.println("是注册页面，不进行拦截处理");
        	chain.doFilter(req, resp); 
        } else {
            /*不是登录页面  进行拦截处理*/
            System.out.println("不是登录页面，进行拦截处理");

            if (!isLogin(request)) {
                System.out.println("没有登录过或者账号密码错误，跳转到登录界面");
                response.sendRedirect("user_login.jsp");
            } else {
                System.out.println("已经登录，进行下一步");
                chain.doFilter(req, resp);
            }       	
        }

    }


    private boolean isLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String username = "";
        String password = "";
        String result = null;
        
        // 得到当前cookies的账户密码信息
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                } else if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }

        //看看用户是否存在于数据库中
		try {
			IUserBIZ userBIZ = new UserBIZImpl();
			result = userBIZ.userCheck(username, password,request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (UserLoginEnum.USER_LOGIN_SUCCESS.getValue().equals(result)){
			return true;
		}
        return false;
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("LoginFilter  init");
    }

}
