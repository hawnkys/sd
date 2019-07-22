package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;

public class GetTableStatusAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	String res = "";
	
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		res = func.getTablesStatus(1);
		
		if(res.equals("")) {
			res = "No Tables on the database";
		}
		
		return SUCCESS;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
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
		this.session=session;
	}
}
