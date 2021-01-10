package utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Base64;
/**
* 加密相关的工具类
* @author 严照东
*/
public class Md5 {
	/*
     * @methodsName: EncoderByMd5
     * @description: 利用MD5进行加密
     * @return: String
     * @throws: NoSuchAlgorithmException, UnsupportedEncodingException
	 */
	  public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		    //确定计算方法
		    MessageDigest md5 = MessageDigest.getInstance("MD5");
		    Base64.Encoder encoder = Base64.getEncoder();
		    //加密后的字符串
		    String newstr=encoder.encodeToString(md5.digest(str.getBytes("utf-8")));
		    return newstr;
		  }
}
