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

public class ColDepInfoAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private ArrayList<College> colleges = null;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		if(func == null) {
			return ERROR;
		}
		else {
			colleges = func.getAllCollegeInfo();
			
			if(colleges != null) {
				return SUCCESS;
			}
			
			return ERROR;
		}
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	public ArrayList<College> getColleges() {
		return colleges;
	}

	public void setColleges(ArrayList<College> colleges) {
		this.colleges = colleges;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
