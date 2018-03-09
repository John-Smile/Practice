package practice.jcip.chp13;

public class IntrisicLock {
	
	public static void main(String[] args) throws Exception {
		final Object lock = new Object();
		Thread t1 = new Thread() {
			public void run() {
				synchronized (lock) {
					try {
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();
		Thread.sleep(1000);
		Thread t2 = new Thread() {
			public void run() {
				try {
					System.out.println("I am here");
					synchronized(lock) {
						System.out.println("I am in.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		t2.start();
		Thread.sleep(5000);
		t2.interrupt();
		System.out.println("interrupted");
	}

}
