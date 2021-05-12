package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 江峰
 * @create 2019-12-02 11:26
 */
public class JoinCountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		// makeThreadAwaitByJoin();
		makeThreadAwaitByCountDownLatch();
	}

	static void makeThreadAwaitByCountDownLatch() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(2);
		ExecutorService es = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 2; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 5; i++) {
						System.out.println(
								Thread.currentThread().getName() + " : " + i);
					}
					countDownLatch.countDown();
				}
			});
		}
		es.shutdown();
		countDownLatch.await();
		System.out.println("end");

	}

	static void makeThreadAwaitByJoin() throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					System.out.println(
							Thread.currentThread().getName() + " : " + i);
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					System.out.println(
							Thread.currentThread().getName() + " : " + i);
				}
			}
		});
		t1.start();
		// t2.start();
		t1.join();
		// t2.join();
		System.out.println("end");
	}
}
