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
import data.Department;

public class EditCollegeAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private String name, initials, listDepartments;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		College col = (College) this.session.get("newCol");
		
		if(!name.equals("")) {
			col.setName(name);
		}
		
		if(!initials.equals("")) {
			col.setInitials(initials);
		}
		
		ArrayList<Department> oldDeps = col.getDepList();
		ArrayList<Department> newDeps = new ArrayList<>();
		if(!listDepartments.equals("")) {
			String [] parts = listDepartments.split(",");
			
			for(int i = 0;i < parts.length;i++) {
				newDeps.add(new Department(parts[i]));
			}
			
			col.setDepList(newDeps);
		}
		
		if(func.editCollege(col, oldDeps)) {
			this.session.remove("newCol");
			return SUCCESS;
		}
		else {
			this.session.remove("newCol");
			return ERROR;
		}
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

	public String getListDepartments() {
		return listDepartments;
	}

	public void setListDepartments(String listDepartments) {
		this.listDepartments = listDepartments;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
