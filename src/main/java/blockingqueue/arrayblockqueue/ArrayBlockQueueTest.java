package blockingqueue.arrayblockqueue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列实现生产者消费者模式
 */
public class ArrayBlockQueueTest {
    public static void main(String[] args) {
        BlockingQueue<Object> abq = new ArrayBlockingQueue<>(2);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    abq.put(i);
                    System.out.println("生产了 " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Object take = abq.take();
                    System.out.println(" 消费了 " + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}


