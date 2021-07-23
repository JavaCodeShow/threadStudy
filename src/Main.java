import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @create 2019-11-27 16:43
 */

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 我们创建了一个ArrayBlockingQueue，并且设置队列空间为2
        ArrayBlockingQueue<Object> arrayQueue = new ArrayBlockingQueue<Object>(
                1);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            while (true) {
                try {
                    arrayQueue.put("苹果");
                    System.out.println("生产者-苹果");
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.execute(() -> {
            while (true) {
                try {
                    Object take = arrayQueue.take();
                    System.out.println("消费者" + take);
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}