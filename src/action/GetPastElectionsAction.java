package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.Election;

public class GetPastElectionsAction  extends ActionSupport implements SessionAware  {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private ArrayList<Election> pastElections = null;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		pastElections = func.getFinishedElections();
		
		if(pastElections != null) {
			this.session.put("pastElections", pastElections);
			return SUCCESS;
		}
		else {
			return ERROR;
		}
	}

	public BusinessLogic getFunc() {
		return func;
	}

	public void setFunc(BusinessLogic func) {
		this.func = func;
	}

	public ArrayList<Election> getPastElections() {
		return pastElections;
	}

	public void setPastElections(ArrayList<Election> pastElections) {
		this.pastElections = pastElections;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
