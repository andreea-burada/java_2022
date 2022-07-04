import entities.TCPServerSocketMultiT;

public class ProgMainTCP_Server {

	public static void main(String[] args) {
//		--- TCP SERVER TEST ---
		try {
			TCPServerSocketMultiT tcpServer = new TCPServerSocketMultiT(7787);
			tcpServer.setFileName("Phones.bin");
			tcpServer.startTCPServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // main end
}
