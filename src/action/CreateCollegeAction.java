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

public class CreateCollegeAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	String name, initials, listDepartments;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		College col = new College();
		col.setName(name);
		col.setInitials(initials);
		
		ArrayList<Department> depList = new ArrayList<>();
		String [] parts = listDepartments.split(",");
		
		for(int i = 0;i < parts.length;i++) {
			depList.add(new Department(parts[i]));
		}
		
		col.setDepList(depList);
		
		if(func.createCollege(col)) {
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
