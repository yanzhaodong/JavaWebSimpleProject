package utils;

import java.util.UUID;
/**
* uuid相关的工具类
* @author 严照东
*/
public class UuidUtil {
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
