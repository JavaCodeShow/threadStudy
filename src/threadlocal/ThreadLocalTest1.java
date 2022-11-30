package threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 江峰
 * @create 2019-11-29 16:50
 */
public class ThreadLocalTest1 {
    private static final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocal<String> myThreadLocal = new ThreadLocal<>();
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            TimeUnit.MILLISECONDS.sleep(i);
            es.execute(() -> {
                try {
                    myThreadLocal.set(String.valueOf(count.getAndIncrement()));
                    System.out.println(Thread.currentThread().getName() + "======= " + myThreadLocal.get());
                } finally {
                    // 手动remove,避免内存泄露
                    myThreadLocal.remove();
                }
            });
        }
        es.shutdown();
        myThreadLocal.set("hello");
        System.out.println(Thread.currentThread().getName() + "======= " + myThreadLocal.get());
        // 手动remove,避免内存泄露
        myThreadLocal.remove();
    }
}
