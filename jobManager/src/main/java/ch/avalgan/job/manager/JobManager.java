package ch.avalgan.job.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ch.avalgan.job.utils.Commons.AinsiColors;
import ch.avalgan.job.utils.sql.Connector;
import ch.avalgan.job.utils.sql.DataManager;

/**
 * La classe principale du plugin, elle contient le coeur du plugin
 * @author __Wither
 * @since 0.0.1-SNAPSHOT
 */
public class JobManager extends JavaPlugin{
	
	private DataManager dm;
	
	public FileConfiguration config = getConfig();

	public ConfigurationSection databaseConfig;
	

	public static String type, host, dbName, dbUser, dbPwd;
	public static int port;
	public static boolean reconnection, ssl;

	public Connector connector;
	public Connection connection;
	public static Logger logger;
	/**
	 * Appelé au démarrage du plugin, surcharge du onEnable de JavaPlugin
	 */
	@Override
	public void onEnable() {
		saveDefaultConfig();
		databaseConfig = config.getConfigurationSection("Base de donee");
		JobManager.logger = this.getLogger();
		
		type = databaseConfig.getString("BD_TYPE");
		host = databaseConfig.getString("BD_ADRESS");
		port = databaseConfig.getInt("BD_PORT");
		dbName = databaseConfig.getString("BD_NAME");
		dbUser = databaseConfig.getString("BD_USER");
		dbPwd = databaseConfig.getString("BD_PASSWORD");
		reconnection = databaseConfig.getBoolean("BD_RECONNECTION");
		ssl = databaseConfig.getBoolean("BD_SSL");
		
		this.connector = new Connector();
		
		try {
			this.connector.connect(type, host, port, dbName, dbUser, dbPwd, reconnection, ssl);
			
			this.connection = connector.getConnection();
			
			dm = new DataManager(connection);
			dm.createTables();
			
			logger.info(AinsiColors.GREEN + "--==Base de donnee " + dbName + " conectee==--" + AinsiColors.NONE);
		} catch (ClassNotFoundException e) {
			logger.warning(AinsiColors.RED + "-----------------------==[Jobs]==-----------------------" + AinsiColors.NONE);
			logger.warning(AinsiColors.RED + "Impossible de trouver le driver " + type + " pour JDBC, veuillez contacter l'auteur du plugin." + AinsiColors.NONE);
			logger.warning(AinsiColors.RED + "--------------------------------------------------------" + AinsiColors.NONE);
		} catch (SQLException e) {
			logger.warning(AinsiColors.RED + "-----------------------==[Jobs]==-----------------------" + AinsiColors.NONE);
			logger.warning(AinsiColors.RED + "Impossible de se connecter a la base de donee." + AinsiColors.NONE);
			logger.warning(e.getMessage());
			logger.warning(AinsiColors.RED + "StackTrace : " + AinsiColors.NONE);
			e.printStackTrace();
			logger.warning(AinsiColors.RED + "--------------------------------------------------------" + AinsiColors.NONE);
		}
		
		logger.info(AinsiColors.GREEN + "--==Plugin active==--" + AinsiColors.NONE);
	}
	
	/**
	 * Appelé a l'extinction du plugin, surcharge du onDisable de JavaPlugin
	 */
	@Override
	public void onDisable() {
		getLogger().info(AinsiColors.RED + "--==Plugin desactive==--" + AinsiColors.NONE);
	}
}
