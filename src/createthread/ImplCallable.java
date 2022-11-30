package createthread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 实现了Callable接口的线程，可以获取线程执行结果的返回值，也会感受到线程的执行异常，如果异常了，会阻断程序的运行。
 */
public class ImplCallable implements Callable<String> {

    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(new ImplCallable());
        new Thread(futureTask).start();
        String str = futureTask.get();
        System.out.println(str);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("exit," + Thread.currentThread());
    }

    @Override
    public String call() throws Exception {
        int i = 1 / 0;
        System.out.println("创建了一个线程," + Thread.currentThread());
        return "666";
    }
}
