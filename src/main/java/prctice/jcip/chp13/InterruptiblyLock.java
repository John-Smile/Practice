package prctice.jcip.chp13;

import java.util.concurrent.locks.ReentrantLock;

public class InterruptiblyLock {
	private ReentrantLock lock;
	public boolean sendOnSharedLine(String message) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			return cancellableSendOnSharedLine(message);
		} finally {
			lock.unlock();
		}
	}
	private boolean cancellableSendOnSharedLine(String message) {
		// TODO Auto-generated method stub
		return false;
	}

}
