package deadlock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 江峰
 * @create 2019-12-04 14:45
 */
public class ThreadPoolDeadLock {
    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Long> f1 = executorService.submit(new Callable<Long>() {

            public Long call() throws Exception {
                System.out.println("start f1");
                Thread.sleep(1000);// 延时
                Future<Long> f2 = executorService.submit(new Callable<Long>() {

                    public Long call() throws Exception {
                        System.out.println("start f2");
                        return -1L;
                    }
                });
                System.out.println("result" + f2.get());
                System.out.println("end f1");
                return -1L;
            }
        });
    }
}
