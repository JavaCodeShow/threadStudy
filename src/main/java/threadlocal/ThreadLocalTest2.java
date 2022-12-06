package threadlocal;

/**
 * @author 江峰
 */
public class ThreadLocalTest2 {
    static void fun() {
        ThreadLocal<String> threadLocal = ThreadLocalUtils.getThreadLocal();
        threadLocal.set("name");
        threadLocal.set("man");
    }

    static void goo() {
        ThreadLocal<String> threadLocal = ThreadLocalUtils.getThreadLocal();
        String name = threadLocal.get();
        System.out.println(name);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            fun();
            goo();
            // 手动remove,避免内存泄露
            ThreadLocalUtils.getThreadLocal().remove();
        }).start();
    }
}
