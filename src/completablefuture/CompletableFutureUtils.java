package completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CompletableFutureUtils<T> {

	/**
	 * 获取CompletableFuture批量异步执行的结果
	 *
	 * @param futureList
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public List<T> batchAsyncRunThenMergeResult(
			List<CompletableFuture<T>> futureList)
			throws ExecutionException, InterruptedException {
		CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(
				futureList.toArray(new CompletableFuture[futureList.size()]));
		CompletableFuture<List<T>> listCompletableFuture = voidCompletableFuture
				.thenApply(v -> futureList.stream()
						.map(completableFuture -> completableFuture.join())
						.collect(Collectors.toList()));
		List<T> ts = listCompletableFuture.get();
		return ts;
	}
}
