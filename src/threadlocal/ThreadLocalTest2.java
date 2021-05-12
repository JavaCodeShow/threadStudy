package threadlocal;

/**
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-05-12 22:48:46
 * @since
 */
public class ThreadLocalTest2 {
	static void fun() {
		ThreadLocal threadLocal = ThreadLocalUtils.getThreadLocal();
		threadLocal.set("name");
	}

	static void goo() {
		ThreadLocal threadLocal = ThreadLocalUtils.getThreadLocal();
		String name = (String) threadLocal.get();
		System.out.println(name);
	}

	public static void main(String[] args) {
		new Thread(() -> {
			fun();
			goo();
		}).start();
	}
}
