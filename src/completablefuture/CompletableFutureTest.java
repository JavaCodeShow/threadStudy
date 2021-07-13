package completablefuture;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CompletableFutureTest {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static CompletableFuture<String> findAccount(String accountId) {

        return CompletableFuture.supplyAsync(() -> {
            // mock finding account from database
            try {
                System.out.println(Thread.currentThread().getName() + ":start!"
                        + "   时间：" + LocalTime.now());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + ":end!"
                        + "   时间：" + LocalTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "account" + accountId;
        });
    }

    public static void batchProcess(List<String> accountIdList) {
        // 并行根据accountId查找对应account
        List<CompletableFuture<String>> accountFindingFutureList = accountIdList
                .stream().map(accountId -> findAccount(accountId))
                .collect(Collectors.toList());

        // 使用allOf方法来表示所有的并行任务
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(accountFindingFutureList
                        .toArray(new CompletableFuture[accountFindingFutureList
                                .size()]));

        // 下面的方法可以帮助我们获得所有子任务的处理结果
        CompletableFuture<List<String>> finalResults = allFutures
                .thenApply(v -> accountFindingFutureList.stream().map(
                        accountFindingFuture -> accountFindingFuture.join())
                        .collect(Collectors.toList()));

        try {
            List<String> l = finalResults.get();
            System.out.println(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        List<String> l = new ArrayList<String>();
        l.add("11111");
        l.add("22222");
        l.add("33333");
        // batchProcess(l);
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        l.forEach(x -> {
            futureList.add(CompletableFuture.supplyAsync(() -> {
                // 这个就是实际的每一个任务的结果
                return x + " : hello";
            }, executorService));
        });

        List<String> stringList = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(stringList);
    }
}
