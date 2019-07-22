package data;

import java.io.Serializable;
import java.util.ArrayList;

public class College implements Serializable{
	private static final long serialVersionUID = 1L;
	
	int id;
	String name;
	String initials;
	ArrayList<Department> depList;
	
	public College() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public ArrayList<Department> getDepList() {
		return depList;
	}

	public void setDepList(ArrayList<Department> depList) {
		this.depList = depList;
	}
}
