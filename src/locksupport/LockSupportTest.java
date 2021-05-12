package locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 江峰
 * @create 2019-12-02 21:06
 */
public class LockSupportTest {
	public static void main(String[] args) {
		Thread thread = Thread.currentThread();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(3);
					System.out.println("thread");
					LockSupport.unpark(thread);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		LockSupport.park();
		System.out.println("helloworld");
	}
}
