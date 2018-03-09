package practice.lr.chp5;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;

public class MasterSlaveTest {
	public static void main(String[] args) throws InterruptedException {
		MasterSlaveTest test = new MasterSlaveTest();
		test.masterslave();
	}
	
	public void masterslave() throws InterruptedException {
		Jedis master = new Jedis(ConnectionProperties.host, 6379);
		Jedis slave = new Jedis(ConnectionProperties.host , 6380);
		master.append("msg", "Learning Redis");
		System.out.println("Getting message from master: " + master.get("msg"));
		System.out.println("Getting message from slave: " + slave.get("msg"));
		master.shutdown();
		master.close();
		slave.slaveofNoOne();
		slave.append("msg", " slave becomes the master");
		System.out.println("Getting message from slave turned master: " + slave.get("msg"));
		System.out.println("First sleeping...");
		Thread.sleep(20000);
		master = new Jedis(ConnectionProperties.host, 6379);
		master.slaveof(ConnectionProperties.host, 6380);
		System.out.println("Second sleeping...");
		Thread.sleep(20000);
		System.out.println("Getting message from master turned slave: " + master.get("msg"));
		master.append("msg", "throw some exceptions!!");
		slave.close();
		master.close();
	}

}
