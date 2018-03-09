package practice.lr.chp4;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Publisher {
	private JedisPool pool = new JedisPool(new JedisPoolConfig(), ConnectionProperties.host);
	Jedis jedis = null;
	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}
	public void setResource(Jedis jedis) {
		pool.returnResource(jedis);
	}
	private void publisher() {
		Jedis jedis = this.getResource();
		jedis.publish("news", "Houstan calling texas... message published!!");
	}
	public static void main(String[] args) {
		Publisher test = new Publisher();
		test.publisher();
	}

}
