import java.io.IOException;
import java.net.SocketException;

import entities.UDPClientSocket;

public class ProgMainUDP_Client {

	public static void main(String[] args) {
//		--- UDP CLIENT TEST ---
		
		try {
			String serverResponse = null;
			
			UDPClientSocket udpClient = new UDPClientSocket();
			
			serverResponse = udpClient.sendAndReceiveMsg("W?", "127.0.0.1", 60001);
			System.out.println(serverResponse);
			
			serverResponse = udpClient.sendAndReceiveMsg("PCKG", "127.0.0.1", 60001);
			System.out.println(serverResponse);
			
			serverResponse = udpClient.sendAndReceiveMsg("BYE", "127.0.0.1", 60001);
			System.out.println(serverResponse);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
