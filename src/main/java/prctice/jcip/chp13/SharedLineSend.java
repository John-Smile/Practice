package prctice.jcip.chp13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SharedLineSend {
	private ReentrantLock lock;

	public boolean trySendOnSharedLine(String message,
			                           long timeout, TimeUnit unit) throws InterruptedException {
		long nanosToLock = unit.toNanos(timeout) - estimatedNanosToSend(message);
		
		if (!lock.tryLock(nanosToLock, TimeUnit.NANOSECONDS)) {
			return false;
		}
		try {
			return sendOnSharedLine(message);
		} finally {
			lock.unlock();
		}
	}

	private boolean sendOnSharedLine(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	private long estimatedNanosToSend(String message) {
		// TODO Auto-generated method stub
		return 0;
	}

}
