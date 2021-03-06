package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Subject of + 2 points <=> Mark 6 or 7 (and parts of the mark 8):
//	a. Create public class TCPServerSocketMultiT which handles multi-threading TCP server 
//socket connections
//	for implementing a proprietary communication protocol (set of rules)

//	b. The class contains the following private fields:
//	- serverSocket as ServerSocket, port = 50001 as int, f as File and vt as VectThread 
//("has a" relationship)

//	c. The class contains the following methods and constructor:
//		c.1 - constructor which gets the port as parameter and create the serverSocket:
//		public TCPServerSocketMultiT(int port) throws Exception

//		c.2 - getter and setter for the field port

//		c.3 - public void setFileName(String newFName) method which allocate memory for the 
//field f, if and only if, 
//			the String parameter is different than null, otherwise is setting null

//		c.4.- public void startTCPServer() throws IOException method which is having the infinite processing loop and is implementing 3 commands from the proprietary protocol

//			HINTS for startTCPServer method:
//		-create multi-threading by using lambda expressions from Runnable functional 
//interface after the blocking accept() method from serverSocket object

//		-get the input stream as BufferedReader and output stream as ObjectOutputStream

//		-Initialise the vt field from class VectThread by passing the file absolute path
//from f field as parameters AND OBTAIN the list (ArrayList of Phone objects) from the file 

//		-parse line by line the TCP request
//			- if EXIT text command is received over the socket, then break the infinite 
//loop of the processing and send TCP FIN packet back to the TCP client (e.g. by closing socket, etc.)
//			- (mark 6) if GETFILE text command is received over the socket, then reply 
//back the serialised list encapsulated in the vt object field
//			- (mark 7) if GETJSON text command is received over the socket, then reply 
//back with the list in JSON format
//			- (mark 8) if GETDB text command is received over the socket, then reply 
//back with the list as String produced by UtilsDAO.selectData() (please also take into account, you have to initialize JDBC connection and close it with static methods from UtilsDAO);

public class TCPServerSocketMultiT {
	// attributes
	private ServerSocket serverSocket;
	private int port = 50001;
	private File f;
	private VectThread vt;
	protected static int noClients = 0;

	// constructors 
	public TCPServerSocketMultiT(int port) throws Exception {
		setPort(port);
		// create the serverSocket
		serverSocket = new ServerSocket(this.port);
	}

	public void startTCPServer() throws IOException {
		System.out.println("Starting TCP server...\n");

		// device list -> list of objects (Phone)
		vt = new VectThread(f.getAbsolutePath());
		List<ElectronicDevices> phonesList = vt.getDevicesList();

		// processing clients
		while (true) {
			Socket clientSocket = serverSocket.accept();	// get client
			int currentClientNo = ++TCPServerSocketMultiT.noClients;
			// Lambda for TCP
			Runnable server = () -> {
				InputStream clientData = null;
				BufferedReader clientBuffReader = null;

				OutputStream serverData = null;
				ObjectOutputStream serverOutputStream = null;
				try {
					// get client
					boolean isListening = true;
					// input stream -> get data from client
					clientData = clientSocket.getInputStream();
					clientBuffReader = new BufferedReader(new InputStreamReader(clientData));

					// output stream -> send data to client
					serverData = clientSocket.getOutputStream();
					serverOutputStream = new ObjectOutputStream(serverData);

					while (isListening == true) // processing client
					{
						// parsing line by line
						String currentLine;
						while ((currentLine = clientBuffReader.readLine()) != null && currentLine.length() > 0) {
							// serverOutputStream.reset();
							System.out.println("Received from client " + currentClientNo + ": " + currentLine + "\n");

							if ("GETFILE".equals(currentLine)) // if GETFILE text command is received over
							// the socket, then reply
							// back the serialised list encapsulated in the vt object field
							{
								serverOutputStream.writeObject(phonesList);
							} else if ("GETDB".equals(currentLine)) // if GETDB text command is received over
							// the socket, then reply
							// back with the list as String produced by UtilsDAO.selectData() (please also
							// take into account, you have to initialise JDBC connection and close it with
							// static methods from UtilsDAO);
							{
								try {
									UtilsDAO.setConnection();
									serverOutputStream.writeObject(UtilsDAO.selectData());
									UtilsDAO.closeConnection();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							} else if ("GETJSON".equals(currentLine)) // if GETJSON text command is received
							// over the socket, then reply back with the list in JSON format
							{
								JSONObject currentObj;
								JSONArray phonesJSON = new JSONArray();
								for (ElectronicDevices elD : phonesList) {
									Phone currentPhone = (Phone) elD;
									currentObj = new JSONObject();
									try {
										currentObj.put("weight", Float.toString(currentPhone.getWeight()));
										currentObj.put("diagonal", Double.toString(currentPhone.getDiagonal()));
										currentObj.put("producer", currentPhone.getProducer());
									} catch (JSONException e) {
										e.printStackTrace();
									}
									phonesJSON.put(currentObj);
								}
								serverOutputStream.writeObject(phonesJSON.toString());

							} else if ("EXIT".equals(currentLine)) {
								serverOutputStream.writeObject("TCP FIN");
								clientSocket.close(); // close client connection

								isListening = false;
								break; // exit the parsing loop
							} else {
								serverOutputStream.writeObject("IDK what that is");
							}
						}
						// System.out.println("Outside the parse while");

					}
					System.out.println("Goodbye Client! Socket is closed: " + clientSocket.isClosed() + "\n");

				} catch (SocketException sockex) {
					System.out.println("Socket Exception caught.");
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
			}; // lambda end

			// running TCP server -> create thread for each client
			Thread serverThread = new Thread(server);
			System.out.println("Hello Client!\n");
			serverThread.start();
		}
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
