package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import biz.IUserBIZ;
import bizimpl.UserBIZImpl;

/**
 * Application Lifecycle Listener implementation class MyServletContextListener
 */

@WebListener
public class MyServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce)  { 
    	IUserBIZ userBIZ= new UserBIZImpl();
    	userBIZ.adminInit();
    }
	
}
