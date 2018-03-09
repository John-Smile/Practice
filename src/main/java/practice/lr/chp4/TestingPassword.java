package practice.lr.chp4;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;

public class TestingPassword {
	public static void main(String[] args) {
		TestingPassword test = new TestingPassword();
		test.authentication1();
	}
	public void authentication1() {
		Jedis jedis = new Jedis(ConnectionProperties.host);
		jedis.set("foo", "bar");
		System.out.println(jedis.get("foo"));
		jedis.close();
	}
	public void authentication2() {
		Jedis jedis = new Jedis(ConnectionProperties.host);
		jedis.auth("hmk");
		jedis.set("foo", "bar");
		System.out.println(jedis.get("foo"));
		jedis.close();
	}

}
