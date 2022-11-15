package unsafe;

import java.util.concurrent.TimeUnit;

public class ParkTest {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("subThread try to unpark mainThread");
                UnsafeAccessor.getUnsafe().unpark(mainThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("park main mainThread");
        UnsafeAccessor.getUnsafe().park(false, 0L);
        System.out.println("unpark mainThread success");
    }

}
