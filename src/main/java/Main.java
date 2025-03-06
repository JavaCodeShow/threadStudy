public class Main {
    static volatile boolean flag = false;//volatile变量

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!flag) {
                }
                ;
            }
        }).start();
        Thread.sleep(100);
        flag = true;
        System.out.println("主线程运行完毕");
    }
}

