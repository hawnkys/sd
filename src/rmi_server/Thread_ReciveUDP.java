package rmi_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Thread_ReciveUDP implements Runnable {
	Thread t;
	int port;
	
	public Thread_ReciveUDP(int port) {
		this.port = port;
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		reciveAndSend(port);
	}
	
	public void reciveAndSend(int port) {
		DatagramSocket aSocket = null;
		//String s = "";
		
		try{
			aSocket = new DatagramSocket(port);
			
			while(true){
				byte[] buffer = new byte[1000]; 			
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				//s = new String(request.getData(), 0, request.getLength());	
				//System.out.println("Server Recebeu: " + s);	

				
				DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
				aSocket.send(reply);
				
			}
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
	}
}
