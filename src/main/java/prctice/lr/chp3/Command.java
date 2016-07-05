package prctice.lr.chp3;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public abstract class Command {
	protected Socket socket;
	public Command() {
		try {
			socket = new Socket(ConnectionProperties.host, ConnectionProperties.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String createPayload(List<String> messageList) {
		int argumentSize = messageList.size();
		StringBuffer payload = new StringBuffer();
		payload.append("*");
		payload.append(argumentSize);
		payload.append("\r\n");
		for (int cursor = 0; cursor < messageList.size(); cursor++) {
			payload.append("$");
			payload.append(messageList.get(cursor).length());
			payload.append("\r\n");
			payload.append(messageList.get(cursor));
			payload.append("\r\n");
		}
		return payload.toString().trim();
	}
	
	public abstract String createPayload();
	public abstract void execute() throws IOException;

}
