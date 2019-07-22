package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import data.College;

public class SingleColInfoAction extends ActionSupport implements SessionAware {
private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	String initials;
	String oldDeps = "";
	College col = null;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		@SuppressWarnings("unchecked")
		ArrayList<College> colList = (ArrayList<College> ) this.session.get("colleges");
		this.session.remove("colleges");
		
		
		for(int i = 0;i<colList.size();i++) {
			if(colList.get(i).getInitials().equals(initials)) {
				col = colList.get(i);
			}
		}
		
		for(int i = 0;i < col.getDepList().size();i++) {
			oldDeps += col.getDepList().get(i).getName() + ",";
		}
		
		if(col == null) {
			return ERROR;
		}
		else {
			this.session.put("newCol", col);
			return SUCCESS;
		}
	}

	public String getOldDeps() {
		return oldDeps;
	}

	public void setOldDeps(String oldDeps) {
		this.oldDeps = oldDeps;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public College getCol() {
		return col;
	}

	public void setCol(College col) {
		this.col = col;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
