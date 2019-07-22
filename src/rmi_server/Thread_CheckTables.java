package rmi_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Thread_CheckTables implements Runnable {
	Thread t;
	Connection connect;
	ArrayList<String> table = new ArrayList<>();
	
	public Thread_CheckTables(Connection connect) {
		this.connect = connect;
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		while(true) {
			
			table = getIPs(connect);
			
			if(table == null) {
				System.out.println("[Thread_CheckTables] can't get tables !!!");
			}
			else {
				checkTables(getIPs(connect));
			}
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void checkTables(ArrayList<String> ip_list) {
		for(int i=0;i<ip_list.size();i++) {
			String[] parts = ip_list.get(i).split(" ");
			
			if(connectionTest(parts[0], Integer.parseInt(parts[1]))) {
				setTableStatus(ip_list.get(i), 1);
			}
			else {
				setTableStatus(ip_list.get(i), 0);
			}
		}
	}
	
	public boolean connectionTest(String server_ip, int server_port) {
		try {
			Socket s = new Socket(server_ip, server_port);
			
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter outToServer = new PrintWriter(s.getOutputStream(), true);
			
			inFromServer.readLine();
			outToServer.println("teste");
			
			s.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void setTableStatus(String server_ip, int stats) {
		try {
			PreparedStatement preparedStatement = connect.prepareStatement("UPDATE `table` SET active=" + stats + " WHERE server_ip='" + server_ip + "'");
			preparedStatement.executeUpdate();
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getIPs(Connection connect) {
		ArrayList<String> ip_list = new ArrayList<>();
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT server_ip FROM `table`");
			
			while(rs.next()) {
				ip_list.add(rs.getString("server_ip"));
			}
			
			return ip_list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
