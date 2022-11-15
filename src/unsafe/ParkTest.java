package unsafe;

import java.util.concurrent.TimeUnit;

public class ParkTest {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("two seconds later, main unpark");
                UnsafeAccessor.getUnsafe().unpark(mainThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("main park");
        UnsafeAccessor.getUnsafe().park(false, 0L);
        System.out.println("main end");
    }

}
