package practice.lr.chp5;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class PushDataMaster {
	public static void main(String[] args) {
		PushDataMaster test = new PushDataMaster();
		test.pushData();
	}
	
	private void pushData() {
		Jedis jedis = new Jedis(ConnectionProperties.host);
		Pipeline pipeline = jedis.pipelined();
		for (int nv = 0; nv < 900000; nv++) {
			pipeline.sadd("MSG", ",data-" + nv);
		}
		pipeline.sync();
		jedis.close();
	}
}
