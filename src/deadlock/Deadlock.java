package deadlock;

import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @create 2019-12-04 13:52
 */
public class Deadlock {
	public static void main(String[] args) {
		final Object a = new Object();
		final Object b = new Object();
		Thread threadA = new Thread(() -> {
			synchronized (a) {
				try {
					System.out.println("now i in threadA-locka");
					TimeUnit.SECONDS.sleep(1);
					synchronized (b) {
						System.out.println("now i in threadA-lockb");
					}
				} catch (Exception e) {
					// ignore
				}
			}
		});

		Thread threadB = new Thread(() -> {
			synchronized (b) {
				try {
					System.out.println("now i in threadB-lockb");
					TimeUnit.SECONDS.sleep(1);
					synchronized (a) {
						System.out.println("now i in threadB-locka");
					}
				} catch (Exception e) {
					// ignore
				}
			}
		});

		threadA.start();
		threadB.start();
	}

}
