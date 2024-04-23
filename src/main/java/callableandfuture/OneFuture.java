package callableandfuture;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 描述：演示一个Future的使用方法
 */
public class OneFuture {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future1 = service.submit(new CallableTask());
        Future<Integer> future2 = service.submit(new CallableTask());
        try {
            System.out.println(future1.get() + "      " + LocalDateTime.now());
            System.out.println(future2.get() + "      " + LocalDateTime.now());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }

        System.out.println("main over");
    }

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}