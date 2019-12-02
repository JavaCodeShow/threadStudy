package threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 江峰
 * @create 2019-11-29   16:50
 */
public class ThreadLocalTest {
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        final ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        myThreadLocal.set(String.valueOf(count.getAndDecrement()));
                        System.out.println(Thread.currentThread().getName() + "======= " + myThreadLocal.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        myThreadLocal.remove();
                    }
                }
            });
        }
        es.shutdown();
        myThreadLocal.set("hello");
        System.out.println(Thread.currentThread().getName() + "======= " + myThreadLocal.get());
        // System.exit(0);
    }
}
