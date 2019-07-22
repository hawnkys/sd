package action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import business.BusinessLogic;
import data.Election;
import data.Statistics;

public class GetStatisticsAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private BusinessLogic func;
	private String title;
	private Election e = null;
	private String results = "";
	
	@SuppressWarnings("unchecked")
	public String execute() throws RemoteException, MalformedURLException, NotBoundException {
		this.func = (BusinessLogic) this.session.get("func");
		ArrayList<Election> pastElections = (ArrayList<Election>) this.session.get("pastElections");
		
		for(int i=0;i<pastElections.size();i++) {
			if(pastElections.get(i).getTitle().equals(title)) {
				e = pastElections.get(i);
			}
		}
		
		if(e != null) {
			Statistics stats = func.getStatistics(e);
			
			if(stats.getTotalVotes() == 0) {
				results = "There is no votes for this election";
				return SUCCESS;
			}
			
			results += "Total votes = " + stats.getTotalVotes() + " <br>";
			Map<String, Integer> map = stats.getMap();
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String list = entry.getKey();
				int votes = entry.getValue();


				float percentageVotes = ((float)votes / stats.getTotalVotes()) * 100;

				DecimalFormat df = new DecimalFormat();
				df.setMinimumFractionDigits(2);
				df.setMaximumFractionDigits(2);
				results += "List = " + list + " | Total Votes = " + votes + " | Percentage = " + df.format(percentageVotes) +" %" + " <br>";
			}
			
			return SUCCESS;
		}
		else {
			return ERROR;
		}
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public BusinessLogic getFunc() {
		return func;
	}

	public void setFunc(BusinessLogic func) {
		this.func = func;
	}

	public Election getE() {
		return e;
	}

	public void setE(Election e) {
		this.e = e;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
