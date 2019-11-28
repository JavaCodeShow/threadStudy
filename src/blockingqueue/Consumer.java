package blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * 消费者
 *
 * @author 江峰
 * @create 2019-11-28   17:28
 */
class Consumer implements Runnable {
    protected BlockingQueue<Object> queue;

    Consumer(BlockingQueue<Object> theQueue) {
        this.queue = theQueue;
    }


    void take(Object obj) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("消费者 读 中断");
        }
        System.out.println("消费对象 " + obj);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object obj = queue.take();
                System.out.println("消费者资源队列大小=  " + queue.size());
                take(obj);
            } catch (InterruptedException e) {
                System.out.println("消费者 中断");
            }
        }
    }
}
