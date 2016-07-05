package practice.jcip.chp12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;

public class TestingThreadFactory implements ThreadFactory {
	public final AtomicInteger numCreated = new AtomicInteger();
	private final ThreadFactory factory = Executors.defaultThreadFactory();

	@Override
	public Thread newThread(Runnable r) {
		numCreated.incrementAndGet();
		return factory.newThread(r);
	}
	
	public void testPoolExpansion() throws InterruptedException {
		int MAX_SIZE = 10;
		ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE);
		for (int i = 0; i < 10 * MAX_SIZE; i++) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			});
		}
		
		for (int i = 0; i < 20 && numCreated.get() < MAX_SIZE; ++i) {
			Thread.sleep(100);
		}
		Assert.assertEquals(numCreated.get(), MAX_SIZE);
		exec.shutdown();
	}

}
