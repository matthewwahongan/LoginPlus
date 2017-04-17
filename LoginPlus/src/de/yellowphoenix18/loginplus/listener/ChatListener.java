package de.yellowphoenix18.loginplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.yellowphoenix18.loginplus.config.MainConfig;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.config.PasswordConfig;
import de.yellowphoenix18.loginplus.utils.EncryptionType;
import de.yellowphoenix18.loginplus.utils.EncryptionUtils;
import de.yellowphoenix18.loginplus.utils.PluginUtils;
import de.yellowphoenix18.loginplus.versionutils.VersionUtils;

public class ChatListener implements Listener {
	
	@EventHandler
	public void on(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(PluginUtils.login.contains(p)) {
			e.setCancelled(true);
			String password = e.getMessage();
			EncryptionType type = PasswordConfig.getHashtype(p.getUniqueId().toString());
			if(EncryptionUtils.hashPassword(password, type).equalsIgnoreCase(PasswordConfig.getHashedPassword(p.getUniqueId().toString()))) {
				PluginUtils.login.remove(p);
				PluginUtils.timer.remove(p);
				VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_login_success_title, MessagesConfig.title_login_success_subtitle);
			} else {
				PluginUtils.attempts.put(p, PluginUtils.attempts.get(p)-1);
				if(PluginUtils.attempts.get(p) <= 0) {
					PluginUtils.attempts.remove(p);
					PluginUtils.kick.add(p);
					String ip = p.getAddress().getAddress().toString().replace("/", "");
					if(MainConfig.login_failed_ban == true) {
						PluginUtils.banned_ips.put(ip, MainConfig.login_failed_ban_time);
					}
				} else {
					VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_login_failed_title, MessagesConfig.title_login_failed_subtitle.replace("%Attempts%", PluginUtils.attempts.get(p) + ""));
				}
			}
		} else if(PluginUtils.register.contains(p)) {
			e.setCancelled(true);
			String password = e.getMessage();
			PasswordConfig.setPassword(p.getUniqueId().toString(), EncryptionUtils.hashPassword(password, MainConfig.type), MainConfig.type.toString());
			VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_register_success_title, MessagesConfig.title_register_success_subtitle);
			PluginUtils.register.remove(p);
		}
	}

}
