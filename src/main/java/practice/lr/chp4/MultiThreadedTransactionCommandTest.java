package practice.lr.chp4;

public class MultiThreadedTransactionCommandTest {
	public static void main(String[] args) throws InterruptedException {
		ConnectionManager.flush();
		Thread transactionClient = new Thread(new TransactionCommand());
		Thread singleCommandClient = new Thread(new SingleCommand());
		transactionClient.start();
		Thread.sleep(100);
		singleCommandClient.start();
	}
}
