package exception;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @create 2019-12-02 10:40
 */
public class ExecutorExceptionTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(5));

        // 异常捕获处理
        threadPoolExecutor.setThreadFactory(r -> {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new MyExecutorExceptionHandler());
            return thread;
        });

        for (int i = 0; i < 6; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                if (finalI == 3) {
                    int num = 1 / 0;
                }
                System.out.println("hello   " + finalI);
            });
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println("exit");
        threadPoolExecutor.shutdown();
    }
}
