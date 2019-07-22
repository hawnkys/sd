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

public class ListCollegersAction  extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	ArrayList<College> colList = null;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		colList = func.getAllCollegeInfo();
		this.session.put("colleges", colList);
		
		if(colList == null) {
			return ERROR;
		}
		else {
			return SUCCESS;
		}
	}
	
	public ArrayList<College> getColList() {
		return colList;
	}

	public void setColList(ArrayList<College> colList) {
		this.colList = colList;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
