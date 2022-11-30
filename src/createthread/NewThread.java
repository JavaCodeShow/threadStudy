package createthread;

public class NewThread {

    public static void main(String[] args) {
        new Thread(() -> System.out.println("创建了一个线程")).start();
    }
}
