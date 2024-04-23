import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务2，当前线程是:" + Thread.currentThread().getId());
            int result = 1 + 2;
            int i = 1 / 0;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步任务2结束");
            return result;
        }, executorService);
        System.out.println(task2.get(3, TimeUnit.SECONDS));
        System.out.println("end");
        executorService.shutdown();

    }
}
