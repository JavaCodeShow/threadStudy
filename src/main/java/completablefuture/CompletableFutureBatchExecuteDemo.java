package completablefuture;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 使用CompletableFuture对于集合中的数据，进行批量多线程的操作
 */
public class CompletableFutureBatchExecuteDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 单个
        // List<String> list = new ArrayList<>();
        // list.add("1");
        // list.add("2");
        // list.add("3");
        // List<CompletableFuture<String>> futureList = new ArrayList<>();
        // list.forEach(x -> futureList.add(CompletableFuture.supplyAsync(() -> findById(x), executorService)));
        // List<String> stringList = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());

        // 批量
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7");
        List<List<String>> partitionList = Lists.partition(list, 2);
        List<CompletableFuture<List<String>>> futureList = new ArrayList<>();
        partitionList.forEach(x -> futureList.add(CompletableFuture.supplyAsync(() -> findByIdList(x), executorService)));
        List<String> stringList = futureList.stream().map(CompletableFuture::join).flatMap(Collection::stream).collect(Collectors.toList());

        executorService.shutdown();
        System.out.println(stringList);
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
