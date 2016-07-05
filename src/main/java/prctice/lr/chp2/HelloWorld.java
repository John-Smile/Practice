package prctice.lr.chp2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class HelloWorld {
	private JedisPool pool = new JedisPool("192.168.111.128", 6379);
	
	private void test() {
		try {
			Jedis jedis = pool.getResource();
			jedis.set("MSG", "Hello World");
			String result = jedis.get("MSG");
			System.out.println(" MSG : " + result);
			pool.returnResource(jedis);
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally {
			pool.destroy();
		}
	}
	
	public static void main(String[] args) {
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.test();
	}

}
