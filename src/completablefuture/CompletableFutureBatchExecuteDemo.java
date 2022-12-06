package completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 使用CompletableFuture对于集合中的数据，进行批量多线程的操作
 */
public class CompletableFutureBatchExecuteDemo {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> list = new ArrayList<>();
        list.add("11111");
        list.add("22222");
        list.add("33333");
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        list.forEach(x -> futureList.add(CompletableFuture.supplyAsync(() -> x + " : hello", executorService)));
        List<String> stringList = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        executorService.shutdown();
        System.out.println(stringList);
    }
}
