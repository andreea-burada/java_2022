package entities;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class TCPServerSocketMultiT {
	// attributes
	private ServerSocket serverSocket;
	private int port = 50001;
	private File f;
	private VectThread vt;
	
	// constructors
	public TCPServerSocketMultiT(int port) throws Exception
	{
		// create the serverSocket
		try {
		 serverSocket = new ServerSocket(port);
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
		}
	}

	public void startTCPServer() throws IOException
	{
		Socket clientSocket = serverSocket.accept();
	
		Runnable task = () -> {
			BufferedReader inputBuffStr = null;
			InputStream inputStr = null;
			try {
				inputStr = clientSocket.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputBuffStr = new BufferedReader(new InputStreamReader(inputStr));
			
			// device list
			vt = new VectThread(f.getAbsolutePath());
			vt.run();
			List<ElectronicDevices> devicesList = vt.getDevicesList();
			
			// TCP parsing
			while (true)
			{
				
			}
		};
	}
	
	public void setFileName(String newFName) {
		if (newFName != null)
			f = new File(newFName);
		else
			f = null;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
