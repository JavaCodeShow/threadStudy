package volatiletest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolatileSynchronizedDemo {

    static int num = 0;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int j = 0; j < 5; j++) {
            es.execute(() -> {
                for (int k = 0; k < 10000; k++) {
                    synchronized (VolatileSynchronizedDemo.class) {
                        num++;
                    }
                }
            });
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num = " + num);
        es.shutdown();
    }

}
