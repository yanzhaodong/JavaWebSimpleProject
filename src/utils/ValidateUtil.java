package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Graphics2D;  
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random; 

public class ValidateUtil {
    //验证码图片的宽度。

    private static int width = 80;
    //验证码图片的高度。

    private static int height = 23;
    /**
     * 验证码图片的高度。
     */
    private static int length = 4;    
    private static String textCode;
	private static Random random = new Random();  
	
	
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        ValidateUtil.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        ValidateUtil.height = height;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        ValidateUtil.length = length;
    }
    public String getText() {
        return textCode;
    }
    public void setText(String text) {
        ValidateUtil.textCode = text;
    }
    /**
     * 随机生成颜色
     */
    public static Color getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }
    
    /**
     * 随机生成验证码字符串
     */  
	public static String createValidateCode() {
		String temp = "1234567890qwertyuioplkjhgfdsazxcvbnm";
		StringBuilder validateCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			validateCode.append(temp.charAt(new Random().nextInt(temp
					.length())));
		}
		return validateCode.toString();
	}

    /**
     * 创建验证码图片
     */
    public static BufferedImage getImageFromCode() {
    	
    	
    	//创建对象
    	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    	Graphics g = image.getGraphics();
    	Random r=new Random();
    	
    	//设置内部参数
    	textCode = createValidateCode();
    	Color backColor = Color.WHITE;
    	Color foreColor = Color.BLACK;
    	Color lineColor = null;
    	int interLine = 4;
    	boolean randomLocation = true;
    	
    	//设置背景颜色
    	g.setColor(backColor);
    	g.fillRect(0,0,width,height);
    	
    	//绘制干扰线
    	if(interLine>0){
    		int x=r.nextInt(4),y=0;
    		int x1=width-r.nextInt(4),y1=0;
    		for(int i=0;i<interLine;i++){
    			g.setColor(lineColor==null?getRandomColor():lineColor);
    			y=r.nextInt(height-r.nextInt(4));
    			y1=r.nextInt(height-r.nextInt(4));
    			g.drawLine(x,y,x1,y1);
    			}
    	}
    	
    	//绘制验证码
    	int fsize=(int)(height*0.8);//字体大小为图片高度的80%
        int fx=0;
        int fy=fsize;
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,fsize));
        
        //写字符
        for(int i=0;i<textCode.length();i++){
            fy=randomLocation?(int)((Math.random()*0.3+0.6)*height):fy;//每个字符高低是否随机
            g.setColor(foreColor==null?getRandomColor():foreColor);
            g.drawString(textCode.charAt(i)+"",fx,fy);
            fx+=(width / textCode.length()) * (Math.random() * 0.3 + 0.8); //依据宽度浮动
        }
    	
        //添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);//噪点数量
        for (int i = 0; i < area; i++) {
            int xxx = r.nextInt(width);
            int yyy = r.nextInt(height);
            int rgb = getRandomColor().getRGB();
            image.setRGB(xxx, yyy, rgb);
        }
        g.dispose();
    	return image;
    }
	public static String createValidateCode(int length) {
		String temp = "1234567890qwertyuioplkjhgfdsazxcvbnm";
		StringBuilder validateCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			validateCode.append(temp.charAt(new Random().nextInt(temp
					.length())));
		}
		return validateCode.toString();
	}
	
	public static void outputStream(HttpServletRequest request,HttpServletResponse response){
        try {
            // 设置浏览器不缓存本页
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Expires", "0");
            // 生成验证码，写入用户session
            String verifyCode = createValidateCode(4);
            request.getSession().setAttribute("imageVerify", verifyCode);
            // 输出验证码给客户端
            response.setContentType("image/jpeg");
            BufferedImage bim = getImageFromCode();
            ImageIO.write(bim, "JPEG", response.getOutputStream());

        } catch (Exception e) {
        }
        return;
    }
    public static void write(BufferedImage image,OutputStream sos) throws IOException {
        ImageIO.write(image, "png", sos);
        sos.close();
    }
    public static void output(BufferedImage image, OutputStream out) throws IOException                  //将验证码图片写出的方法
    {
        ImageIO.write(image, "JPEG", out);
    }
	public static void main(String[] args) {
		System.out.println(createValidateCode());
	}
	


}
