package createthread;

import java.util.concurrent.TimeUnit;

public class ImplRunnable implements Runnable {

    @Override
    public void run() {
        int i = 1 / 0;
        System.out.println("创建了一个线程");
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            new Thread(new ImplRunnable()).start();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("exit");
        } catch (Exception e) {
            System.out.println("我捕获到异常");
        }

    }

}
