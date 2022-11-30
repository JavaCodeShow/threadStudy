package createthread;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * submit可以获取到线程执行的结果
 */
public class SubmitTest {
    public static void main(String[] args) throws Exception {
        System.out.println(LocalDateTime.now());
        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<Object> future = es.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            // int i = 1 / 0;
            return "666";
        });
        Object result = future.get();
        System.out.println(result + " " + Thread.currentThread());
        System.out.println(LocalDateTime.now());
        System.out.println("exit," + Thread.currentThread());
        es.shutdown();
    }
}
