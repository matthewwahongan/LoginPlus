package de.yellowphoenix18.loginplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.yellowphoenix18.loginplus.config.MainConfig;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.config.PasswordConfig;
import de.yellowphoenix18.loginplus.utils.PluginUtils;
import de.yellowphoenix18.loginplus.versionutils.VersionUtils;

public class JoinListener implements Listener {
	
	@EventHandler
	public void on(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(PasswordConfig.isSet(p.getUniqueId().toString())) {
			VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_login_title, MessagesConfig.title_login_subtitle);
			PluginUtils.login.add(p);
			if(MainConfig.timer_enabled) {
				PluginUtils.timer.put(p, 0);
			}
			PluginUtils.attempts.put(p, MainConfig.login_attempts);
		} else {
			VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_register_title, MessagesConfig.title_register_subtitle);
			PluginUtils.register.add(p);
		}
		
		
	}

}
