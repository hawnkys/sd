package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.List;

public class ListsInfoAction  extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private ArrayList<List> lists = null;
	private String listString = "";
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		if(func == null) {
			return ERROR;
		}
		else {
			lists = func.getListsInfo();
			this.session.put("lists", lists);
			
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
			
			return ERROR;
		}
	}

	public String getListString() {
		return listString;
	}

	public void setListString(String listString) {
		this.listString = listString;
	}

	public ArrayList<List> getLists() {
		return lists;
	}

	public void setLists(ArrayList<List> lists) {
		this.lists = lists;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
