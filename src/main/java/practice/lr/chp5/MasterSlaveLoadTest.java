package practice.lr.chp5;

import java.util.ArrayList;
import java.util.List;

import practice.lr.chp3.ConnectionProperties;

public class MasterSlaveLoadTest {
	private List<Thread> threadList = new ArrayList<Thread>();
	public void setup() {
		Thread pumpData = new Thread(new PumpData());
		pumpData.start();
	}
	public void readFromMasterNode() {
		long starttime = System.currentTimeMillis();
		for (int number = 1; number < 11; number++) {
			Thread thread = new Thread(new FetchData(number, starttime, ConnectionProperties.host, 6379));
			threadList.add(thread);
		}
		for (int number = 0; number < 10; number++) {
			Thread thread = threadList.get(number);
			thread.start();
		}
	}
	public void readFromSlaveNodes() {
		long starttime0 = System.currentTimeMillis();
		for (int number = 1; number < 6; number++) {
			Thread thread = new Thread(new FetchData(number, starttime0, ConnectionProperties.host, 6381));
			threadList.add(thread);
		} 
		long starttime1 = System.currentTimeMillis();
		for (int number = 6; number < 11; number++) {
			Thread thread = new Thread(new FetchData(number, starttime1, ConnectionProperties.host, 6380));
			threadList.add(thread);
		}
		for (int number = 0; number < 10; number++) {
			Thread thread = threadList.get(number);
			thread.start();
		}
	}

}
