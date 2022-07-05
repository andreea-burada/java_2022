package entities;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

//Subject+2 points <=> Mark 9/10:
//Create public class UDPServerSocket which implements a proprietary communication protocol and implements AutoCloseable 
//(override specific method)
//It has 2 fields: socket - DatagramSocket and bindPort - int
//It contains the following constructor methods:

//a. public UDPServerSocket() throws SocketException - init bindPort on 60001

//b. public int getBindPort() - returns the bindPord field

//c. public void processRequest() throws IOException - receive UDP packets and process them (without infinite loop) with the following rules:
//		- if the request contains W? , then the reply UDP packet contains as pay-load "UDPS".
//		- if the request contains BYE , then the reply UDP packet contains as pay-load "BYE ACK" and close the resources (e.g. socket)
//		- if the request contains any other pay-load , then the reply UDP packet contains as pay-load "ACK".


public class UDPServerSocket implements AutoCloseable {
	// attributes
	protected DatagramSocket socket;
	protected int bindPort;
	
	public UDPServerSocket() throws SocketException
	{
		bindPort = 60001;
		socket = new DatagramSocket(bindPort);	// Initialise socket on port 60001
		System.out.println("Starting UDP server...\n");
	}
	
//		- if the request contains W? , then the reply UDP packet contains as pay-load "UDPS"
//		- if the request contains BYE , then the reply UDP packet contains as pay-load "BYE ACK" and close the resources (e.g. socket)
//		- if the request contains any other pay-load , then the reply UDP packet contains as pay-load "ACK"
	public void processRequest() throws IOException
	{
		byte[] receivedRequest = null;
		byte[] response = null;
		DatagramPacket packetToClient = null;
		DatagramPacket packetFromClient = null;
		String responseString;
		
		// get address
		InetAddress clientAddress = null;
		// get port
		int clientPort = 0;
		
		// process UDP packets without infinite loop
		while (socket.isClosed() == false)
		{
			receivedRequest = new byte[16];
			packetFromClient = new DatagramPacket(receivedRequest, receivedRequest.length);
			
			// get request
			socket.receive(packetFromClient); 	// request is in -> receivedRequest
			
			clientAddress = packetFromClient.getAddress();
			clientPort = packetFromClient.getPort();
			
			responseString = new String(packetFromClient.getData());
			//responseString = responseString.replaceAll("\\s", "");	// remove extra spaces
			
			if (responseString.contains("W?"))
			{
				// send "UDPS"
				response = "UDPS".getBytes();
				packetToClient = new DatagramPacket(response, response.length, clientAddress, clientPort);
				socket.send(packetToClient);
			}
			else if (responseString.contains("BYE"))
			{
				// send BYE ACK
				response = "BYE ACK".getBytes();
				packetToClient = new DatagramPacket(response, response.length, clientAddress, clientPort);
				socket.send(packetToClient);
				try {
					this.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
			{
				// send ACK
				response = "ACK".getBytes();
				packetToClient = new DatagramPacket(response, response.length, clientAddress, clientPort);
				socket.send(packetToClient);
			}	
		}
		System.out.println("Exited Socket Loop - Server\nSocket Closed\n");
	}
	
	public int getBindPort()
	{
		return bindPort;
	}
	
	@Override
	public void close() throws Exception {
		socket.close();
	}
	
}
