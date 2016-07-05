package practice.jcip.chp12;

import org.junit.Assert;
import org.junit.Test;

public class BoundedBufferTest {

	@Test
	public void testIsEmptyWhenConstructed() {
		BoundedBuffer<Integer> bb =  new BoundedBuffer<Integer>(10);
		Assert.assertTrue(bb.isEmpty());
		Assert.assertFalse(bb.isFull());
	}
	
	@Test
	public void testIsFullAfterPuts() throws InterruptedException {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		for (int i = 0; i < 10; i++) {
			bb.put(i);
		}
		Assert.assertTrue(bb.isFull());
		Assert.assertFalse(bb.isEmpty());
	}
	
	@Test
	public void testTakeBlockWhenEmpty() {
		final long LOCKUP_DETECT_TIMEOUT = 1000L; 
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					bb.take();
					fail();
				} catch (InterruptedException success) {
					
				}
			}
		};
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			Assert.assertFalse(taker.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}
	
	private void fail() {
		System.err.println("testTakeBlockWhenEmpty fails");
	}
}
