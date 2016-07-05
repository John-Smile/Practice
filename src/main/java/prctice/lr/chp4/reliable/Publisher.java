package prctice.lr.chp4.reliable;

import java.util.Arrays;

import prctice.lr.chp3.ConnectionProperties;
import prctice.lr.chp4.Reader;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Publisher {
	public String luaScript = Reader.read("src/main/resources/ReliableMsging");
	private JedisPool pool = new JedisPool(new JedisPoolConfig(), ConnectionProperties.host);
	Jedis jedis = null;
	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}
	public void setResource(Jedis jedis) {
		pool.returnResource(jedis);
	}
	public static void main(String[] args) {
		Publisher test = new Publisher();
		test.sendingAreliableMessages();
	}
	private void sendingAreliableMessages() {
		Jedis jedis = this.getResource();
		String result = (String) jedis.eval(luaScript,
				Arrays.asList(""),
				Arrays.asList("{type='channel', publishto='client1', msg='" + System.currentTimeMillis() +"'}"));
		System.out.println(result);
		this.setResource(jedis);
	}

}
