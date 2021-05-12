package threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @create 2019-12-02 10:40
 */
public class ThreadPoolExecutorTest {
	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
				60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(5));
		for (int i = 0; i < 20; i++) {
			try {
				threadPoolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							TimeUnit.SECONDS.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("hello");
					}
				});
			} catch (Exception e) {

			}
		}
		threadPoolExecutor.shutdown();
	}
}
