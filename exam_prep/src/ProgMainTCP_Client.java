import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import entities.*;

public class ProgMainTCP_Client {

	public static void main(String[] args) {
		int port = 7787;

		try {
			InetAddress address = InetAddress.getByName("127.0.0.1");
			
			Socket socket = new Socket(address, port);
			Socket socket2 = new Socket(address, port);
			
			OutputStream serverOutput = socket.getOutputStream();
			OutputStream serverOutput2 = socket2.getOutputStream();
			PrintWriter writer2Server = new PrintWriter(serverOutput);
			PrintWriter writer2Server2 = new PrintWriter(serverOutput2);

			InputStream serverInput = socket.getInputStream();
			InputStream serverInput2 = socket2.getInputStream();
			ObjectInputStream serverObjInput = new ObjectInputStream(serverInput);
			ObjectInputStream serverObjInput2 = new ObjectInputStream(serverInput2);

			try {
				writer2Server.println("GETFILE\n");
				writer2Server.flush();
				System.out.println("Client 1");
				List<ElectronicDevices> elList = (List<ElectronicDevices>) serverObjInput.readObject();
				for (ElectronicDevices currentElD : elList) {
					Phone currentPh = (Phone) currentElD;
					System.out.println(currentPh);
				}
				System.out.println("\n");

				writer2Server2.println("GETDB");
				writer2Server2.flush();
				String db = (String) serverObjInput2.readObject();
				System.out.println("Client 2\n" + db);

				writer2Server.println("GETJSON");
				writer2Server.flush();
				// JSONArray phonesJSONArray = (JSONArray) serverObjInput.readObject();
				String phonesJSONStrArray = (String) serverObjInput.readObject();
				JSONArray phonesJSONArray;
				try {
					phonesJSONArray = new JSONArray(phonesJSONStrArray);
					System.out.println("Client 1\n" + phonesJSONArray + "\n");
				} catch (JSONException e) {
					e.printStackTrace();
				}

				String received;
				writer2Server.println("EXIT");
				writer2Server.flush();
				received = (String) serverObjInput.readObject();
				System.out.println("Client 1 closed\n" + received + "\n");
				
				writer2Server2.println("EXIT");
				writer2Server2.flush();
				received = (String) serverObjInput2.readObject();
				System.out.println("Client 2 closed\n" + received);
//				while (true)
//				{
//					
//				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			writer2Server.close();
			serverObjInput.close();
			socket.close();
			
			writer2Server2.close();
			serverObjInput2.close();
			socket2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
