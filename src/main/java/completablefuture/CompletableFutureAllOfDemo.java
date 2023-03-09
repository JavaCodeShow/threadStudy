package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用CompletableFuture对于集合中的数据，进行批量多线程的操作
 */
public class CompletableFutureAllOfDemo {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CompletableFuture<Void> futures = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> sleep(2), executorService),
                CompletableFuture.runAsync(() -> sleep(1), executorService),
                CompletableFuture.runAsync(() -> sleep(3), executorService)
        );
        futures.join();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("执行总耗时：" + (end - start) + "ms");
    }

    private static void sleep(Integer second) {
        try {
            TimeUnit.SECONDS.sleep(second);
            System.out.println(Thread.currentThread().getName() + "  睡眠 " + second + "秒结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
