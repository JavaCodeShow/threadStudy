package completablefuture;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用CompletableFuture对于集合中的数据,进行批量多线程的操作,不关心返回结果,但是需要等待所有的线程都处理完。
 */
public class CompletableFutureNotResultDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<String> list = Lists.newArrayList("1", "2", "3");
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(list.stream()
                .map(x -> CompletableFuture.runAsync(() -> findById(x), executorService))
                .toArray(CompletableFuture[]::new));
        allFutures.join();
        1
        executorService.shutdown();
        System.out.println("hello world");
        System.out.println(LocalDateTime.now());
    }


    private static String findById(String id) {
        System.out.println(LocalDateTime.now() + "   " + id);
        try {
            TimeUnit.SECONDS.sleep(Integer.parseInt(id));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello: " + id;
    }
}
