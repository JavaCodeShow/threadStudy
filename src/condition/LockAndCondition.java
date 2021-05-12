package condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 江峰
 * @create 2019-12-02 21:32
 */
public class LockAndCondition {
	public static void main(String[] args) {
		final ReentrantLock lock = new ReentrantLock();
		// condition是一个实现多线程间通信的工具类。
		Condition condition = lock.newCondition();
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				try {
					System.out.println(
							Thread.currentThread().getName() + "我获取到锁了");
					System.out.println(
							Thread.currentThread().getName() + "我开始等待");
					condition.await();
					System.out.println(
							Thread.currentThread().getName() + "我等待结束了");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
					System.out.println(
							Thread.currentThread().getName() + "我释放锁了");
				}
			}
		});
		es.execute(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				try {
					System.out.println(
							Thread.currentThread().getName() + "我拿到锁了");
					TimeUnit.SECONDS.sleep(2);
					condition.signal();
					System.out.println(
							Thread.currentThread().getName() + "我释放了一个信号");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
					System.out.println(
							Thread.currentThread().getName() + "我释放锁了");
				}
			}
		});
		es.shutdown();
		System.out.println("end main");

	}
}
