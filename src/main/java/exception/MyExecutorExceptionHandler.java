package exception;

/**
 * @author 江峰
 * @date 2022/7/29 15:44
 */
public class MyExecutorExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        System.out.println("错误原因：" + e);
    }
}
