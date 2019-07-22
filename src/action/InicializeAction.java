package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;

public class InicializeAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		func = new BusinessLogic();
		
		if(func == null) {
			return ERROR;
		}
		else {
			this.session.put("func", func);
			return SUCCESS;
		}
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
