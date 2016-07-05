package prctice.lr.chp4;

import prctice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;

public class TestingPassword {
	public static void main(String[] args) {
		TestingPassword test = new TestingPassword();
		test.authentication1();
	}
	private void authentication1() {
		Jedis jedis = new Jedis(ConnectionProperties.host);
		jedis.set("foo", "bar");
		System.out.println(jedis.get("foo"));
	}
	private void authentication2() {
		Jedis jedis = new Jedis(ConnectionProperties.host);
		jedis.auth("hmk");
		jedis.set("foo", "bar");
		System.out.println(jedis.get("foo"));
	}

}
