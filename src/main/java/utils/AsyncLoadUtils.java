package utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * 使用场景：主线程同步等待多个子线程处理的结果
 * example:
 * <pre>
 *         ExecutorService executorService = Executors.newFixedThreadPool(10);
 *
 *         AsyncLoadUtils.AsyncLoader loader = AsyncLoadUtils.newLoader();
 *
 *         AsyncLoadUtils.AsyncFutureResult<String> strTask = loader.submitTask("strTask", () -> {
 *             System.out.println("async task two");
 *             return "root";
 *         }, executorService);
 *
 *         AsyncLoadUtils.AsyncFutureResult<Integer> numTask = loader.submitTask("numTask", () -> {
 *             System.out.println("async task one");
 *             return 123456;
 *         }, executorService);
 *
 *         loader.join();
 *
 *         // Integer num = numTask.getResult();
 *         // String str = strTask.getResult();
 *
 *         String str = strTask.getResultOrThrow();
 *         Integer num = numTask.getResultOrThrow();
 *
 *         System.out.println(num);
 *         System.out.println(str);
 *         executorService.shutdown();
 * </pre>
 */
public class AsyncLoadUtils {

    /**
     * 创建一个新会话
     */
    public static AsyncLoader newLoader() {
        return new AsyncLoader();
    }

    public static final class AsyncLoader {

        private final List<AsyncFutureResult<?>> futureResultList;

        private boolean closed = false;

        private AsyncLoader() {
            this.futureResultList = new ArrayList<>();
        }

        public List<AsyncFutureResult<?>> getResultList() {
            this.join();
            return Collections.unmodifiableList(futureResultList);
        }

        /**
         * 提交一个任务
         *
         * @param taskName 任务名字
         * @param task     异步任务
         * @param executor 异步线程池
         * @param <R>
         * @return AsyncFutureResult
         */
        public <R> AsyncFutureResult<R> submitTask(
                String taskName,
                Supplier<R> task,
                Executor executor) {
            if (closed) {
                throw new IllegalStateException("cannot submit new task due to session is closed");
            }
            AsyncFutureResult<R> asyncFutureResult = new AsyncFutureResult<>();
            asyncFutureResult.setTaskName(taskName);
            CompletableFuture<R> future = CompletableFuture.supplyAsync(task, executor);
            asyncFutureResult.setFuture(future);
            this.futureResultList.add(asyncFutureResult);
            return asyncFutureResult;
        }

        /**
         * 等待这一批任务结束
         */
        public void join() {
            if (closed) {
                return;
            }
            this.futureResultList.forEach(result -> {
                try {
                    result.setResult(result.getFuture().join());
                } catch (Throwable e) {
                    result.setThrowable(e);
                }
            });
            if (!closed) {
                closed = true;
            }
        }
    }

    public static final class AsyncFutureResult<R> {

        private String taskName;

        private Throwable throwable;

        private R result;

        private CompletableFuture<R> future;

        public R getResult() {
            return throwable == null ? result : null;
        }

        public R getResultOrThrow() throws Throwable {
            if (Objects.nonNull(throwable)) {
                throw throwable;
            }
            return result;
        }

        public String getTaskName() {
            return taskName;
        }

        private void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        private void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }

        private void setResult(Object result) {
            this.result = (R) result;
        }

        private CompletableFuture<R> getFuture() {
            return future;
        }

        private void setFuture(CompletableFuture<R> future) {
            this.future = future;
        }
    }
}
