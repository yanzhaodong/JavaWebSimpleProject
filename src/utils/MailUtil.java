package utils;

import javax.mail.Session;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
* 发动邮件相关的工具类
* @author 严照东
*/
public class MailUtil {
	/**
     * @methodsName: sendMail
     * @description: 发送邮件
     * @param: to       收件人地址
     * @param: code     发送过去的激活验证码
     * @return: String
     * @throws: NoSuchAlgorithmException, UnsupportedEncodingException
	 */
	public static void sendMail(String to, String code) {  
		  try {
			//加载properties文件,获取发送人邮箱地址
			Properties properties = new Properties();      
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/Config.properties");
			properties.load(in);
			in.close();
			String from = properties.getProperty("emailFrom");
			String validationCode = properties.getProperty("validationCode");
			String ip = properties.getProperty("ip");
			// 配置
			Properties prop=new Properties();
			// 设置邮件服务器主机名，这里是163
			prop.put("mail.host","smtp.163.com" );
			// 发送邮件协议名称
			prop.put("mail.transport.protocol", "smtp");
			// 是否认证
			prop.put("mail.smtp.auth", true);
			
		    // SSL加密
		    MailSSLSocketFactory sf = null;
		    sf = new MailSSLSocketFactory();
		    // 设置信任所有的主机
		    sf.setTrustAllHosts(true);
		    prop.put("mail.smtp.ssl.enable", "true");
		    prop.put("mail.smtp.ssl.socketFactory", sf);
		 
		    // 创建会话对象
		    Session session = Session.getDefaultInstance(prop, new Authenticator() {
		      // 认证信息，需要提供"用户账号","授权码"
		      public PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(from, validationCode);
		      }
		    });
		    // 是否打印出debug信息
		    session.setDebug(false);
		    // 创建邮件
		    Message message = new MimeMessage(session);
		    // 邮件发送者
		    message.setFrom(new InternetAddress(from));
		    // 邮件接受者
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    //给自己抄送一份，防止被网易怀疑发送垃圾邮件
		    message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(from));
		    // 邮件主题
		    message.setSubject("激活邮件");
		    String content = "<html><head></head><body><h1>请点击连接激活,如果打不开，请复制下列地址到网址栏</h1><h3>"
		    		+ "<a href='http://"+ip+":8080/WebProject/UserServlet?action=activate&code="+code+"'>"
		    		+ "http://"+ip+":8080/WebProject/UserServlet?action=activate&code=" + code + "</href></h3></body></html>";
		    message.setContent(content, "text/html;charset=UTF-8");
		    // 邮件发送
		    Transport transport = session.getTransport();
		    transport.connect();
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
	}  
}
