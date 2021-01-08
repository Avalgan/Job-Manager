package ch.avalgan.job.utils.sql;

import java.sql.Connection;

public class PlayerManager {
	private Connection connection;
	
	public PlayerManager(Connection connection) {
		this.connection = connection;
	}
	
	

	public Connection getConnection() {
		return connection;
	}
}
