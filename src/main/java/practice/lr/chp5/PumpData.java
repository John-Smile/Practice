package practice.lr.chp5;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;

public class PumpData implements Runnable {

	@Override
	public void run() {
		Jedis jedis = new Jedis(ConnectionProperties.host, 6379);
		for (int index = 1; index < 1000000; index++) {
			jedis.append("message-" + index, "my dumb value " + index);
		}
		jedis.close();
	}

}
