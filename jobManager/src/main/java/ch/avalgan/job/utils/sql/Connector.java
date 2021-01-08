package ch.avalgan.job.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe pour connecter plus facilement le plugin a une base de donnée
 * @author __Wither
 * @since 0.0.1 - SNAPSHOT
 */
public class Connector {
	private Connection connection;
	
	
	public Connection connect(String type, String host, int port, String dbName, String user, String pwd, boolean reconnect, boolean usessl) throws ClassNotFoundException, SQLException {
		if(this.connected()) {
			return this.connection;
		}
		
		String url = new StringBuilder()
				.append("jdbc:")
				.append(type)
				.append("://")
				.append(host)
				.append(":")
				.append(port)
				.append("/")
				.append(dbName)
				.append("?autoreconnect=")
				.append(reconnect)
				.append("&useSSL=")
				.append(usessl) //retourne jdbc:TYPE://HOST:PORT/DBNAME?autoreconnect=RECONNECT&usessl=USESSL
								//avec la config par défaut : jdbc:mysql://localhost:3306/avalganjobs?autoreconnect=true&usessl=false
				.toString();
		
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, user, pwd);
		return this.connection;
	}
	
	
	public boolean connected() {
		try {
			return (this.connection != null) && (!this.connection.isClosed());
		} catch (SQLException e) {
			return false;
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	
}
