package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.College;

public class DeleteCollegeAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private String name;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		@SuppressWarnings("unchecked")
		ArrayList<College> colList = (ArrayList<College>) this.session.get("colleges");
		
		College aux = null;
		for(int i=0;i<colList.size();i++) {
			if(colList.get(i).getName().equals(name)) {
				aux = colList.get(i);
			}
		}
		
		if(aux != null) {
			if(func.removeCollege(aux.getId())) {
				return SUCCESS;
			}
			else {
				return ERROR;
			}
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

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
