package singleton;

/**
 * 饱汉式 线程安全
 *
 * @author 江峰
 * @create 2019-12-03 16:01
 */
public class Singleton1 {
	private static final Singleton1 singleton = new Singleton1();

	private Singleton1() {
	}

	public static Singleton1 getSingleton() {
		return singleton;
	}
}
