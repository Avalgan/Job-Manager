package ch.avalgan.job.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import ch.avalgan.job.main.JobManager;
import ch.avalgan.job.utils.Commons.AinsiColors;

/**
 * Classe permettant de gérer les données sql en général
 * @author __Wither
 * @since 0.0.1 - SNAPSHOT
 */
public class DataManager {
	private Connection connection;
	
	/**
	 * Constructeur de la classe
	 * @param connection : la connexion SQL
	 * @since 0.0.1 - SNAPSHOT
	 */
	public DataManager(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * crée les tables SQL si elles n'existent pas 
	 * @since 0.0.1 - SNAPSHOT
	 */
	public void createTables() {
		Logger logger = JobManager.logger;
		try {
			String builderTable = "CREATE TABLE IF NOT EXISTS `builders` ("
					+ "  `idJoueur` int(11) NOT NULL,"
					+ "  `libre` tinyint(1) NOT NULL,"
					+ "  `niveau` int(11) NOT NULL,"
					+ "  `votesNegatifs` int(11) NOT NULL,"
					+ "  `votesPositifs` int(11) NOT NULL,"
					+ "  PRIMARY KEY (`idJoueur`),"
					+ "  FOREIGN KEY (`idJoueur`) REFERENCES `joueurs` (`idJoueur`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
			
			String builderTasksTable = "CREATE TABLE IF NOT EXISTS `buildertasks` ("
					+ "  `idBuilder` int(11) NOT NULL,"
					+ "  `idClient` int(11) NOT NULL,"
					+ "  `dateDemande` datetime NOT NULL,"
					+ "  PRIMARY KEY (`idBuilder`,`idClient`),"
					+ "  FOREIGN KEY (`idBuilder`) REFERENCES `builders` (`idJoueur`),"
					+ "  FOREIGN KEY (`idClient`) REFERENCES `joueurs` (`idJoueur`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
			
			String chomeurTable = "CREATE TABLE IF NOT EXISTS `chomeurs` ("
					+ "  `idJoueur` int(11) NOT NULL,"
					+ "  FOREIGN KEY (`idJoueur`) REFERENCES `joueurs` (`idJoueur`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
			
			String jobTable = "CREATE TABLE IF NOT EXISTS `jobs` ("
					+ "  `idJob` int(11) NOT NULL AUTO_INCREMENT,"
					+ "  `nom` varchar(255) NOT NULL,"
					+ "  `difficulté` smallint(3) NOT NULL,"
					+ "  PRIMARY KEY (`idJob`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
			
			String joueurTable = "CREATE TABLE IF NOT EXISTS `joueurs` ("
					+ "  `idJoueur` int(11) NOT NULL AUTO_INCREMENT,"
					+ "  `pseudo` varchar(255) NOT NULL,"
					+ "  `firstJoinDate` datetime NOT NULL,"
					+ "  `lastJoinDate` datetime NOT NULL,"
					+ "  PRIMARY KEY (`idJoueur`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
			
			String reportTable =  "CREATE TABLE IF NOT EXISTS `reports` ("
					+ "  `idReporter` int(11) NOT NULL,"
					+ "  `idReported` int(11) NOT NULL,"
					+ "  `raison` varchar(255) NOT NULL,"
					+ "  PRIMARY KEY (`idReporter`,`idReported`),"
					+ "  FOREIGN KEY (`idReporter`) REFERENCES `joueurs` (`idJoueur`),"
					+ "  FOREIGN KEY (`idReported`) REFERENCES `joueurs` (`idJoueur`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
			
			String travailleurTable = "CREATE TABLE IF NOT EXISTS `travailleurs` ("
					+ "  `idJoueur` int(11) NOT NULL,"
					+ "  `idJob` int(11) NOT NULL,"
					+ "  `xpTotal` decimal(7,2) NOT NULL,"
					+ "  `niveau` int(11) NOT NULL,"
					+ "  PRIMARY KEY (`idJoueur`,`idJob`),"
					+ "  FOREIGN KEY (`idJob`) REFERENCES `jobs` (`idJob`),"
					+ "  FOREIGN KEY (`idJoueur`) REFERENCES `joueurs` (`idJoueur`)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
			
		
			runNoReturnSQLRequests(joueurTable, "joueur");
			runNoReturnSQLRequests(builderTable, "builder");
			runNoReturnSQLRequests(builderTasksTable, "builder tasks");
			runNoReturnSQLRequests(chomeurTable, "chomeur");
			runNoReturnSQLRequests(jobTable, "job");
			runNoReturnSQLRequests(reportTable, "report");
			runNoReturnSQLRequests(travailleurTable, "travailleur");
			
			logger.info(AinsiColors.GREEN + "---==[Tables crees avec succes]==---" + AinsiColors.NONE);
		} catch(SQLException e) {
			logger.warning(AinsiColors.RED + "-----------------------==[Jobs]==-----------------------" + AinsiColors.NONE);
			logger.warning(AinsiColors.RED + "Impossible de creer les tables." + AinsiColors.NONE);
			logger.warning(e.getMessage());
			logger.warning(AinsiColors.RED + "StackTrace : " + AinsiColors.NONE);
			e.printStackTrace();
			logger.warning(AinsiColors.RED + "--------------------------------------------------------" + AinsiColors.NONE);
		}
	}

	/**
	 * Permet d'éxecuter une requête SQL sans valeur de retour
	 * @param sql la requête à executer
	 * @throws SQLException si la requête est invalide, ou qu'il y a un problème sur la BD
	 */
	private void runNoReturnSQLRequests(String sql, String name) throws SQLException {
		PreparedStatement q = this.connection.prepareStatement(sql);
		
		
		q.execute();
		
		q.close();
		JobManager.logger.info(AinsiColors.GREEN + "---==[Tables "+ name + " creee avec succes]==---" + AinsiColors.NONE);
	}
}
