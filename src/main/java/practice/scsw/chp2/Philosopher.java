package practice.scsw.chp2;

import java.util.Random;

public class Philosopher extends Thread {
	private Chopstick left, right;
	private Random random = new Random();
	
	public Philosopher(Chopstick left, Chopstick right) {
		this.left = left;
		this.right = right;
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(random.nextInt(1000));
				synchronized(left) {
					synchronized(right) {
						Thread.sleep(random.nextInt(1000));
					}
				}
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	public static void main(String[] args) {
		Chopstick[] cs = new Chopstick[5];
		Philosopher[] ps = new Philosopher[5];
		for (int i = 0; i < 5; ++i) {
			Chopstick c = new Chopstick(Long.valueOf(i));
			cs[i] = c;
		}
		for (int i = 0; i < 5; ++i) {
			ps[i] = new Philosopher(cs[i % 5], cs[(i + 1) % 5]);
		}
		for (int i = 0; i < 5; ++i) {
			ps[i].start();
		}
	}

}
