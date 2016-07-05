package practice.lr.chp4;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;

public class TestEchoAndPing {
	public static void main(String[] args) throws InterruptedException {
		TestEchoAndPing echoAndPing = new TestEchoAndPing();
		Thread thread = new Thread(new LoadGenerator());
		thread.start();
		while(true) {
			Thread.sleep(100);
			echoAndPing.testPing();
			echoAndPing.testEcho();
		}
	}
	private void testPing() {
		long start = System.currentTimeMillis();
		Jedis jedis = new Jedis(ConnectionProperties.host);
		System.out.println(jedis.ping() + " in " + (System.currentTimeMillis() - start) + " milliseconds");
	}
	private void testEcho() {
		long start = System.currentTimeMillis();
		Jedis jedis = new Jedis(ConnectionProperties.host);
		System.out.println(jedis.echo("hi Redis ") + " in " + (System.currentTimeMillis() - start) + " milliseconds");
	}

}
