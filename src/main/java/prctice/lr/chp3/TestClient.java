package prctice.lr.chp3;

public class TestClient {
	public void execute(Command command) {
		try {
			command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TestClient testClient = new TestClient();
		SetCommand set = new SetCommand("MSG", "Hello world : simple test client");
		testClient.execute(set);
		
		GetCommand get = new GetCommand("MSG");
		testClient.execute(get);
	}

}
