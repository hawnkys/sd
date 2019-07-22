package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;

public class DeleteTableAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
	private BusinessLogic func;
	String table_id;

	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		if(func.deleteTable(Integer.parseInt(table_id))) {
			return SUCCESS;
		}
		else {
			return ERROR;
		}
	}

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
