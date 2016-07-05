package prctice.lr.chp4;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class PipelineCommandTest {
	Jedis jedis = ConnectionManager.get();
	long starttime_withoutpipeline = 0;
	long starttime_withpipeline = 0;
	long endtime_withoutpipeline = 0;
	long endtime_withpipeline = 0;
	private void getState() {
		System.out.println(" time taken for test without pipeline " + (endtime_withoutpipeline - starttime_withoutpipeline));
		System.out.println(" time taken for test with pipeline " + (endtime_withpipeline - starttime_withpipeline));
	}
	private void checkWithoutPipeline() {
		starttime_withoutpipeline = System.currentTimeMillis();
		for (int keys = 0; keys < 10; keys++) {
			for (int nv = 0; nv < 100; nv++) {
				jedis.hset("keys-" + keys, "name" + nv, "value" + nv);
			}
			for (int nv = 0; nv < 100; nv++) {
				jedis.hget("keys-" + keys, "name" + nv);
			}
		}
		endtime_withoutpipeline = System.currentTimeMillis();
		jedis.flushDB();
	}
	private void checkWithPipeline() {
		starttime_withpipeline = System.currentTimeMillis();
		for (int keys = 0; keys < 10; keys++) {
			Pipeline commandpipe = jedis.pipelined();
			for (int nv = 0; nv < 100; nv++) {
				commandpipe.hset("keys-" + keys, "name" + nv, "value" + nv);
			}
			for (int nv = 0; nv < 100; nv++) {
				commandpipe.hget("keys-" + keys, "name" + nv);
			}
			List<Object> results = commandpipe.syncAndReturnAll();
			for (Object o : results) {
				System.out.println(o);
			}
		}
		endtime_withpipeline = System.currentTimeMillis();
		jedis.flushDB();
	}
	public static void main(String[] args) throws InterruptedException {
		PipelineCommandTest test = new PipelineCommandTest();
		test.checkWithoutPipeline();
		Thread.sleep(1000);
		test.checkWithPipeline();
		Thread.sleep(1000);
		test.getState();
	}

}
