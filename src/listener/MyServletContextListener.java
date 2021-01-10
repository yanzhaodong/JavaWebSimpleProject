package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import biz.IAdminBIZ;
import bizimpl.AdminBIZImpl;

/**
* 监听界面初始化事件，并初始化管理员账户
* @author 严照东
*/

@WebListener
public class MyServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce)  { 
    	IAdminBIZ adminBIZ= new AdminBIZImpl();
		adminBIZ.adminInit();
    }
	
}
