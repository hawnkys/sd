package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.Election;

public class CreateElectionAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private String title, description, type, start_date, start_time, end_date, end_time, lists;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		System.out.println("t = " + title);
		System.out.println(description);
		System.out.println(type);
		System.out.println(start_date);
		System.out.println(start_time);
		System.out.println(end_date);
		System.out.println(end_time);
		
		Election election = new Election();
		election.setTitle(title);
		election.setDescription(description);
		if(type.equals("Students Core")) {
			election.setType(0);
		}
		else {
			election.setType(1);
		}
		
		String[] parts_date = start_date.split("-");
		String[] parts_time = start_time.split(":");
		Calendar calendar = new GregorianCalendar(Integer.parseInt(parts_date[0]), Integer.parseInt(parts_date[1]), Integer.parseInt(parts_date[2]), Integer.parseInt(parts_time[0]), Integer.parseInt(parts_time[1]));
		Date date = calendar.getTime();
		election.setBegin(date);
			
		String[] parts_date2 = end_date.split("-");
		String[] parts_time2 = end_time.split(":");
		Calendar cal = new GregorianCalendar(Integer.parseInt(parts_date2[0]), Integer.parseInt(parts_date2[1]), Integer.parseInt(parts_date2[2]), Integer.parseInt(parts_time2[0]), Integer.parseInt(parts_time2[1]));
		Date date2 = cal.getTime();
		election.setEnd(date2);
		
		election.setLists(new ArrayList<String>());
		
		String[] listParts = lists.split(",");
		ArrayList<String> finalLists = new ArrayList<>();
		for(int i=0;i<listParts.length;i++) {
			finalLists.add(listParts[i]);
		}
		
		election.setLists(finalLists);
		
		if(func.createElection(election)) {
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

	public String getLists() {
		return lists;
	}

	public void setLists(String lists) {
		this.lists = lists;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
