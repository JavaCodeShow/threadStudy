import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 江峰
 * @create 2019-11-27   16:43
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Lock l1 = new ReentrantLock();
        final Lock l2 = new ReentrantLock();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                l1.lock();
                try {
                    System.out.println("now i in threadA-locka");
                    Thread.sleep(1000l);
                    l2.lock();
                    System.out.println("now i in threadA-lockb");
                } catch (Exception e) {
                    // ignore
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            public void run() {
                try {
                    l2.lock();
                    System.out.println("now i in threadB-lockb");
                    Thread.sleep(1000l);
                    l1.tryLock(1, TimeUnit.SECONDS);
                    System.out.println("now i in threadB-locka");
                } catch (Exception e) {
                    // ignore
                }
            }
        });

        threadA.start();
        threadB.start();
        // TimeUnit.SECONDS.sleep(2);
        // threadA.interrupt();
        // boolean interrupted = threadA.isInterrupted();
        // System.out.println(interrupted);
    }
}