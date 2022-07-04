import java.io.IOException;
import java.net.SocketException;

import entities.UDPServerSocket;

public class ProgMainUDP_Server {

	public static void main(String[] args) {
//		--- UDP SERVER TEST ---

		try {
			// server
			UDPServerSocket udpServer;
			udpServer = new UDPServerSocket();
			udpServer.processRequest();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	} // main end

}
