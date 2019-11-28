package cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程中的CAS问题
 * 1. 线程失败重视，直到成功。CPU压力大。
 * 2. ABA问题
 * 3. 只能解决单个变量的原子行问题。
 *
 * @author 江峰
 * @create 2019-11-28   14:04
 */

public class Cas {
    // private static int count = 0;
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        Thread.yield();
                        Thread.yield();
                        Thread.yield();
                        Thread.yield();
                        // count++;
                        count.incrementAndGet();
                        Thread.yield();
                        Thread.yield();
                        Thread.yield();
                        Thread.yield();
                    }
                }
            });
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println(count);
        System.exit(0);
    }
}
