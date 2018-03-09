package practice.lr.chp5;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class FetchData implements Runnable {
	int endnumber = 0;
	int startnumber = 0;
	JedisPool jedisPool = null;
	long starttime = 0;
	public FetchData(int number, long starttime, String host, int port) {
		endnumber = number * 100000;
		startnumber = endnumber - 100000;
		this.starttime = starttime;
		jedisPool = new JedisPool(host, port);
	}

	@Override
	public void run() {
		Jedis jedis = jedisPool.getResource();
		for (int index = startnumber; index < endnumber; index++) {
			System.out.println("print values for index = " + index + ", message = " + jedis.get("message-" + index));
		}
		long endtime = System.currentTimeMillis();
		System.out.println("TOTAL TIME " + (endtime - starttime));
		
	}

}
