package utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Base64;

public class Md5 {
	   /**利用MD5进行加密*/
	  public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		    //确定计算方法
		    MessageDigest md5 = MessageDigest.getInstance("MD5");
		    Base64.Encoder encoder = Base64.getEncoder();
		    //加密后的字符串
		    String newstr=encoder.encodeToString(md5.digest(str.getBytes("utf-8")));
		    return newstr;
		  }
		   
	  /**判断用户密码是否正确
	   *newpasswd 用户输入的密码
	   *oldpasswd 正确密码*/
	  public boolean check(String newStr,String oldStr) throws NoSuchAlgorithmException, UnsupportedEncodingException{
	    if(EncoderByMd5(newStr).equals(oldStr))
	      return true;
	    else
	      return false;
	  }
}
