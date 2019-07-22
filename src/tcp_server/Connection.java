package tcp_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import data.Election;
import rmi_server.RMI_Interface;

class Connection extends Thread {
    BufferedReader in;
    PrintWriter out;
    RMI_Interface func;
    Socket clientSocket;
    int thread_number;
    int serverPort;
    boolean logged;
    
    public Connection (Socket aClientSocket, int numero, RMI_Interface func, int serverPort) {
        thread_number = numero;
        this.func=func;
        this.serverPort = serverPort;
        this.logged = false;
        
        try{
            clientSocket = aClientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.start();
        }catch(IOException e) {
            System.out.println("Connection: " + e.getMessage());
        }
    }

    @Override
    public void run(){
    	manager(in, out, func);
    }
    
    //type | login ; username | ruben ; password | password
	public void manager(BufferedReader in, PrintWriter out, RMI_Interface func) {
		String protocol;
		
		try {
			out.println("Login pls: ");
			protocol = in.readLine();
			
			if(protocol.equals("teste")) {
				clientSocket.close();
				return;
			}
			
			String[] parts = splitUsingTokenizer(protocol, " |;");
			
			for(int i=0;i<parts.length;i++) {
				System.out.println("Parts["+i+"] = " + parts[i]);
			}
			System.out.println(protocol);
			
			switch(parts[1]) {
				case "login":
					login(parts, in,out,func);
					break;
	
				default:
					out.println("Insert a valid protocol !!!");
					manager(in, out, func);
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void login(String[] parts, BufferedReader in, PrintWriter out, RMI_Interface func) throws IOException {
		if(logged) {
			out.println("You are alrady logged");
			voteMenu(parts, in, out, func);
		}
		else {
			if(func.login(parts[3], parts[5])) {
				out.println("Login Successful");
				logged = true;
				voteMenu(parts, in, out, func);
			}
			else {
				out.println("Wrong Username/password !!!");
			}
		}
		
		manager(in, out, func);
	}
	
	public void voteMenu(String[] parts, BufferedReader in, PrintWriter out, RMI_Interface func) throws NumberFormatException, IOException {
		out.println("--- Vote ---\n");
		ArrayList<Election> e_list = func.getElectionsOfTable(serverPort);
		
		printElections(e_list);

		out.println("Insert election id: ");
		int e_id = Integer.parseInt(in.readLine());

		out.println("Insert list name: (Insert 0 for null vote)");
		String l_name = in.readLine();
		
		if(func.vote(parts[3], serverPort, e_id, l_name)) {
			out.println("Vote registed successfully");
			logged = false;
			manager(in, out, func);
		}
		else {
			out.println("You have already voted in this election !!!");
			logged = false;
			manager(in, out, func);
		}

	}
	
	public void printElections(ArrayList<Election> listOfElections) {
		String res = "";
		
		for(int i=0;i<listOfElections.size();i++) {
			res += "ID = " + listOfElections.get(i).getId() + " | Title = " + listOfElections.get(i).getTitle() +" | Description = " + listOfElections.get(i).getDescription();
			for(int j=0;j<listOfElections.get(i).getLists().size();j++) {
				if(j==0) {
					res += " | lists: " + listOfElections.get(i).getLists().get(j) + ", ";
				}
				else {
					res += listOfElections.get(i).getLists().get(j) + ", ";
				}
			}
			res += "\n";
		}
		
		out.println(res);
	}
	
	public static String[] splitUsingTokenizer(String Subject, String Delimiters) 
	{
		StringTokenizer StrTkn = new StringTokenizer(Subject, Delimiters);
		ArrayList<String> ArrLis = new ArrayList<String>(Subject.length());

		while(StrTkn.hasMoreTokens())
		{
			ArrLis.add(StrTkn.nextToken());
		}
		return ArrLis.toArray(new String[0]);
	}
}

