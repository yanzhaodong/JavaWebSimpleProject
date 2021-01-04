package utils;

public class StringUtil {
	// NULL 在用户没有在请求报文中传输该KEY的时候发生
	// "" 是用户在表单没有输入数据的情况下发生
	public static boolean isEmpty(String arg){
		return arg == null || arg.equals("");
	}

	public static boolean isNotEmpty(String arg){
		return arg != null && !arg.equals("");
	}
}

