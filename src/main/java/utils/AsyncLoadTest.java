package utils;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncLoadTest {

    public static void main(String[] args) throws Throwable {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        System.out.println(LocalDateTime.now() + "       " + "start");


        AsyncLoadUtils.AsyncLoader loader = AsyncLoadUtils.newLoader();
        AsyncLoadUtils.AsyncFutureResult<String> helloResult = loader.submitTask("hello", () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + "       " + "hello");
            return "hello";
        }, executorService);

        AsyncLoadUtils.AsyncFutureResult<String> funResult = loader.submitTask("hello", () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + "       " + "fun");
            int i = 1 / 0;
            return "fun";
        }, executorService);
        for (AsyncLoadUtils.AsyncFutureResult result : loader.getResultList()) {
            if (Objects.nonNull(result.getThrowable())) {
                System.out.println(result.getThrowable());
            }
        }

        String result = helloResult.getResult();
        String result1 = funResult.getResult();
        System.out.println(LocalDateTime.now() + "       " + "get result");
        System.out.println(result);
        System.out.println(result1);

        executorService.shutdown();
    }
}
