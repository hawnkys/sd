package business;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Properties;

import data.College;
import data.Department;
import data.Election;
import data.List;
import data.Person;
import data.Statistics;
import data.Table;
import data.Vote;
import rmi_server.RMI_Interface;

public class BusinessLogic {
	static RMI_Interface func;
	static int tries;
	
	public BusinessLogic() throws MalformedURLException, RemoteException, NotBoundException {
		tries = 0;
		connectToRMIServer();
	}
	
	public ArrayList<College> getAllCollegeInfo() throws RemoteException {
		return func.getAllCollegeInfo();
	}
	
	public ArrayList<Person> getUsersAllInformation() throws RemoteException {
		return func.getUsersAllInformation();
	}
	
	public Person getUserInfo(String username) throws RemoteException {
		return func.getUserInfo(username);
	}
	
	public boolean registePerson(Person p) throws RemoteException {
		return func.registePerson(p);
	}
	
	public boolean editPerson(Person p, String oldUsername) throws RemoteException {
		return func.editPerson(p, oldUsername);
	}
	
	public boolean deletePerson(String username) throws RemoteException {
		return func.deletePerson(username);
	}
	
	public ArrayList<Vote> getVotes(String username) throws RemoteException {
		return func.getVotes(username);
	}
	
	public boolean createCollege(College college) throws RemoteException{
		return func.createCollege(college);
	}
	
	public boolean editCollege(College col, ArrayList<Department> old_dep) throws RemoteException {
		return func.editCollege(col, old_dep);
	}
	
	public boolean removeCollege(int id) throws RemoteException {
		return func.removeCollege(id);
	}
	
	public ArrayList<List> getListsInfo() throws RemoteException {
		return func.getListsInfo();
	}
	
	public boolean createElection(Election e) throws RemoteException {
		return func.createElection(e);
	}
	
	public boolean createList(List list) throws RemoteException {
		return func.createList(list);
	}
	
	public boolean editList(List l, ArrayList<String> oldList) throws RemoteException {
		return func.editList(l, oldList);
	}
	
	public boolean deleteList(String name) throws RemoteException{
		return func.deleteList(name);
	}
	
	public ArrayList<Election> getElectionsInfo() throws RemoteException {
		return func.getElectionsInfo();
	}
	
	public boolean editElection(Election e, ArrayList<String> oldList) throws RemoteException {
		return func.editElection(e, oldList);
	}
	
	public ArrayList<Election> getFinishedElections() throws RemoteException {
		return func.getFinishedElections();
	}
	
	public Statistics getStatistics(Election e) throws RemoteException {
		return func.getStatistics(e);
	}
	
	public boolean createTable(Table t) throws RemoteException {
		return func.createTable(t);
	}
	
	public ArrayList<Table> getTableInfo() throws RemoteException {
		return func.getTableInfo();
	}
	
	public boolean deleteTable(int id) throws RemoteException {
		return func.deleteTable(id);
	}
	
	public String getTablesStatus(int flag) throws RemoteException {
		return func.getTablesStatus(flag);
	}
	
	public static void connectToRMIServer() {
		Properties properties = new Properties();
		
		System.out.println("Getting RMI ports ...");
        InputStream configs = BusinessLogic.class.getResourceAsStream("/resources/configRMI.properties");
		
		try {
			String rmi_port = null, rmi_port2 = null;
		    
			properties.load(configs);
			
			rmi_port = properties.getProperty("RMI_PORT");
			rmi_port2 = properties.getProperty("RMI_PORT2");

			System.out.println("RMI ports getted successfully");
			connect(rmi_port, rmi_port2);
		} catch (IOException e) {
			System.out.println("Failed to get the rmi ports !!!");
		}
	}
	
	public static void connect(String rmi_port, String rmi_port2) {
		tries++;
		if(tries == 10) {
			func = null;
			return;
		}
		System.out.println("Trying to connect to rmi port: " + rmi_port);
		try {
			func = (RMI_Interface) LocateRegistry.getRegistry(Integer.parseInt(rmi_port)).lookup("rmi");
			System.out.println("Connected to port: " + rmi_port);
		} catch (AccessException e) {
			System.out.println("Failed to connect to port: " + rmi_port + "  (AccessException)");
			connect(rmi_port2, rmi_port);
		} catch (RemoteException e) {
			System.out.println("Failed to connect to port: " + rmi_port + "  (RemoteException)");
			connect(rmi_port2, rmi_port);
		} catch (NotBoundException e) {
			System.out.println("Failed to connect to port: " + rmi_port + "  (NotBoundException)");
			connect(rmi_port2, rmi_port);
		}
	}
}
