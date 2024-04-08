package deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 死锁检测,检测代码有没有死锁产生
 */
public class DeadlockDetector {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.findDeadlockedThreads();

        if (threadIds != null) {
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds);
            System.out.println("Detected Deadlock Threads:");
            for (ThreadInfo threadInfo : threadInfos) {
                System.out.println(threadInfo.getThreadName());
            }
        } else {
            System.out.println("No Deadlock Detected.");
        }
    }
}
