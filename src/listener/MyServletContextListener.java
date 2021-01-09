package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import biz.IAdminBIZ;
import bizimpl.AdminBIZImpl;

/**
 * Application Lifecycle Listener implementation class MyServletContextListener
 */

@WebListener
public class MyServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce)  { 
    	IAdminBIZ adminBIZ= new AdminBIZImpl();
    	adminBIZ.adminInit();
    }
	
}
