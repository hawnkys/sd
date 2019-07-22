package rmi_server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import data.College;
import data.Department;
import data.Election;
import data.List;
import data.Person;
import data.Statistics;
import data.Table;
import data.Vote;

public interface RMI_Interface extends Remote {
	public boolean registePerson(Person p) throws RemoteException;
	public boolean createElection(Election e) throws RemoteException;
	public boolean createCollege(College college) throws RemoteException;
	public boolean editCollege(College col, ArrayList<Department> old_dep) throws RemoteException;
	public boolean createList(List list) throws RemoteException;
	public boolean removeCollege(int id) throws RemoteException;
	public boolean editList(List l, ArrayList<String> oldList) throws RemoteException;
	public boolean editElection(Election e, ArrayList<String> oldList) throws RemoteException;
	public boolean createTable(Table t) throws RemoteException;
	public boolean changeTableStatus(int id) throws RemoteException;
	public boolean deleteTable(int id) throws RemoteException;
	public boolean login(String username, String password) throws RemoteException;
	public boolean vote(String username, int serverPort, int e_id, String l_name) throws RemoteException;
	public boolean editPerson(Person p, String oldUsername) throws RemoteException;
	public boolean deleteList(String name) throws RemoteException;
	
	public ArrayList<Person> getUsers() throws RemoteException;
	public ArrayList<List> getListsInfo() throws RemoteException;
	public ArrayList<Election> getElectionsInfo() throws RemoteException;
	public ArrayList<College>getAllCollegeInfo() throws RemoteException;
	public ArrayList<Table> getTableInfo() throws RemoteException;
	public String getDepartmentName(int id) throws RemoteException;
	public ArrayList<Election> getElectionsOfTable(int server_port) throws RemoteException;
	public ArrayList<Vote> getVotes(String username) throws RemoteException;
	public String getTablesStatus(int flag) throws RemoteException;
	public ArrayList<Election> getFinishedElections() throws RemoteException;
	public Statistics getStatistics(Election e) throws RemoteException;
	public ArrayList<Person> getUsersAllInformation() throws RemoteException;
	public Person getUserInfo(String username) throws RemoteException;
	public boolean deletePerson(String username) throws RemoteException;
}