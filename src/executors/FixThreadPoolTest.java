package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @create 2019-11-28 16:12
 */
public class FixThreadPoolTest {
    public static void main(String[] args) {
        // 创建了数量为3的线程池
        ExecutorService es = Executors.newFixedThreadPool(3);
        // 通过这个线程池来执行任务，
        for (int i = 0; i < 4; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
