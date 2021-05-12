package blockingqueue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @create 2019-11-28 17:31
 */
public class ProducerConsumerExample {
	public static void main(String[] args) throws InterruptedException {
		int numProducers = 4;
		int numConsumers = 3;
		LinkedBlockingDeque<Object> myQueue = new LinkedBlockingDeque<>(1);
		for (int i = 0; i < 1; i++) {
			new Thread(new Producer(myQueue)).start();
		}
		for (int i = 0; i < 1; i++) {
			new Thread(new Consumer(myQueue)).start();
		}
		TimeUnit.SECONDS.sleep(1);
		System.exit(0);
	}
}
