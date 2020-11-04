import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 江峰
 * @create 2019-11-27   16:43
 */
public class Main {
    public static void main(String[] args) {
        final Lock l1 = new ReentrantLock();
        final Lock l2 = new ReentrantLock();
        Thread threadA = new Thread(() -> {
            l1.lock();
            try {
                System.out.println("now i in threadA-locka");
                Thread.sleep(1000L);
                l2.lock();
                System.out.println("now i in threadA-lockb");
            } catch (Exception e) {
                // ignore
            }
        });
    }

    public synchronized void hello() {
        System.out.println("synchronized 代码块");
    }
}