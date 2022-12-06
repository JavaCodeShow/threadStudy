package threadlocal.parentandsonthread.tl;

public class ThreadLocalDemo {
    private static ThreadLocal<Integer> requestIdThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Integer reqId = 5;
        ThreadLocalDemo a = new ThreadLocalDemo();
        a.setRequestId(reqId);
    }

    public void setRequestId(Integer requestId) {
        requestIdThreadLocal.set(requestId);
        doBussiness();
    }

    public void doBussiness() {
        System.out.println("首先打印requestId:" + requestIdThreadLocal.get());
        (new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程启动");
                System.out.println("在子线程中访问requestId:" + requestIdThreadLocal.get());
            }
        })).start();
    }
}