package practice.lr.chp4;

import java.util.ArrayList;
import java.util.List;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;

public class LoadGenerator implements Runnable {
	List<Thread> clients = new ArrayList<Thread>();
	public LoadGenerator() {
		for (int i = 0; i < 50; ++i) {
			clients.add(new Thread(new Sample()));
		}
	}
	@Override
	public void run() {
		for (int i = 0; i < 50; ++i) {
			clients.get(i).start();
		}
	}
	public class Sample implements Runnable {
		Jedis jedis = new Jedis(ConnectionProperties.host);
		@Override
		public void run() {
			int x = 0;
			while (!Thread.currentThread().isInterrupted()) {
				jedis.sadd(Thread.currentThread().getName(), "Some text" + new Integer(x).toString());
				x++;
			}
		}
		
	}

}
