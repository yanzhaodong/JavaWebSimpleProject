package utils;

public class Cast {
	//防止cast的时候警告
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
