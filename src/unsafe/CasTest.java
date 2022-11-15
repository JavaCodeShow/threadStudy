package unsafe;

public class CasTest {

    private volatile int a;

    public static void main(String[] args) {
        CasTest casTest = new CasTest();
        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                casTest.increment(i);
                System.out.print(casTest.a + " ");
            }
        }).start();
        new Thread(() -> {
            for (int i = 5; i < 10; i++) {
                casTest.increment(i);
                System.out.print(casTest.a + " ");
            }
        }).start();
    }

    private void increment(int x) {
        while (true) {
            try {
                long fieldOffset = UnsafeAccessor.getUnsafe().objectFieldOffset(CasTest.class.getDeclaredField("a"));
                if (UnsafeAccessor.getUnsafe().compareAndSwapInt(this, fieldOffset, x - 1, x))
                    break;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
