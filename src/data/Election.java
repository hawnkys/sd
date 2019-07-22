package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Election implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int id;
	int type;
	int active;
	String title;
	String description;
	Date begin;
	Date end;
	ArrayList<String> lists;
	
	public Election() {
		super();
	}
	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ArrayList<String> getLists() {
		return lists;
	}

	public void setLists(ArrayList<String> lists) {
		this.lists = lists;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
