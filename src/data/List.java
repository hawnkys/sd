package data;

import java.io.Serializable;
import java.util.ArrayList;

public class List implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int id;
	String name;
	String type;
	ArrayList<String> members;
	
	public List() {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<String> members) {
		this.members = members;
	}
}
