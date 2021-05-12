package blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * 描述:生产者
 *
 * @author 江峰
 * @create 2019-11-28 17:17
 */

class Producer implements Runnable {

	protected BlockingQueue<Object> queue;

	Producer(BlockingQueue<Object> theQueue) {
		this.queue = theQueue;
	}

	Object getResource() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("生产者 读 中断");
		}
		return new Object();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Object justProduced = getResource();
				System.out.println("生成对象 " + justProduced);
				queue.put(justProduced);
				System.out.println("生产者资源队列大小=  " + queue.size());
			} catch (InterruptedException e) {
				System.out.println("生产者 中断");
			}
		}
	}
}