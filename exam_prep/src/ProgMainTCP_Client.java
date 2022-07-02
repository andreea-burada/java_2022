import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ProgMainTCP_Client {

	public static void main(String[] args) {
		int port = 7787;
		
		try {
			InetAddress address = InetAddress.getByName("127.0.0.1");
			Socket socket = new Socket(address, port);
			OutputStream serverOutput = socket.getOutputStream();
			PrintWriter writer2Server = new PrintWriter(serverOutput);
			writer2Server.println("GETDB");
			
			InputStream serverInput = socket.getInputStream();
			BufferedReader serverBuffReader 
				= new BufferedReader(new InputStreamReader(serverInput));
			
			String current = "";
			StringBuffer allData = new StringBuffer("");
			while ((current = serverBuffReader.readLine()) != null)
			{
				allData.append(current);
			}
			System.out.println(current + "\n\n");
			writer2Server.println("EXIT");
			allData.delete(0, allData.length());
			while ((current = serverBuffReader.readLine()) != null
					&& (current.length() > 0))
			{
				allData.append(current);
			}
			System.out.println(current + "\n\n");
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
