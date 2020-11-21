package producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 江峰
 * @date 2020/11/21 0021 11:23
 */

public class ProducerAndConsumerTest1 {

    private static final Lock lock = new ReentrantLock();
    private static final Condition fullCondition = lock.newCondition();
    private static final Condition emptyCondition = lock.newCondition();
    private static final int capaticy = 3;

    public static void main(String[] args) {
        Queue queue = new LinkedList();
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }

    public static class Producer implements Runnable {
        Queue queue;

        Producer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    if (queue.size() == capaticy) {
                        System.out.println("队列满");
                        fullCondition.await();
                    }
                    Object o = new Object();
                    queue.add(o);
                    System.out.println(o);
                    emptyCondition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class Consumer implements Runnable {
        Queue queue;

        Consumer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {

                try {
                    lock.lock();
                    if (queue.size() == 0) {
                        System.out.println("队列空");
                        emptyCondition.await();
                    }
                    Object poll = queue.poll();
                    System.out.println(poll);
                    fullCondition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
