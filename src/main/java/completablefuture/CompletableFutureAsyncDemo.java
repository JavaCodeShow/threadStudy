package completablefuture;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 使用CompletableFuture对于集合中的数据，进行批量多线程的操作
 */
public class CompletableFutureAsyncDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        long start = System.currentTimeMillis();

        List<String> list = Lists.newArrayList("1", "2", "3");
        List<String> stringList = list.stream().map(x ->
                CompletableFuture.supplyAsync(() -> findById(x), executorService))
                .collect(Collectors.toList()).stream() // 这里一定需要转换为steam流才能是并行的
                .map(CompletableFuture::join)
                .distinct()
                .collect(Collectors.toList());

        executorService.shutdown();
        System.out.println(stringList);
        System.out.println(System.currentTimeMillis() - start + "ms");

    }

    private static String findById(String id) {
        System.out.println(Thread.currentThread().getName() + "  " + LocalDateTime.now() + "   " + id);
        try {
            TimeUnit.SECONDS.sleep(Integer.parseInt(id));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello: " + id;
    }
}
