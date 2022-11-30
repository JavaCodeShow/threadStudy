package condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 江峰
 * @create 2019-12-02 22:04
 */

public class MakeFood {
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    c2.await();
                    System.out.println(Thread.currentThread().getName()
                            + "-------菜上好了");
                    TimeUnit.SECONDS.sleep(1);
                    c1.signalAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(10);
        new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    System.out.println(
                            Thread.currentThread().getName() + "菜做好了");
                    TimeUnit.SECONDS.sleep(1);
                    c2.signalAll();
                    c1.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
