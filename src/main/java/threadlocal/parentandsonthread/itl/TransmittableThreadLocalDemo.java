package threadlocal.parentandsonthread.itl;

public class TransmittableThreadLocalDemo {
    private static InheritableThreadLocal<Integer> requestIdThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        Integer reqId = new Integer(5);
        TransmittableThreadLocalDemo a = new TransmittableThreadLocalDemo();
        a.setRequestId(reqId);
    }

    public void setRequestId(Integer requestId) {
        requestIdThreadLocal.set(requestId);
        doBussiness();
    }

    public void doBussiness() {
        System.out.println("首先打印requestId:" + requestIdThreadLocal.get());
        new Thread(() -> {
            System.out.println("子线程启动");
            System.out.println("在子线程中访问requestId:" + requestIdThreadLocal.get());
        }).start();
    }
}