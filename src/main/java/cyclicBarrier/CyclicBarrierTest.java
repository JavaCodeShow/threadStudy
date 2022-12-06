package cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                // 2、这是三个人都到齐之后会执行的代码
                System.out.println("三个人都已到达会议室");
            }
        });

        for (int i = 1; i <= 3; i++) {
            final int finalI = i;
            new Thread(() -> {
                try {
                    // 4、模拟每人到会议室所需时间
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第" + Thread.currentThread().getName() + "个人到达会议室");
                // 5、等待其他人到会议室
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始开会");
            }, String.valueOf(finalI)).start();
        }
        System.out.println("exit");
    }
}
