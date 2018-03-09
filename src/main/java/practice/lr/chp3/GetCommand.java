package practice.lr.chp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GetCommand extends Command {
	private String key;
	public GetCommand(String key) {
		this.key = key;
	}

	@Override
	public String createPayload() {
		List<String> messageList = new ArrayList<String>();
		messageList.add("GET");
		messageList.add(key);
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
			String msg = in.readLine();
			if (!msg.contains("-1")) {
				System.out.println(msg);
				System.out.println(in.readLine());
			} else {
				System.out.println("This key does not exist ! ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
			in.close();
			socket.close();
		}
		
	}

}
