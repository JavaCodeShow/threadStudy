package executors;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类作用描述
 *
 * @author 江峰
 * @since 2021/7/27
 */
public class RejectedExecutionHandlerTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(2),
                new ThreadPoolExecutor.CallerRunsPolicy()); // 修改拒绝策略测试即可
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":  " + LocalDateTime.now());
            });
        }

    }
}
