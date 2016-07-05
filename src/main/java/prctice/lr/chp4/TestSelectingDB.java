package prctice.lr.chp4;

import prctice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;

public class TestSelectingDB {
	public static void main(String[] args) {
		TestSelectingDB test = new TestSelectingDB();
		test.commandSelect();
	}
	private void commandSelect() {
		Jedis jedis = new Jedis(ConnectionProperties.host);
		jedis.select(1);
		jedis.set("msg", "Hello world");
		System.out.println(jedis.get("msg"));
		jedis.select(2);
		System.out.println(jedis.get("msg"));
	}

}
