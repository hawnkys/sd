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

public class EditElectionAction extends ActionSupport implements SessionAware {
private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	String title, description, start_date, start_time, end_date, end_time, lists;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		Election e = (Election) this.session.get("e");
		
		if(!title.equals("")) {
			e.setTitle(title);
		}
		
		if(!description.equals("")) {
			e.setDescription(description);
		}
		
		if(!start_date.equals("")) {
			String[] parts_date = start_date.split("-");
			String[] parts_time = start_time.split(":");
			Calendar calendar = new GregorianCalendar(Integer.parseInt(parts_date[0]), Integer.parseInt(parts_date[1]), Integer.parseInt(parts_date[2]), Integer.parseInt(parts_time[0]), Integer.parseInt(parts_time[1]));
			Date date = calendar.getTime();
			e.setBegin(date);
		}
		
		if(!end_date.equals("")) {
			String[] parts_date2 = end_date.split("-");
			String[] parts_time2 = end_time.split(":");
			Calendar cal = new GregorianCalendar(Integer.parseInt(parts_date2[0]), Integer.parseInt(parts_date2[1]), Integer.parseInt(parts_date2[2]), Integer.parseInt(parts_time2[0]), Integer.parseInt(parts_time2[1]));
			Date date2 = cal.getTime();
			e.setEnd(date2);
		}
		
		ArrayList<String> oldLists = e.getLists();
		if(!lists.equals("")) {
			String[] listParts = lists.split(",");
			ArrayList<String> finalLists = new ArrayList<>();
			for(int i=0;i<listParts.length;i++) {
				finalLists.add(listParts[i]);
			}
			
			e.setLists(finalLists);
		}
		
		if(func.editElection(e, oldLists)) {
			this.session.remove("e");
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

	public String getLists() {
		return lists;
	}

	public void setLists(String lists) {
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
