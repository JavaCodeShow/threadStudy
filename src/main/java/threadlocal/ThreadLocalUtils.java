package threadlocal;

/**
 * threadLocal单例工具类。
 * 每个线程内部都会维护一个ThreadLocalMap, key是threadLocal对象，value就是我们往threadLocal里面写入的值。
 */
public class ThreadLocalUtils<T> {
    private volatile static ThreadLocal threadLocal;

    /**
     * 获取单例的threadLocal
     */
    public static <T> ThreadLocal<T> getThreadLocal() {
        if (threadLocal == null) {
            synchronized (ThreadLocalUtils.class) {
                if (threadLocal == null) {
                    threadLocal = new ThreadLocal();
                }
            }
        }
        return threadLocal;
    }
}
