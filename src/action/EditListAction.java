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

public class EditListAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private String name, listUsers;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		List li = (List) this.session.get("li");
		
		if(!name.equals("")) {
			li.setName(name);
		}
		
		ArrayList<String> oldLists = li.getMembers();
		if(!listUsers.equals("")) {
			String[] parts = listUsers.split(",");
			ArrayList<String> aux = new ArrayList<>();
			
			for(int i=0;i<parts.length;i++) {
				aux.add(parts[i]);
			}
			
			li.setMembers(aux);
		}
		
		if(func.editList(li, oldLists)) {
			this.session.remove("li");
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
