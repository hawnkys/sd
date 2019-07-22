package rmi_server;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import data.College;
import data.Department;
import data.Election;
import data.List;
import data.Person;
import data.Statistics;
import data.Table;
import data.Vote;

public class Server_RMI_Backup extends UnicastRemoteObject implements RMI_Interface {
	private static final long serialVersionUID = 1L;
	public static Connection connect;
	public static RMI_Interface h;
	public static int COUNT = 0;
	public static final int MAX_TIMEOUT = 4000;
	
	protected Server_RMI_Backup() throws RemoteException {
		super();
	}
	
	public boolean deleteList(String name) throws RemoteException {
		System.out.println("--- Delete List ---");
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connect.prepareStatement("DELETE FROM list WHERE name='" + name + "'");
			preparedStatement.executeUpdate();
			connect.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePerson(String username) throws RemoteException {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connect.prepareStatement("DELETE FROM user WHERE username='" + username + "'");
			preparedStatement.executeUpdate();
			connect.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Person getUserInfo(String username) throws RemoteException {
		System.out.println("--- Get Person Info ---");
		Person p = new Person();
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE username='" + username + "'");
			
			if(rs.next()) {
				p.setUsername(rs.getString("username"));
				p.setJob(rs.getString("job"));
				p.setCollege(getCollegeName(rs.getInt("college_id")));
				p.setDepartment(getDepartmentName(rs.getInt("department_id")));
				p.setAddress(rs.getString("address"));
				p.setTelephone(rs.getInt("phone"));
				p.setCC_number(rs.getInt("cc_number"));
				p.setCC_date(rs.getDate("cc_date"));
				
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean editPerson(Person p, String oldUsername) throws RemoteException {
		System.out.println("--- Edit Person ---");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		try {
			Statement statement = connect.createStatement();
			Statement statement2 = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM college WHERE name='" + p.getCollege() + "'");
			ResultSet rs1 = statement2.executeQuery("SELECT id FROM department WHERE name='" + p.getDepartment() + "'");

			if(rs.next()) {
				if(rs1.next()) {
					PreparedStatement preparedStatement = connect.prepareStatement("UPDATE user SET username='" + p.getUsername() +"', job='" + p.getJob() + "'" +
							", college_id=" + rs.getInt("id") +  ", department_id=" + rs1.getInt("id") + ", address='" + p.getAddress() + "'" +
							", phone=" + p.getTelephone() + ", cc_number=" + p.getCC_number() + ", cc_date='" + sdf.format(p.getCC_date()) + "' WHERE username'=" + oldUsername + "'");
					preparedStatement.executeUpdate();
					connect.commit();
					return true;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Person> getUsersAllInformation() throws RemoteException {
		System.out.println("--- Get users all informations ---");
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT* FROM user");
			ArrayList<Person> pList = new ArrayList<>();
			
			while(rs.next()) {
				Person p = new Person();
				
				p.setId(rs.getInt("id"));
				p.setUsername(rs.getString("username"));
				p.setJob(rs.getString("job"));
				p.setCollege(getCollegeName(rs.getInt("college_id")));
				p.setDepartment(getDepartmentName(rs.getInt("department_id")));
				p.setAddress(rs.getString("address"));
				p.setTelephone(rs.getInt("phone"));
				p.setCC_number(rs.getInt("cc_number"));
				p.setCC_date(rs.getDate("cc_date"));
				
				pList.add(p);
			}
			connect.commit();
			return pList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Statistics getStatistics(Election e) throws RemoteException {
		System.out.println("--- Get Statistics ---");
		Map<String, Integer> map = new HashMap<String, Integer>();
		Statistics stats = new Statistics();
		
		stats.setTotalVotes(countElectionVotes(e.getId()));
		
		ArrayList<String> listNames = e.getLists();
		
		for(int i=0;i<listNames.size();i++) {
			int res = getListVoltes(e.getId(), getListID(listNames.get(i)));
			map.put(listNames.get(i), res);
		}
		
		stats.setMap(map);
		
		return stats;
	}
	
	public int getListVoltes(int e_id, int l_id) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(*) total FROM vote WHERE election_id=" + e_id + " AND list_id=" +l_id);
			if(rs.next()) {
				return rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int countElectionVotes(int e_id) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(*) total FROM vote WHERE election_id=" + e_id);
			if(rs.next()) {
				return rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<Election> getFinishedElections() throws RemoteException {
		System.out.println("--- Get Finished Elections ---");
		ArrayList<Election> list = new ArrayList<>();

		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT* FROM election WHERE finish=1");
			while(rs.next()) {
				Election aux = new Election();
				aux.setId(rs.getInt("id"));
				aux.setTitle(rs.getString("title"));
				aux.setDescription(rs.getString("description"));
				aux.setBegin(rs.getTimestamp("date_begin"));
				aux.setEnd(rs.getTimestamp("date_end"));
				aux.setLists(getElectionLists(rs.getInt("id")));
				aux.setActive(rs.getInt("active"));
				
				list.add(aux);
			}

			connect.commit();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getTablesStatus(int flag) throws RemoteException {
		String res;
		
		res = getActiveTables(flag);
		res += getDeactiveTables(flag);
		
		return res;
	}
	
	public String getDeactiveTables(int flag) throws RemoteException {
		if(flag == 0) {
			String res = "\n--- Deactive Tables ---\n";
			try {
				Statement statement = connect.createStatement();
				ResultSet rs = statement.executeQuery("SELECT department_id FROM `table` WHERE active=0");
				while(rs.next()) {
						res += "College: " + getCollegeName(getColIDFromDep(rs.getInt("department_id"))) + " | Department: " + getDepartmentName(rs.getInt("department_id")) + "\n";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}
		else {
			String res = "<br>--- Deactive Tables ---<br>";
			try {
				Statement statement = connect.createStatement();
				ResultSet rs = statement.executeQuery("SELECT department_id FROM `table` WHERE active=0");
				while(rs.next()) {
						res += "College: " + getCollegeName(getColIDFromDep(rs.getInt("department_id"))) + " | Department: " + getDepartmentName(rs.getInt("department_id")) + "<br>";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}
	}
	
	public String getActiveTables(int flag) throws RemoteException {
		if(flag == 0) {
			String res = "--- Active Tables ---\n";
			try {
				Statement statement = connect.createStatement();
				ResultSet rs = statement.executeQuery("SELECT department_id FROM `table` WHERE active=1");
				while(rs.next()) {
						res += "College: " + getCollegeName(getColIDFromDep(rs.getInt("department_id"))) + " | Department: " + getDepartmentName(rs.getInt("department_id")) + "\n";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}
		else {
			String res = "--- Active Tables ---<br>";
			try {
				Statement statement = connect.createStatement();
				ResultSet rs = statement.executeQuery("SELECT department_id FROM `table` WHERE active=1");
				while(rs.next()) {
						res += "College: " + getCollegeName(getColIDFromDep(rs.getInt("department_id"))) + " | Department: " + getDepartmentName(rs.getInt("department_id")) + "<br>";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}
	}
	
	public ArrayList<Vote> getVotes(String username) throws RemoteException {
		ArrayList<Vote> votes = new ArrayList<>();
		try {
			int userID = getUserID(username);
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT* FROM vote WHERE user_id=" + userID);
			while(rs.next()) {				
				Vote v = new Vote();
				v.setUsername(username);
				v.setDepartment(getDepartmentName(getDepIDFromTable(rs.getInt("table_id"))));
				v.setCollege(getCollegeName(getColIDFromDep(getDepIDFromTable(rs.getInt("table_id")))));
				v.setElection(getElectionTitle(rs.getInt("election_id")));
				v.setList(getListName(rs.getInt("list_id")));
				v.setDate(rs.getTimestamp("vote_date"));
				
				votes.add(v);
			}
			
			return votes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getListName(int l_id) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT name FROM list WHERE id=" + l_id);
			if(rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getElectionTitle(int e_id) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT title FROM election WHERE id=" + e_id);
			if(rs.next()) {
				return rs.getString("title");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCollegeName(int col_id) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT name FROM college WHERE id=" + col_id);
			if(rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getColIDFromDep(int dep_id) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT college_id FROM department WHERE id=" + dep_id);
			if(rs.next()) {
				return rs.getInt("college_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getDepIDFromTable(int t_id) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT department_id FROM `table` WHERE id=" + t_id);
			if(rs.next()) {
				return rs.getInt("department_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean vote(String username, int serverPort, int e_id, String l_name) throws RemoteException {
		try {
			int u_id = getUserID(username);
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT table_id FROM vote WHERE user_id=" + u_id + " AND election_id=" + e_id);
			
			if(!rs.next()) {
				PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO vote "+
						" (user_id, election_id, list_id, table_id, vote_date) VALUES" +
						" (?,?,?,?,?)");
				
				Date current_date = new Date();
				
				preparedStatement.setInt(1, u_id);
				preparedStatement.setInt(2, e_id);
				preparedStatement.setInt(3, getListID(l_name));
				preparedStatement.setInt(4, getTableID(serverPort));
				preparedStatement.setTimestamp(5, new Timestamp(current_date.getTime()));
				preparedStatement.executeUpdate();
				connect.commit();
				
				return true;
			}
			
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getTableID(int serverPort) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM `table` WHERE server_ip LIKE '%" + serverPort + "%'");
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<Election> getElectionsOfTable(int server_port) throws RemoteException {
		ArrayList<Election> eList = new ArrayList<>();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM `table` WHERE server_ip LIKE '%" + server_port + "%'");
			if(rs.next()) {
				int tID = rs.getInt("id");
				ArrayList<Integer> eIDs = getElectionsIDsOfTable(tID);
				for(int i=0;i<eIDs.size();i++) {
					eList.add(getElectionInfoByID(eIDs.get(i)));
				}
			}
			
			return eList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Election getElectionInfoByID(int el_id) {
		Election e = new Election();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, title, description FROM election WHERE id=" + el_id);
			if(rs.next()) {
				e.setId(rs.getInt("id"));
				e.setTitle(rs.getString("title"));
				e.setDescription(rs.getString("description"));
				e.setLists(getElectionLists(rs.getInt("id")));
			}
			return e;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Integer> getElectionsIDsOfTable(int table_id) {
		ArrayList<Integer> eIDs = new ArrayList<>();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT election_id FROM table_election WHERE table_id=" + table_id);
			while(rs.next()) {
				eIDs.add(rs.getInt("election_id"));
			}
			return eIDs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean login(String username, String password) throws RemoteException {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM user WHERE username='" + username +"' and pw = '" + password + "'");
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteTable(int id) throws RemoteException {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connect.prepareStatement("DELETE FROM `table` WHERE id=" + id);
			preparedStatement.executeUpdate();
			connect.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean changeTableStatus(int id) throws RemoteException {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT active FROM `table` WHERE id=" + id);
			
			if(rs.next()) {
				int active = rs.getInt("active");
				if(active == 1) {
					PreparedStatement preparedStatement = connect.prepareStatement("UPDATE `table` SET active=0 WHERE id=" + id);
					preparedStatement.executeUpdate();
					connect.commit();
					return true;
				}
				else {
					PreparedStatement preparedStatement = connect.prepareStatement("UPDATE `table` SET active=1 WHERE id=" + id);
					preparedStatement.executeUpdate();
					connect.commit();
					return true;
				}
			}
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Table> getTableInfo() throws RemoteException {
		ArrayList<Table> list = new ArrayList<>();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT* FROM `table`");
			
			while(rs.next()) {
				Table t = new Table();
				t.setId(rs.getInt("id"));
				t.setServerIP(rs.getString("server_ip"));
				t.setActive(rs.getInt("active"));
				t.setDepartmentID(rs.getInt("department_id"));
				
				list.add(t);
			}
			
			connect.commit();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getDepartmentName(int id) throws RemoteException {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT name FROM department WHERE id=" + id);
			if(rs.next()) {
				connect.commit();
				return rs.getString("name");
			}
			connect.commit();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean createTable(Table t) throws RemoteException {
		System.out.println("--- Create Table ---");
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM `table` WHERE server_ip='" + t.getServerIP()+ "'");
			if(!rs.next()) {
				PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO `table` "+
						" (server_ip, department_id, active) VALUES" +
						" (?,?,?)");
				preparedStatement.setString(1, t.getServerIP());
				preparedStatement.setInt(2, t.getDepartmentID());
				preparedStatement.setInt(3, 0);
				preparedStatement.executeUpdate();
				connect.commit();
				
				
				insertTable_Election(t);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void insertTable_Election(Table t) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM `table` WHERE server_ip='" + t.getServerIP()+ "'");
			if(rs.next()) {
				if(t.getElectionList().size() > 0) {
					for(int i=0;i<t.getElectionList().size();i++) {
						PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO table_election " +
								" (table_id, election_id) VALUES" +
								" (?,?)");
						preparedStatement.setInt(1, rs.getInt("id"));
						preparedStatement.setInt(2, t.getElectionList().get(i).getId());
						preparedStatement.executeUpdate();
						connect.commit();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean editElection(Election e, ArrayList<String> oldList) throws RemoteException {
		System.out.println("--- Edit Election ---");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		
		try {
			PreparedStatement preparedStatement = connect.prepareStatement("UPDATE election SET title='" + e.getTitle() +"', description='" + e.getDescription() + "'" +
					", date_begin='" + sdf.format(e.getBegin()) + "', date_end='"+ sdf.format(e.getEnd()) +"' WHERE id=" + e.getId() + "");
			preparedStatement.executeUpdate();
			
			for(int i=0;i<oldList.size();i++) {
				boolean exists = false;
				for(int j=0;j<e.getLists().size();j++) {
					if(oldList.get(i).toLowerCase().equals(e.getLists().get(j).toLowerCase())) {
						exists = true;
					}
				}
				
				if(!exists) {
					preparedStatement = connect.prepareStatement("DELETE FROM election_list WHERE (election_id=" + e.getId() + " AND list_id='" + getListID(oldList.get(i)) + "')");
					preparedStatement.executeUpdate();
				}
			}
			
			for(int i=0;i<e.getLists().size();i++) {
				boolean exists = false;
				for(int j=0;j<oldList.size();j++) {
					if(e.getLists().get(i).toLowerCase().equals(oldList.get(j).toLowerCase())) {
						exists = true;
					}
				}
				
				if(!exists) {
					preparedStatement = connect.prepareStatement("INSERT INTO election_list "+
							" (election_id, list_id) VALUES" +
							" (?,?)");
					
					preparedStatement.setInt(1, e.getId());
					preparedStatement.setInt(2, getListID(e.getLists().get(i)));
					preparedStatement.executeUpdate();
				}
			}

			connect.commit();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<Election> getElectionsInfo() throws RemoteException {
		System.out.println("--- Get Elections Information ---");
		ArrayList<Election> list = new ArrayList<>();

		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT* FROM election WHERE finish=0");
			while(rs.next()) {
				Election aux = new Election();
				aux.setId(rs.getInt("id"));
				aux.setTitle(rs.getString("title"));
				aux.setDescription(rs.getString("description"));
				aux.setBegin(rs.getTimestamp("date_begin"));
				aux.setEnd(rs.getTimestamp("date_end"));
				aux.setLists(getElectionLists(rs.getInt("id")));
				aux.setActive(rs.getInt("active"));
				
				list.add(aux);
			}

			connect.commit();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getElectionLists(int el_id) {
		ArrayList<String> list = new ArrayList<>();

		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT li.name FROM list li INNER JOIN election_list el ON li.id=el.list_id WHERE el.election_id=" + el_id);

			while(rs.next()) {
				list.add(rs.getString("name"));
			}

			connect.commit();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean editList(List l, ArrayList<String> oldList) throws RemoteException {
		System.out.println("--- Edit List ---");
		try {
			PreparedStatement preparedStatement = connect.prepareStatement("UPDATE list SET name='" + l.getName() + "'" + " WHERE id=" + l.getId() + "");
			preparedStatement.executeUpdate();
			
			for(int i=0;i<oldList.size();i++) {
				boolean exists = false;
				for(int j=0;j<l.getMembers().size();j++) {
					if(oldList.get(i).toLowerCase().equals(l.getMembers().get(j).toLowerCase())) {
						exists = true;
					}
				}
				
				if(!exists) {
					preparedStatement = connect.prepareStatement("DELETE FROM user_list WHERE (list_id=" + l.getId() + " AND user_id='" + getUserID(oldList.get(i)) + "')");
					preparedStatement.executeUpdate();
				}
			}
			
			for(int i=0;i<l.getMembers().size();i++) {
				boolean exists = false;
				for(int j=0;j<oldList.size();j++) {
					if(l.getMembers().get(i).toLowerCase().equals(oldList.get(j).toLowerCase())) {
						exists = true;
					}
				}
				
				if(!exists) {
					preparedStatement = connect.prepareStatement("INSERT INTO user_list "+
							" (list_id, user_id) VALUES" +
							" (?,?)");
					
					preparedStatement.setInt(1, l.getId());
					preparedStatement.setInt(2, getUserID(l.getMembers().get(i)));
					preparedStatement.executeUpdate();
				}
			}

			connect.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<List> getListsInfo() throws RemoteException {
		System.out.println("--- Get list info ---");
		ArrayList<List> list = new ArrayList<>();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name, type FROM list");
			
			while(rs.next()) {
				List aux = new List();
				aux.setId(rs.getInt("id"));
				aux.setName(rs.getString("name"));
				aux.setType(rs.getString("type"));
				aux.setMembers(getListUsers(rs.getInt("id")));
				
				list.add(aux);
			}
			
			connect.commit();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getListUsers(int list_id) {
		ArrayList<String> list = new ArrayList<>();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT us.username FROM USER us INNER JOIN user_list ul ON us.id=ul.user_id WHERE ul.list_id=" + list_id);
			
			while(rs.next()) {
				list.add(rs.getString("username"));
			}
			
			connect.commit();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createList(List list) throws RemoteException {
		System.out.println("--- Create List ---");
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT name FROM list WHERE name='" + list.getName() + "'");
			if(!rs.next()) {
				PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO list "+
						" (name, type) VALUES" +
						" (?,?)");

				preparedStatement.setString(1, list.getName());
				preparedStatement.setString(2, list.getType().toLowerCase());
				preparedStatement.executeUpdate();
				connect.commit();
				
				int list_id = getListID(list.getName());
				int user_id;
				
				if(list.getMembers().size() > 0) {
					for(int i=0;i<list.getMembers().size();i++) {
						user_id = checkUser(list.getMembers().get(i).toLowerCase(), list.getType().toLowerCase());
						if(user_id >= 0) {
							preparedStatement = connect.prepareStatement("INSERT INTO user_list "+
									" (user_id, list_id) VALUES" +
									" (?,?)");

							preparedStatement.setInt(1, user_id);
							preparedStatement.setInt(2, list_id);
							preparedStatement.executeUpdate();
						}
						else {
							preparedStatement = connect.prepareStatement("Delete FROM list WHERE id=" + list_id);
							preparedStatement.executeUpdate();
							connect.commit();
							return false;
						}
					}
				}
				
				connect.commit();
                return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<Person> getUsers() throws RemoteException {
		System.out.println("--- Get users ---");
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, username, job FROM user");
			
			ArrayList<Person> pList = new ArrayList<>();
			
			while(rs.next()) {
				Person p = new Person();
				
				p.setId(rs.getInt("id"));
				p.setUsername(rs.getString("username"));
				p.setJob(rs.getString("job"));
				
				pList.add(p);
			}
			
			connect.commit();
			return pList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean createElection(Election e) throws RemoteException {
		System.out.println("--- Create Election ---");
		try {
			Statement statement = connect.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT title FROM election WHERE title='" + e.getTitle() + "'");
			if(!rs.next()) {
				PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO election "+
                        " (title, description, date_begin, date_end, election_type, active, finish) VALUES" +
                        " (?,?,?,?,?,?,?)");
				
				Date start_election = e.getBegin();
				Date current_date = new Date();
				
				if (start_election.after(current_date)) {
					preparedStatement.setString(1, e.getTitle());
					preparedStatement.setString(2, e.getDescription());
					preparedStatement.setTimestamp(3, new Timestamp(e.getBegin().getTime()));
					preparedStatement.setTimestamp(4, new Timestamp(e.getEnd().getTime()));
					preparedStatement.setInt(5, e.getType());
					preparedStatement.setInt(6, 0);
					preparedStatement.setInt(7, 0);
					preparedStatement.executeUpdate();
					connect.commit();
					
					if(e.getLists().size() > 0) {
						for(int i=0;i<e.getLists().size();i++) {
							insertListIntoElection(e.getLists().get(i), e.getTitle());
						}
					}
				}
				else {
					return false;
				}
				
                connect.commit();
                return true;
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public boolean removeCollege(int id) throws RemoteException {
		System.out.println("--- Remove college ---");
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT name FROM college WHERE id=" + id + "");
			if(rs.next()) {
				PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM college WHERE id='" + id + "'");
				preparedStatement.executeUpdate();
				
                connect.commit();
                return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean editCollege(College col, ArrayList<Department> old_dep) throws RemoteException {
		System.out.println("--- Edit College ---");
		try {
			PreparedStatement preparedStatement = connect.prepareStatement("UPDATE college SET name='" + col.getName() + "'" + ", initials='" + col.getInitials() + "'" + " WHERE id=" + col.getId() + "");
			preparedStatement.executeUpdate();
			for(int i=0;i<old_dep.size();i++) {
				boolean exists = false;
				for(int j=0;j<col.getDepList().size();j++) {
					if(old_dep.get(i).getName().toLowerCase().equals(col.getDepList().get(j).getName().toLowerCase())) {
						exists = true;
					}
				}
				
				if(!exists) {
					preparedStatement = connect.prepareStatement("DELETE FROM department WHERE (college_id=" + col.getId() + " AND name='" + old_dep.get(i).getName() + "')");
					preparedStatement.executeUpdate();
				}
			}
			
			for(int i=0;i<col.getDepList().size();i++) {
				boolean exists = false;
				for(int j=0;j<old_dep.size();j++) {
					if(col.getDepList().get(i).getName().toLowerCase().equals(old_dep.get(j).getName().toLowerCase())) {
						exists = true;
					}
				}
				
				if(!exists) {
					preparedStatement = connect.prepareStatement("INSERT INTO department "+
							" (name, college_id) VALUES" +
							" (?,?)");
					preparedStatement.setString(1, col.getDepList().get(i).getName());
					preparedStatement.setInt(2, col.getId());
					preparedStatement.executeUpdate();
				}
			}

			connect.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<College> getAllCollegeInfo() throws RemoteException{
		System.out.println("--- Get college Info ---");
		ArrayList<College> colleges = new ArrayList<>();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT* FROM college");
			
			while(rs.next()) {
				College aux = new College();
				aux.setId(rs.getInt("id"));
				aux.setName(rs.getString("name"));
				aux.setInitials(rs.getString("initials"));
				aux.setDepList(getDepartments(aux.getId()));
				
				colleges.add(aux);
			}
            
			connect.commit();
			return colleges;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Department> getDepartments(int college_id) {
		System.out.println("--- Get Departments ---");
		ArrayList<Department> depList = new ArrayList<>();
		
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name FROM department WHERE college_id=" + college_id + "");

			while(rs.next()) {
				Department dep = new Department();
				dep.setName(rs.getString("name"));
				dep.setId(rs.getInt("id"));
				depList.add(dep);
			}
			
			connect.commit();
			return depList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	public boolean createCollege(College college) throws RemoteException {
		System.out.println("--- Create College ---");
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT name FROM college WHERE name='" + college.getName() + "'");
			if(!rs.next()) {
				PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO college "+
                        " (name, initials) VALUES" +
                        " (?,?)");
				
				preparedStatement.setString(1, college.getName());
				preparedStatement.setString(2, college.getInitials());
				preparedStatement.executeUpdate();
				connect.commit();
                
                if(college.getDepList().size() != 0) {
                	Statement statement2 = connect.createStatement();
        			ResultSet rs2 = statement2.executeQuery("SELECT id FROM college WHERE name='" + college.getName() + "'");
        			
        			if(rs2.next()) {
        				for(int i = 0;i<college.getDepList().size();i++) {
                    		preparedStatement = connect.prepareStatement("INSERT INTO department "+
                                    " (name, college_id) VALUES" +
                                    " (?,?)");
                        	
                        	preparedStatement.setString(1, college.getDepList().get(i).getName());
            				preparedStatement.setInt(2, rs2.getInt("id"));
            				preparedStatement.executeUpdate();
                    	}
        			}
                }
                
                connect.commit();
                return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean registePerson(Person p) throws RemoteException {
		System.out.println("--- Regist Person ---");
		try {
			Statement statement = connect.createStatement();
			Statement statement2 = connect.createStatement();
			Statement statement3 = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT username FROM user WHERE username='" + p.getUsername()+ "'");
			ResultSet rs2 = statement2.executeQuery("SELECT id FROM college WHERE name='" + p.getCollege() + "'");
			ResultSet rs3 = statement3.executeQuery("SELECT id FROM department WHERE name='" + p.getDepartment() + "'");
			
			if(!rs.next()) {
				if(rs2.next()) {
					if(rs3.next()) {
						PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO user "+
		                        " (username, pw, job, college_id, department_id, address, phone, cc_number, cc_date) VALUES" +
		                        " (?,?,?,?,?,?,?,?,?)");
						
						preparedStatement.setString(1, p.getUsername());
						preparedStatement.setString(2, p.getPw());
						preparedStatement.setString(3, p.getJob());
						preparedStatement.setInt(4, rs2.getInt("id"));
						preparedStatement.setInt(5,  rs3.getInt("id"));
						preparedStatement.setString(6, p.getAddress());
						preparedStatement.setInt(7, p.getTelephone());
						preparedStatement.setInt(8, p.getCC_number());
						preparedStatement.setDate(9, new java.sql.Date(p.getCC_date().getTime()));
						
						preparedStatement.executeUpdate();
		                connect.commit();
		                
		                return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void insertListIntoElection(String listName, String el_Title) {
		try {
			PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO election_list"+
			        " (election_id, list_id) VALUES" +
			        " (?,?)");
			
			preparedStatement.setInt(1, getElectionID(el_Title));
			preparedStatement.setInt(2, getListID(listName));
			preparedStatement.executeUpdate();
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getElectionID(String title) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM election WHERE title='" + title +"'");
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getListID(String listName) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM list WHERE name='" + listName +"'");
			
			if(rs.next()) {
				connect.commit();
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int checkUser(String username, String type) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM user WHERE username='" + username + "' AND job='" + type + "'");
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getUserID(String username) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM user WHERE username='" + username +"'");
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// ========================================================================================================================================================
	
	public static void main(String[] args) {
		int[] ports = loadPorts();
		whatToDo(ports);
	}
	
	public static void whatToDo(int[] ports) {
		if(ports == null) {
			System.out.println("Failed to load data from configurations file !!!");
			System.exit(0);
		}
		else {
			if(checkConnection(ports[0]) && checkConnection(ports[1])) { //2 up
				System.out.println("Both servers online !!!");
				System.exit(0);
			}
			else {
				if(!checkConnection(ports[0]) && !checkConnection(ports[1])) { //2 down
					startAsMainServer(ports[0]);
				}
				else if (checkConnection(ports[0])){
					System.out.println("RMI Server on port(" + ports[0] + ") is connected");
					Thread_SendUDP aux = new Thread_SendUDP(ports[0]);
					while(!aux.t.isInterrupted()) {}
					
					System.out.println("RMI Server on port(" + ports[0] + ") crashed goit to start at port (" + ports[1] + ")");
					startAsMainServer(ports[1]);
				}
				else if (checkConnection(ports[1])){
					System.out.println("RMI Server on port(" + ports[1] + ") is connected");
					Thread_SendUDP aux = new Thread_SendUDP(ports[1]);
					while(!aux.t.isInterrupted()) {}
					
					System.out.println("RMI Server on port(" + ports[1] + ") crashed goit to start at port (" + ports[0] + ")");
					startAsMainServer(ports[0]);
				}
			}
		}
	}
	
	public static void startAsMainServer(int port) {
		Properties properties = new Properties();
		System.out.println("Loading configuration file ...");
		InputStream configs = Server_RMI.class.getResourceAsStream("/resources/configRMI.properties");
		
		try { // try to get data from configuration file
			String user = null, pw = null;
		    String db_url = "jdbc:mysql://";
		    
			properties.load(configs);
			
			db_url += properties.getProperty("DATABASE_ADDRESS");
			user = properties.getProperty("DATABASE_USER");
		    pw = properties.getProperty("DATABASE_PASS");
		    
		    System.out.println("Configuration file loaded sucessfully");
		    startRMIServer(port, db_url, user, pw);
		} catch (IOException e) {
			System.out.println("Failed to load data from configurations file !!!");
		}
	}
	

	public static void startRMIServer(int rmi_port, String url, String user, String pw) {
		System.out.println("Starting RMI server ...");
		try {
			System.setProperty("java.rmi.server.hostname", "169.254.22.12");
			Server_RMI_Backup h = new Server_RMI_Backup();
			Registry r = LocateRegistry.createRegistry(rmi_port);
			r.rebind("rmi", h);
			
			connectToDB(url, user, pw);
			
			new Thread_CheckElections(connect);
			new Thread_CheckTables(connect);
			new Thread_ReciveUDP(rmi_port);
			
			System.out.println("RMI Server ready (port = " + rmi_port + ")");
		} catch (RemoteException e) {
			System.out.println("Failed to start the server !!!");
			e.printStackTrace();
		}
	}
	
	public static boolean checkConnection(int port) {
		try {
			h = (RMI_Interface) LocateRegistry.getRegistry(port).lookup("rmi");
			return true;
		} catch (AccessException e) {
			return false;
		} catch (RemoteException e) {
			return false;
		} catch (NotBoundException e) {
			return false;
		}
	}
	

	public static int[] loadPorts() {
		int[] ports = new int[2];
		Properties properties = new Properties();

		System.out.println("Loading configuration file ...");
		InputStream configs = Server_RMI_Backup.class.getResourceAsStream("/resources/configRMI.properties");

		try { // try to get data from configuration file

			properties.load(configs);

			ports[0] = Integer.parseInt(properties.getProperty("RMI_PORT"));
			ports[1] = Integer.parseInt(properties.getProperty("RMI_PORT2"));

			return ports;

		} catch (IOException e) {
			System.out.println("Failed to load data from configurations file !!!");
		}
        return null;
	}
	
	public static void connectToDB(String url, String user, String pw) {
		System.out.println("Connecting to database ( " + url + " ) ...");
		url += "?autoReconnect=true&useSSL=false";
		
		try {
			connect = DriverManager.getConnection(url, user, pw);
			connect.setAutoCommit(false);
			System.out.println("Database connected");
		} catch (SQLException e) {
			System.out.println("Failed to connected the database !!!");
		}
	}
}
