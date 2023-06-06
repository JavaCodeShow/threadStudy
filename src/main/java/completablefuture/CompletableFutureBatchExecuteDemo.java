package completablefuture;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 使用CompletableFuture对于集合中的数据，进行批量多线程的操作
 */
public class CompletableFutureBatchExecuteDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7");
        List<List<String>> partitionList = Lists.partition(list, 2);
        List<String> resultList = partitionList.stream()
                .map(x -> CompletableFuture.supplyAsync(() -> findByIdList(x), executorService))
                .reduce((f1, f2) -> f1.thenComposeAsync(res1 -> f2.thenApply(res2 -> {
                    res1.addAll(res2);
                    return res1;
                })))
                .map(CompletableFuture::join)
                .orElse(Collections.emptyList());
        executorService.shutdown();
        System.out.println(resultList);
        System.out.println(LocalDateTime.now());
    }

    private static List<String> findByIdList(List<String> idList) {
        System.out.println(LocalDateTime.now() + "   " + idList.get(0));
        try {
            TimeUnit.SECONDS.sleep(Integer.parseInt(idList.get(0)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return idList.stream().map(id -> "hello: " + id).collect(Collectors.toList());
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
