/**
 * @author 江峰
 * @create 2019-11-27 16:43
 */

public class Main {

    // private static final Main main = new Main();

    private static Main main = null;

    private Main() {

    }

    public static Main getMain() {

        if (main == null) {
            synchronized (Main.class) {
                if (main == null) {
                    main = new Main();
                }
            }
        }
        return main;
    }
}
