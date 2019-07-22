package rmi_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Thread_SendUDP implements Runnable {
	Thread t;
	int port;
	int loss_packages;
	
	public Thread_SendUDP(int port) {
		this.port = port;
		this.loss_packages = 0;
		
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		sendAndRecive(port);
	}
	
	public void sendAndRecive(int port) {
		DatagramSocket aSocket = null;
		String msg = "Tas?";
		
		try {
			aSocket = new DatagramSocket();    

			while(true){
				//send
//				System.out.println("Sendind: " + msg);
				byte [] m = msg.getBytes();
				InetAddress aHost = InetAddress.getByName("localhost");
				DatagramPacket request = new DatagramPacket(m, m.length, aHost,port);
				aSocket.send(request);			
				
				try {
					aSocket.setSoTimeout(1000);
					
					//receive
					byte[] buffer = new byte[1000];
					DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
					aSocket.receive(reply);
//					System.out.println("Recebeu: " + new String(reply.getData(), 0, reply.getLength()));
					loss_packages = 0;
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				catch (IOException e) {
					System.out.println("Timeout");
					loss_packages++;
					if(loss_packages == 5) {
						t.interrupt();
						break;
					}
				}
			}
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
	}

	public int getLoss_packages() {
		return loss_packages;
	}
}
