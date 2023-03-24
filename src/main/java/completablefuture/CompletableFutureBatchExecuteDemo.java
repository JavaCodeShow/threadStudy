package completablefuture;

import com.google.common.collect.Lists;

import java.util.Collection;
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
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7");
        List<List<String>> partitionList = Lists.partition(list, 2);
        List<String> resultList = partitionList.stream()
                .map(x -> CompletableFuture.supplyAsync(() -> findByIdList(x), executorService))
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        executorService.shutdown();
        System.out.println(resultList);
    }

    private static List<String> findByIdList(List<String> idList) {
        System.out.println(idList);
        return idList.stream().map(id -> "hello: " + id).collect(Collectors.toList());
    }
}
