package bizimpl;

import biz.IAdminBIZ;
import dao.IAdminDAO;
import daoimpl.AdminDAOImpl;

public class AdminBIZImpl implements IAdminBIZ {
	IAdminDAO adminDAO = new AdminDAOImpl();
	
	/*
	 * 初始化管理员
	 */
	public String adminInit() {
		adminDAO.adminInit();
		return "user_login.jsp";
	}
}
