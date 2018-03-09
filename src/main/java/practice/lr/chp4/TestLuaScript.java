package practice.lr.chp4;

import java.util.Arrays;

import practice.lr.chp3.ConnectionProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestLuaScript {
	public String luaScript = Reader.read("src/main/resources/LuaScript");
	private JedisPool  pool = new JedisPool(new JedisPoolConfig(), ConnectionProperties.host);
	Jedis jedis = null;
	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}
	public void setResource(Jedis jedis) {
		pool.returnResource(jedis);
	}
	public static void main(String[] args) {
		TestLuaScript test = new TestLuaScript();
		test.luaScript();
	}
	private void luaScript() {
		Jedis jedis = this.getResource();
		String result = (String) jedis.eval(luaScript,
				Arrays.asList("msg"), 
				Arrays.asList("Learning Redis", "Now I am learning Lua for Redis", "prepare for the test again"));
		System.out.println(result);
		this.setResource(jedis);
	}

}
