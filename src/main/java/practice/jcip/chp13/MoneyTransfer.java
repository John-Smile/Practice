package practice.jcip.chp13;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MoneyTransfer {
	private Random rnd;

	public boolean transferMoney(Account fromAcct,
			                     Account toAcct,
			                     DollarAmount amount,
			                     long timeout,
			                     TimeUnit unit) throws InsufficientFundsException, InterruptedException {
		long fixedDelay = getFixedDelayComponenetNanos(timeout, unit);
		long randMod = getRandomDelayModulusNanos(timeout, unit);
		long stopTime = System.nanoTime() + unit.toNanos(timeout);
		
		while(true) {
			if (fromAcct.lock.tryLock()) {
				try {
					if (toAcct.lock.tryLock()) {
						try {
							if (fromAcct.getBalance().compareTo(amount) < 0) {
								throw new InsufficientFundsException();
							} else {
								fromAcct.debit(amount);
								toAcct.credit(amount);
								return true;
							}
						} finally {
							toAcct.lock.unlock();
						}
					}
				} finally {
					fromAcct.lock.unlock();
				}
			}
			if (System.nanoTime() > stopTime) {
				return false;
			}
			TimeUnit.NANOSECONDS.sleep(fixedDelay + rnd.nextLong() % randMod);
		}
	}

	private long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	private long getFixedDelayComponenetNanos(long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

}
