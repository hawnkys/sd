package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.List;

public class CreateListAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	String name, type, listUsers;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		List l = new List();
		l.setName(name);
		l.setType(type);
		
		String[] parts = listUsers.split(",");
		ArrayList<String> users = new ArrayList<>();
		for(int i=0;i<parts.length;i++) {
			users.add(parts[i]);
		}
		l.setMembers(users);
		
		if(func.createList(l)) {
			return SUCCESS;
		}
		else {
			return ERROR;
		}
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

	public String getListUsers() {
		return listUsers;
	}

	public void setListUsers(String listUsers) {
		this.listUsers = listUsers;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
