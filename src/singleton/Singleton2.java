package singleton;

/**
 * 饿汉式 线程不安全
 *
 * @author 江峰
 * @create 2019-12-03 16:04
 */
public class Singleton2 {
	private static Singleton2 singleton = null;

	private Singleton2() {

	}

	public static Singleton2 getSingleton() {
		if (singleton == null) {
			singleton = new Singleton2();
		}
		return singleton;
	}
}
