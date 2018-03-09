package practice.lr.chp4;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ConnectionManager {
	private static JedisPool jedisPool = new JedisPool(ConnectionProperties.host);
	public static Jedis get() {
		return jedisPool.getResource();
	}
	public static void set(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}
	public static void close() {
		jedisPool.destroy();
	}
	public static void flush() {
		Jedis jedis = jedisPool.getResource();
		jedis.flushDB();
		jedisPool.returnResource(jedis);
	}

}
