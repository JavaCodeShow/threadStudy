package threadlocal;

/**
 * threadLocal工具类。
 *
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-05-12 22:42:43
 * @since
 */
public class ThreadLocalUtils {
    private volatile static ThreadLocal threadLocal;

    /**
     * 获取单例的threadLocal
     *
     * @return
     */
    public static ThreadLocal getThreadLocal() {
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
