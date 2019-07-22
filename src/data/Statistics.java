package data;

import java.io.Serializable;
import java.util.Map;

public class Statistics implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int totalVotes;
	Map<String, Integer> map;
	
	public Statistics() {
		super();
	}

	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
}
