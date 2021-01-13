package ch.avalgan.job.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.bukkit.OfflinePlayer;

public class PlayerManager {
	private Connection connection;
	
	/**
	 * Constructeur
	 * @param connection une connexion active
	 */
	public PlayerManager(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Enregistre un nouveau joueur dans la BD
	 * @param joueur lejoueur a ajouter
	 * @throws SQLException
	 */
	public void registerPlayer(OfflinePlayer joueur) throws SQLException {
		// Préparation de la requête
		PreparedStatement ps = connection.prepareStatement("INSERT INTO joueurs(pseudo,firstJoinDate,lastJoinDate) VALUES (?,?,?)");
		
		
		// Remplacement des "?"
		ps.setString(1, joueur.getName());
		ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		
		//execution
		ps.execute();
		
		
		//fermer le pointeur 
		ps.close();
	}
	
	/**
	 * Met a jour la date de connexion du joueur
	 * @param joueur le joueur concerné
	 * @throws SQLException
	 */
	public void changeLastJoinDate(OfflinePlayer joueur) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE joueurs SET lastJoinDate = ? WHERE pseudo = ?");
		
		ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
		ps.setString(2, joueur.getName());
		
		ps.execute();
		
		ps.close();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setChomeur(OfflinePlayer player) throws SQLException {
		int playerNum = -1;
		// Préparation de la requête
		PreparedStatement ps = connection.prepareStatement("SELECT idJoueur FROM joueurs WHERE pseudo = ?");
				
				
		// Remplacement des "?"
		ps.setString(1, player.getName());
		
		//execution
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			playerNum = rs.getInt("idJoueur");
		}
		
		
		//fermer le pointeur 
		ps.close();
		
		ps = connection.prepareStatement("INSERT INTO chomeurs(idJoueur) VALUES (?)");
		ps.setInt(1, playerNum);
		ps.execute();
		ps.close();
	}
}
