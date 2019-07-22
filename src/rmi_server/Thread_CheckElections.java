package rmi_server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

class Thread_CheckElections implements Runnable {
	Thread t;
	Connection connect;
	
	public Thread_CheckElections(Connection connect) {
		this.connect = connect;
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		while(true) {
			checkElections(connect);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkElections(Connection connect) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, active, date_begin, date_end FROM election WHERE finish=0");
			
			Date begin_date;
			Date end_date;
			while(rs.next()) {
				begin_date = rs.getTimestamp("date_begin");
				end_date = rs.getTimestamp("date_end");
				
				Date current_date = new Date();
				
				if (begin_date.before(current_date)  && end_date.after(current_date)) {
					if(rs.getInt("active") == 0) {
						PreparedStatement preparedStatement = connect.prepareStatement("UPDATE election SET active=1 WHERE id=" + rs.getInt("id"));
						preparedStatement.executeUpdate();
						connect.commit();
					}
				}
				
				if(end_date.before(current_date)) {
					if(rs.getInt("active") == 1) {
						PreparedStatement preparedStatement = connect.prepareStatement("UPDATE election SET active=0, finish=1 WHERE id=" + rs.getInt("id"));
						preparedStatement.executeUpdate();
						connect.commit();
						
						preparedStatement = connect.prepareStatement("DELETE FROM table_election WHERE election_id=" + rs.getInt("id"));
						preparedStatement.executeUpdate();
						connect.commit();
						
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
