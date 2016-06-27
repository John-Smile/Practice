package prctice.jcip.chp14;

public class SynInterruption {
	public static void main(String[] args) throws Exception {
		final Object lock = new Object();
		Thread t1 = new Thread() {
			public void run() {
				synchronized(lock) {
					try {
						while(true) {
							System.out.println("waiting...");
							lock.wait();
						}
					} catch (InterruptedException e) {
						System.out.println(e);
						e.printStackTrace();
					}
					
				}
			}
		};
		t1.start();
		Thread.sleep(5000);
		Thread t2 = new Thread() {
			public void run() {
				synchronized(lock) {
					try {
						System.out.println("sleeping...");
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t2.start();
		Thread.sleep(5000);
		System.out.println("interrupting");
		t1.interrupt();
//		Thread.sleep(5000);
//		System.out.println("interrupting sleeping...");
//		t2.interrupt();
	}

}
