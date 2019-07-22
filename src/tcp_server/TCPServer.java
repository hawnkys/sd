package tcp_server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import rmi_server.RMI_Interface;
import rmi_server.Server_RMI;

public class TCPServer {
	static RMI_Interface h;
    
	@SuppressWarnings("resource")
	public static void main(String[] args) {
        int numero = 0;
        int serverPort = 80;
        String[] rmiPorts = new String[2];

        rmiPorts = loadProperties();
        if(rmiPorts == null) {
        	return;
        }
        
        startConnectionToRMI(rmiPorts[0], rmiPorts[1]);
        
        try{
            System.out.println("A Escuta no Porto " + serverPort);
			ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("LISTEN SOCKET = " + listenSocket);
                  
            while(true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept()) = " + clientSocket);
                numero ++;

                new Connection(clientSocket, numero, h, serverPort);
            }
        }catch(IOException e) {
            System.out.println("Listen: " + e.getMessage());
        }
    }
	
	public static String[] loadProperties() {
		Properties properties = new Properties();
		String[] rmiPorts = new String[2];
		
		System.out.println("Loading configuration file ...");
        InputStream configs = Server_RMI.class.getResourceAsStream("/resources/configRMI.properties");
		try { // try to get data from configuration file
			properties.load(configs);
			
			rmiPorts[0] = properties.getProperty("RMI_PORT");
			rmiPorts[1] = properties.getProperty("RMI_PORT2");
			
		    System.out.println("Configuration file loaded sucessfully");
		    return rmiPorts;
		} catch (IOException e) {
			System.out.println("Failed to load data from configurations file !!!");
		}
		return null;
	}
	
	public static void startConnectionToRMI(String rmi_port, String rmi_port2) {
		System.out.println("Trying to connect to rmi port: " + rmi_port);
		try {
			h = (RMI_Interface) LocateRegistry.getRegistry(Integer.parseInt(rmi_port)).lookup("rmi");
			System.out.println("Connected to port: " + rmi_port);
		} catch (AccessException e) {
			System.out.println("Failed to connect to port: " + rmi_port + "  (AccessException)");
			startConnectionToRMI(rmi_port2, rmi_port);
		} catch (RemoteException e) {
			System.out.println("Failed to connect to port: " + rmi_port + "  (RemoteException)");
			startConnectionToRMI(rmi_port2, rmi_port);
		} catch (NotBoundException e) {
			System.out.println("Failed to connect to port: " + rmi_port + "  (NotBoundException)");
			startConnectionToRMI(rmi_port2, rmi_port);
		}
	}
}

   