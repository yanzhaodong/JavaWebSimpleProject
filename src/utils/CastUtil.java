package utils;

/**
* 防止cast的时候出现警告
* @author 严照东
*/
public class CastUtil {
	/**
     * @methodsName: cast
     * @param: obj          需要被cast的对象
     * @return: Object		cast后的对象
     * @throws: Exception
	 */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
