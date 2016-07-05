package prctice.lr.chp4;

public class MultiThreadedPipelineCommandTest {
	public static void main(String[] args) throws InterruptedException {
		Thread pipelineClient = new Thread(new PipelineCommand());
		Thread singleCommandClient = new Thread(new SingleCommand());
		pipelineClient.start();
		Thread.sleep(50);
		singleCommandClient.start();
	}
}
