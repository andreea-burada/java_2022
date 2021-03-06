import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import entities.ElectronicDevices;
import entities.Phone;

public class ProgMainTCP_Client2 {

	public static void main(String[] args) {
		int port = 7787;
		
		try {
			InetAddress address = InetAddress.getByName("127.0.0.1");
			Socket socket = new Socket(address, port);
			OutputStream serverOutput = socket.getOutputStream();
			PrintWriter writer2Server = new PrintWriter(serverOutput);
			
			InputStream serverInput = socket.getInputStream();
			ObjectInputStream serverObjInput 
				= new ObjectInputStream(serverInput);
			
			try {
				writer2Server.println("GETFILE\n");
				writer2Server.flush();
				
				List<ElectronicDevices> elList = (List<ElectronicDevices>) serverObjInput.readObject();
				for(ElectronicDevices currentElD : elList)
				{
					Phone currentPh = (Phone) currentElD;
					System.out.println(currentPh);
				}
				System.out.println("\n");
				
				writer2Server.println("GETDB");
				writer2Server.flush();
				String db = (String) serverObjInput.readObject();
				System.out.println(db + "\n");
				
				String received;
				writer2Server.println("EXIT");
				writer2Server.flush();
				
				received = (String) serverObjInput.readObject();
				System.out.println(received + "\n");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			writer2Server.close();
			serverObjInput.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
