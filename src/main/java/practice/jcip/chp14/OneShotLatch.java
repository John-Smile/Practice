package practice.jcip.chp14;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {
	private final Sync sync = new Sync();
	
	public void signal() throws InterruptedException {
		sync.releaseShared(0);
	}
	
	public void await() throws InterruptedException {
		sync.acquireSharedInterruptibly(0);
	}
	
	private class Sync extends AbstractQueuedSynchronizer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -9109437606212832320L;

		protected int tryAcquireShared(int ignored) {
			return (getState() == 1) ? 1 : -1;
		}
		
		protected boolean tryReleaseShared(int ignored) {
			setState(1);
			return true;
		}
		
	}

}
