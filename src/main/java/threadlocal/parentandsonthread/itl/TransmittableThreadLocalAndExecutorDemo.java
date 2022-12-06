package threadlocal.parentandsonthread.itl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在线程池里面使用itl会由于线程复用导致获取到的值不是父线程里面的值
 */
public class TransmittableThreadLocalAndExecutorDemo implements Runnable {
    private static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws Exception {
        System.out.println("----主线程启动");
        inheritableThreadLocal.set("主线程第一次赋值");
        System.out.println("----主线程设置后获取值：" + inheritableThreadLocal.get());
        executorService.submit(new TransmittableThreadLocalAndExecutorDemo());
        System.out.println("主线程休眠2秒");
        Thread.sleep(2000);
        inheritableThreadLocal.set("主线程第二次赋值");
        System.out.println("----主线程设置后第二次获取值：" + inheritableThreadLocal.get());
        executorService.submit(new TransmittableThreadLocalAndExecutorDemo());
        executorService.shutdown();
    }

    @Override
    public void run() {
        System.out.println("----子线程获取值：" + inheritableThreadLocal.get());
    }
}
