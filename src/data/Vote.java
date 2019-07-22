package data;

import java.io.Serializable;
import java.util.Date;

public class Vote implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String username;
	String college;
	String department;
	String election;
	String list;
	Date date;
	
	public Vote() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getElection() {
		return election;
	}

	public void setElection(String election) {
		this.election = election;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
