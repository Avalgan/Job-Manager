package ch.avalgan.job.utils.events;

import static ch.avalgan.job.main.JobManager.connection;
import static ch.avalgan.job.main.JobManager.logger;

import java.sql.SQLException;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.avalgan.job.utils.Commons.AinsiColors;
import ch.avalgan.job.utils.sql.PlayerManager;

/**
 * 
 * @author __Wither
 *
 */
public class PlayerJoinEvents implements Listener {
	PlayerManager pm = new PlayerManager(connection);

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		OfflinePlayer player = e.getPlayer();
		try {
			if(! player.hasPlayedBefore()) {
				pm.registerPlayer(player);
				logger.info(AinsiColors.GREEN + "--==Joueur " + player.getName() + " Enregistré==--" + AinsiColors.NONE);
			} else {
				pm.changeLastJoinDate(player);
				logger.info(AinsiColors.GREEN + "--==Le joueur " + player.getName() + " S'est reconnecté !==--" + AinsiColors.NONE);
			}
		} catch (SQLException sqlErr) {
			logger.warning(AinsiColors.RED + "-----------------------==[Jobs]==-----------------------" + AinsiColors.NONE);
			logger.warning(AinsiColors.RED + "Impossible d'enregistrer/modifier le joueur dans la base de donee." + AinsiColors.NONE);
			logger.warning(sqlErr.getMessage());
			logger.warning(AinsiColors.RED + "StackTrace : " + AinsiColors.NONE);
			sqlErr.printStackTrace();
			logger.warning(AinsiColors.RED + "--------------------------------------------------------" + AinsiColors.NONE);
		}
		
	}
}
