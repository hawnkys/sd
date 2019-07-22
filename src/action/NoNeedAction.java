package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


public class NoNeedAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		return SUCCESS;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
