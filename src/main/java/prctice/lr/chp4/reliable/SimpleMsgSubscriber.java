package prctice.lr.chp4.reliable;

import java.util.Arrays;

import prctice.lr.chp4.ConnectionManager;
import prctice.lr.chp4.Reader;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class SimpleMsgSubscriber {
	static Thread lostMsgWorker;
	static Thread msgWorker;
	public static void main(String[] args) {
		SimpleMsgSubscriber source = new SimpleMsgSubscriber();
//		msgWorker = new Thread(source.new MsgProcessor());
		lostMsgWorker = new Thread(source.new LostMsgProcessor());
//		msgWorker.start();
		lostMsgWorker.start();
	}
	public class MsgProcessor extends JedisPubSub implements Runnable {
		Jedis jedis = ConnectionManager.get();

		@Override
		public void run() {
			jedis.subscribe(this, "client1");
			
		}

		@Override
		public void onMessage(String channel, String message) {
			System.out.println("processing the msg = " + message);
			
		}

		@Override
		public void onPMessage(String pattern, String channel, String message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSubscribe(String channel, int subscribedChannels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUnsubscribe(String channel, int subscribedChannels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPUnsubscribe(String pattern, int subscribedChannels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPSubscribe(String pattern, int subscribedChannels) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class LostMsgProcessor implements Runnable {
		Jedis jedis = ConnectionManager.get();

		@Override
		public void run() {
			Jedis jedis = ConnectionManager.get();
			String msg;
			while ((msg=jedis.spop("MSGBOX")) != null) {
				MessageHandler.push(msg);
			}
		}
	}
	
	public static class MessageHandler {
		static Jedis jedis = ConnectionManager.get();
		public static void push(String msg) {
			String luaScript = "";
			luaScript = Reader.read("src/main/resources/ReliableMsging");
			jedis.eval(luaScript,
					Arrays.asList(""),
					Arrays.asList("{type='channel', publishto='client1', msg='" + msg + "'}"));
		}
	}

}
