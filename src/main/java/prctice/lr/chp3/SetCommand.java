package prctice.lr.chp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SetCommand extends Command {
	private String key;
	private String value;
	public SetCommand(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String createPayload() {
		List<String> messageList = new ArrayList<String>();
		messageList.add("SET");
		messageList.add(key);
		messageList.add(value);
		return super.createPayload(messageList);
	}

	@Override
	public void execute() throws IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(super.socket.getOutputStream(), true);
			out.println(this.createPayload());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.flush();
			out.close();
			socket.close();
		}
		
	}

}
