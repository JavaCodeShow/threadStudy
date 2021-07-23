package semaphore;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 类作用描述
 *
 * @author 江峰
 * @since 2021/7/23
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore sp = new Semaphore(1);

        //循环10遍相当于创建了10个线程
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try {

                    sp.acquire();//获得一盏信号灯，该线程就可以运行了

                    System.out.println("线程" + Thread.currentThread().getName() +
                            "进入，当前已有" + (10 - sp.availablePermits()) + "个并发" + "时间 = " + LocalDateTime.now());//获取了可获得许可数

                    TimeUnit.SECONDS.sleep(1);

                    sp.release();//线程离开，就释放灯

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };

            service.execute(runnable);
        }
    }

}
