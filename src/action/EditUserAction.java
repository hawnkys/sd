package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.Person;

public class EditUserAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private String username, pw, job, college, department, addr, phone, cc_number, date;
	private BusinessLogic func;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		Person user = (Person) this.session.get("user");
		this.session.remove("user");
		
		
		System.out.println("Username = " + username);
		System.out.println("PW = " + pw);
		System.out.println("Job = " + job);
		System.out.println("College = " + college);
		System.out.println("Dep = " + department);
		System.out.println("Addr = " + addr);
		System.out.println("phone = " + phone);
		System.out.println("cc_number = " + cc_number);
		System.out.println("Date = " + date.toString());
		
		String oldUsername = user.getUsername();
		if(!username.equals("")) {
			user.setUsername(username);
		}
		
		if(!pw.equals("")) {
			user.setPw(pw);
		}
		
		if(!job.equals(user.getJob())) {
			user.setJob(job);
		}
		
		if(!college.equals(user.getCollege())) {
			user.setCollege(college);
		}
		
		if(!department.equals(user.getDepartment())) {
			user.setDepartment(department);
		}
		
		if(!addr.equals("")) {
			user.setAddress(addr);
		}
		
		if(!phone.equals("")) {
			user.setTelephone(Integer.parseInt(phone));
		}
		
		if(!cc_number.equals("")) {
			user.setCC_number(Integer.parseInt(cc_number));
		}
		
		if(!date.equals("")) {
			String [] parts = date.split("/");
			Calendar calendar = new GregorianCalendar(Integer.parseInt(parts[2]), Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1]));
			Date dates = calendar.getTime();
			user.setCC_date(dates);
		}
		
		
		if(func.editPerson(user, oldUsername)) {
			return SUCCESS;
		}
		else {
			return ERROR;
		}
	}
	
	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCc_number() {
		return cc_number;
	}

	public void setCc_number(String cc_number) {
		this.cc_number = cc_number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BusinessLogic getFunc() {
		return func;
	}

	public void setFunc(BusinessLogic func) {
		this.func = func;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
