package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biz.IImageBIZ;
import bizimpl.ImageBIZImpl;
/**
* 生成随机图片，用来作为登录验证码
* @author 严照东
*/
@WebServlet("/DrawImageServlet")
public class DrawImageServlet extends HttpServlet {
    private static final long serialVersionUID = 3038623696184546092L;

    public static final int WIDTH = 120;//生成的图片的宽度
    public static final int HEIGHT = 30;//生成的图片的高度

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	IImageBIZ imageBIZ = new ImageBIZImpl();
    	imageBIZ.drawImage(request, response);
    }
}
