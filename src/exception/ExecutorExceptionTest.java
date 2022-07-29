package exception;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Java捕获线程异常的几种方式：https://blog.csdn.net/pange1991/article/details/82115437?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-82115437-blog-123413430.pc_relevant_multi_platform_featuressortv2removedup&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-82115437-blog-123413430.pc_relevant_multi_platform_featuressortv2removedup&utm_relevant_index=1
 *
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
