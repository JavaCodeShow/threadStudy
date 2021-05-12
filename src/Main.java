import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 江峰
 * @create 2019-11-27 16:43
 */

public class Main {
	public static void main(String[] args) throws InterruptedException {
		List<String> names = Arrays.asList("Larry", "Steve", "James");
		AtomicReference<String> str = new AtomicReference<>();
		names.forEach(s -> {
			if ("Steve".equals(s)) {
				str.set(s);
			}
		});
		String s = str.get();
		System.out.println(s);
	}
}
