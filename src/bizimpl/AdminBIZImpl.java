package bizimpl;

import javax.servlet.http.HttpServletRequest;

import biz.IAdminBIZ;
import dao.IAdminDAO;
import daoimpl.AdminDAOImpl;


public class AdminBIZImpl implements IAdminBIZ{
	IAdminDAO adminDAO = new AdminDAOImpl();
	@Override
	public void adminInit(HttpServletRequest req) {
		adminDAO.adminInit();
	}
}
