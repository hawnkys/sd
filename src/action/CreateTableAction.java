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
import data.Table;

public class CreateTableAction extends ActionSupport implements SessionAware {
private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	String table_ip, dep_id, electionsList;
	
	@SuppressWarnings("unchecked")
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		
		System.out.println(table_ip + ", " + dep_id + ", " + electionsList);
		
		Table table = new Table();
		table.setServerIP(table_ip);
		table.setDepartmentID(Integer.parseInt(dep_id));
		
		ArrayList<Election> tableElections = new ArrayList<>();
		ArrayList<Election> electList = (ArrayList<Election>) this.session.get("electList");
		String[] parts = electionsList.split(",");
		
		for(int i=0;i<parts.length;i++) {
			for(int j=0;j<electList.size();j++) {
				if(parts[i].equals(electList.get(j).getTitle())) {
					tableElections.add(electList.get(i));
				}
			}
		}
		
		table.setElectionList(tableElections);
		
		if(func.createTable(table)) {
			this.session.remove("electList");
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

	public String getTable_ip() {
		return table_ip;
	}

	public void setTable_ip(String table_ip) {
		this.table_ip = table_ip;
	}

	public String getDep_id() {
		return dep_id;
	}

	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}

	public String getElectionsList() {
		return electionsList;
	}

	public void setElectionsList(String electionsList) {
		this.electionsList = electionsList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
