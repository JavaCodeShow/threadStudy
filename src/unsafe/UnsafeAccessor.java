package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeAccessor {

    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    static Unsafe getUnsafe() {
        return unsafe;
    }

    public static void main(String[] args) {
        Unsafe unsafe = getUnsafe();
        System.out.println(unsafe);
    }
}
