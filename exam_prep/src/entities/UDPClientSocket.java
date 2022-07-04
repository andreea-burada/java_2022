package entities;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.sound.midi.Receiver;

//Subject+2 points <=> Mark 9/10:
//Create public class UDPClientSocket which implements a proprietary communication protocol and implements AutoCloseable (override specific method)
//It has 2 fields: socket - DatagramSocket
//It contains the following constructor methods:

//a. public UDPClientSocket() throws SocketException - init socket WITHOUT bind port

//b. public String sendAndReceiveMsg(String msg, String ipAddr, int port) throws UnknownHostException 
//- send UDP packets and process them (without infinite loop) with the following rules:
//		- when the sent request contains W? , then the response UDP packet from server contains as pay-load "UDPS".
//		- when the sent request contains BYE , then the response UDP packet from server contains as pay-load "BYE ACK" 
//		- when the sent request contains any other pay-load , then the response UDP packet contains as pay-load "ACK".

public class UDPClientSocket implements AutoCloseable {
	// attributes
	DatagramSocket socket;
	
	public UDPClientSocket() throws SocketException
	{
		socket = new DatagramSocket();
	}
	
	public String sendAndReceiveMsg(String msg, String ipAddr, int port) throws UnknownHostException
	{
		String response = null;
		InetAddress serverAddress = InetAddress.getByName(ipAddr);
		
		byte[] responseBuffer = new byte[16];
		byte[] messageBuffer = msg.getBytes();
		
		try {
			DatagramPacket toSend = new DatagramPacket(messageBuffer, messageBuffer.length, serverAddress, port);
			socket.send(toSend);
			
			DatagramPacket toReceive = new DatagramPacket(responseBuffer, responseBuffer.length);
			socket.receive(toReceive);
			response = new String (toReceive.getData());
			
		} catch (IOException e) {
			System.out.println("Error when sending to server\n\n\n");
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public void close() throws Exception {
		socket.close();
		
	}
	
}
