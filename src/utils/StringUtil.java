package utils;

/**
* 字符串相关的工具类
* @author 严照东
*/

public class StringUtil {
	public static boolean isEmpty(String arg){
		return arg == null || arg.equals("");
	}

	public static boolean isNotEmpty(String arg){
		return arg != null && !arg.equals("");
	}
}

