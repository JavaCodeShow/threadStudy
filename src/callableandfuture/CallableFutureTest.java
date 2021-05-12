package callableandfuture;

import java.util.concurrent.*;

/**
 * @author 江峰
 * @create 2019-11-29 11:03
 */
public class CallableFutureTest {
	public static void main(String[] args)
			throws ExecutionException, InterruptedException {
		System.out.println(
				Thread.currentThread().getName() + " " + "start main Thread");
		ExecutorService es = Executors.newSingleThreadExecutor();

		// web环境中这里可以是对数据库的请求。提高系统的并发量。
		Callable<String> stringCallable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println(Thread.currentThread().getName() + " "
						+ "start call Thread");
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName() + " "
						+ "end call Thread");
				return "我 返 回 了";
			}
		};
		System.out.println("1");

		// callable任务一旦被提交，主线程将会等待任务完返回结果后在向下继续执行。
		Future<String> result = es.submit(stringCallable);

		// 任务执行完，关闭线程池
		es.shutdown();

		// 获取异步执行的结果，如果没有结果可用，此方法会阻塞直到异步计算完成
		String s = null;
		try {
			// s = result.get);
			boolean cancelled = result.isCancelled();
			System.out.println("cancelled: " + cancelled);
			s = result.get(1, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println("2");
		System.out.println(s + " " + "end main thread");
	}
}
