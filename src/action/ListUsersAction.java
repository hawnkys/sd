package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.Person;

public class ListUsersAction extends ActionSupport implements SessionAware {
private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	ArrayList<Person> users = null;
	String names = "";
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		if(func == null) {
			return ERROR;
		}
		else {
			users = func.getUsersAllInformation();
			
			if(users != null) {
				for(int i=0;i<users.size();i++) {
					names += users.get(i).getUsername() + " (" + users.get(i).getJob() + "), ";
				}
				return SUCCESS;
			}
			
			return ERROR;
		}
	}
	
	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public ArrayList<Person> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Person> users) {
		this.users = users;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
