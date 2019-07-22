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
import data.Election;

public class GetInfoForTableAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	ArrayList<Election> electList = null;
	ArrayList<College> colleges = null;
	String colDep = "";
	String elString = "";
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		electList = func.getElectionsInfo();
		colleges = func.getAllCollegeInfo();
		
		if(colleges == null || electList == null) {
			return ERROR;
		}
		
		for(int i=0;i<colleges.size();i++) {
			colDep += "College: " + colleges.get(i).getName() + "<br> Departments: ";
			for(int j=0;j<colleges.get(i).getDepList().size();j++) {
				colDep += colleges.get(i).getDepList().get(j).getName() + "(" + colleges.get(i).getDepList().get(j).getId() + "), ";
			}
			colDep += "<br><br>";
		}
		
		for(int i=0;i<electList.size();i++) {
			elString += electList.get(i).getTitle() + ", ";
		}
		
		if(colDep.equals("")) {
			colDep = "No Colleges and Departments on the database";
		}
		
		if(elString.equals("")) {
			elString = "No elections on the database";
		}
		
		this.session.put("electList", electList);
		return SUCCESS;
	}

	public BusinessLogic getFunc() {
		return func;
	}

	public void setFunc(BusinessLogic func) {
		this.func = func;
	}

	public ArrayList<Election> getElectList() {
		return electList;
	}

	public void setElectList(ArrayList<Election> electList) {
		this.electList = electList;
	}

	public ArrayList<College> getColleges() {
		return colleges;
	}

	public void setColleges(ArrayList<College> colleges) {
		this.colleges = colleges;
	}

	public String getColDep() {
		return colDep;
	}

	public void setColDep(String colDep) {
		this.colDep = colDep;
	}

	public String getElString() {
		return elString;
	}

	public void setElString(String elString) {
		this.elString = elString;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
