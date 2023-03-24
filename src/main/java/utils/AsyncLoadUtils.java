package utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;


public class AsyncLoadUtils {


    /**
     * 创建一个新会话
     */
    public static AsyncLoader newLoader() {
        return new AsyncLoader();
    }

    public static final class AsyncLoader {

        private final List<AsyncFutureResult> futureResultList;

        private boolean closed = false;

        private AsyncLoader() {
            this.futureResultList = new ArrayList<>();
        }

        public List<AsyncFutureResult> getResultList() {
            this.join();
            return Collections.unmodifiableList(futureResultList);
        }

        /**
         * 提交一个任务
         *
         * @param loaderName 任务
         * @param task       任务
         * @param executor   异步线程池
         * @param <R>
         * @return AsyncFutureResult
         */
        public <R> AsyncFutureResult<R> submitTask(
                String loaderName,
                Supplier<R> task,
                Executor executor
        ) {
            if (closed) {
                throw new IllegalStateException("cannot submit new task due to session is closed");
            }
            AsyncFutureResult<R> asyncFutureResult = new AsyncFutureResult<>();
            asyncFutureResult.setName(loaderName);
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

        /**
         * 校验器名称
         */
        private String name;

        /**
         * 异常信息
         */
        private Throwable throwable;

        private CompletableFuture<R> future;

        /**
         * 结果信息
         */
        private R result;

        public R getResult() {
            return throwable == null ? result : null;
        }

        public R getResultOrThrow() throws Throwable {
            if (Objects.nonNull(throwable)) {
                throw throwable;
            }
            return result;
        }

        public boolean hasError() {
            return Objects.nonNull(throwable);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }

        public void setResult(R result) {
            this.result = result;
        }

        CompletableFuture<R> getFuture() {
            return future;
        }

        void setFuture(CompletableFuture<R> future) {
            this.future = future;
        }
    }
}
