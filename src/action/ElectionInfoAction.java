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
import data.List;

public class ElectionInfoAction  extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	Election e = null;
	String title;
	String electionLists ="";
	private ArrayList<List> lists = null;
	private String listString = "";
	
	@SuppressWarnings("unchecked")
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		ArrayList<Election> elections = (ArrayList<Election>) this.session.get("elections");
		
		for(int i=0;i<elections.size();i++) {
			if(elections.get(i).getTitle().equals(title)) {
				e = elections.get(i);
			}
		}
		
		if(e != null) {
			this.session.remove("elections");
			this.session.put("e", e);
			for(int i = 0;i<e.getLists().size();i++) {
				electionLists += e.getLists().get(i) + ", ";
			}
			
			lists = func.getListsInfo();
			
			if(lists != null) {
				if(lists.size() > 0) {
					for(int i=0;i<lists.size();i++) {
						listString += lists.get(i).getName() + ", ";
					}
				}
				else {
					listString += "No lists on the database";
				}
				
				return SUCCESS;
			}
			
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

	public ArrayList<List> getLists() {
		return lists;
	}

	public void setLists(ArrayList<List> lists) {
		this.lists = lists;
	}

	public String getListString() {
		return listString;
	}

	public void setListString(String listString) {
		this.listString = listString;
	}

	public Election getE() {
		return e;
	}

	public void setE(Election e) {
		this.e = e;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getElectionLists() {
		return electionLists;
	}

	public void setElectionLists(String electionLists) {
		this.electionLists = electionLists;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
