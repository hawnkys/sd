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

public class GetElectionsAction extends ActionSupport implements SessionAware  {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private ArrayList<Election> elections = null;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		elections = func.getElectionsInfo();
		
		if(elections != null) {
			this.session.put("elections", elections);
			return SUCCESS;
		}
		else {
			return ERROR;
		}
	}

	public ArrayList<Election> getElections() {
		return elections;
	}

	public void setElections(ArrayList<Election> elections) {
		this.elections = elections;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
