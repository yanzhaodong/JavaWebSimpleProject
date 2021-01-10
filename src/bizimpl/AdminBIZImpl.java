package bizimpl;

import biz.IAdminBIZ;
import dao.IAdminDAO;
import daoimpl.AdminDAOImpl;

/**
* 业务层：包含跟管理员用户相关，业务逻辑具的具体实现
* @author 严照东
*/
public class AdminBIZImpl implements IAdminBIZ {
	IAdminDAO adminDAO = new AdminDAOImpl();
	/*
	 * 初始化管理员
	 */
	public String adminInit(){
		adminDAO.adminInit();
		return "user_login.jsp";
	}
}
