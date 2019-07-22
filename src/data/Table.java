package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int id;
	int departmentID;
	int active;
	String serverIP;
	ArrayList<Election> electionList;
	
	public Table() {
		super();
	}

	public ArrayList<Election> getElectionList() {
		return electionList;
	}

	public void setElectionList(ArrayList<Election> electionList) {
		this.electionList = electionList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
