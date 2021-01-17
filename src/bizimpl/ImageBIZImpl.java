package bizimpl;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.IImageBIZ;
import utils.ImageUtil;
import utils.StringUtil;

/**
* 业务层：包含跟图片相关的具体实现
* @author 严照东
*/
public class ImageBIZImpl implements IImageBIZ{
    
	/**
	 * 画验证码图片
     * @methodsName: drawImage
     * @param:  request  
     * @param:  response    
	 */
	public void drawImage(HttpServletRequest request, HttpServletResponse response) {
        String createTypeFlag = request.getParameter("createTypeFlag");//接收客户端传递的createTypeFlag标识
        String text = StringUtil.getRandomChar(4, createTypeFlag);
        ImageUtil imageUtil = new ImageUtil();
        BufferedImage bi = imageUtil.drawImage(text);
        
        //将随机数存在session中
        request.getSession().setAttribute("syscode", text);
        //设置响应头通知浏览器以图片的形式打开
        response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
        //9.设置响应头控制浏览器不要缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        //将图片写给浏览器
        try {
			ImageIO.write(bi, "jpg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
