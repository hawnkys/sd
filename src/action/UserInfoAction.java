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
import data.Person;

public class UserInfoAction extends ActionSupport implements SessionAware {
private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private String username;
	Person user = null;
	private ArrayList<College> colleges = null;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		user = func.getUserInfo(username);
		colleges = func.getAllCollegeInfo();
		this.session.put("user", user);
		
		if(user == null || colleges == null) {
			return ERROR;
		}
		else {
			return SUCCESS;
		}
	}
	
	public ArrayList<College> getColleges() {
		return colleges;
	}

	public void setColleges(ArrayList<College> colleges) {
		this.colleges = colleges;
	}
	
	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
