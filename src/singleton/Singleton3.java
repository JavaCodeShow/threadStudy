package singleton;

/**
 * 双检锁单例模式的写法：线程安全
 *
 * @author 江峰
 * @create 2019-12-03 16:06
 */
public class Singleton3 {
	private static Singleton3 singleton = null;

	private Singleton3() {

	}

	public Singleton3 getSingleton() {
		if (singleton == null) {
			synchronized (Singleton3.class) {
				if (singleton == null) {
					singleton = new Singleton3();
				}
			}
		}
		return singleton;
	}

}
