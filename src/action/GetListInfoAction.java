package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import data.List;

public class GetListInfoAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private String name, members = "";
	private List li = null;
	
	@SuppressWarnings("unchecked")
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		ArrayList<List> lists = (ArrayList<List>) this.session.get("lists");
		
		for(int i=0;i<lists.size();i++) {
			if(lists.get(i).getName().equals(name)) {
				li = lists.get(i);
			}
		}
		if(li != null) {
			for(int i=0;i<li.getMembers().size();i++) {
				members += li.getMembers().get(i) + ", ";
			}
			
			if(members.equals("")) {
				members = "No Members On This List";
			}
			this.session.remove("lists");
			this.session.put("li", li);
			return SUCCESS;
		}
		else {
			return ERROR;
		}
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public List getLi() {
		return li;
	}

	public void setLi(List li) {
		this.li = li;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
