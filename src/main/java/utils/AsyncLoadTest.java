package utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncLoadTest {


    public static void main(String[] args) throws Throwable {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AsyncLoadUtils.AsyncLoader loader = AsyncLoadUtils.newLoader();
        AsyncLoadUtils.AsyncFutureResult<String> strTask = loader.submitTask("strTask", () -> {
            System.out.println("async task two");
            return "root";
        }, executorService);
        AsyncLoadUtils.AsyncFutureResult<Integer> numTask = loader.submitTask("numTask", () -> {
            System.out.println("async task one");
            return 123456;
        }, executorService);

        loader.join();
        // Integer num = numTask.getResult();
        // String str = strTask.getResult();
        String str = strTask.getResultOrThrow();
        Integer num = numTask.getResultOrThrow();
        System.out.println(num);
        System.out.println(str);
        executorService.shutdown();
    }
}
