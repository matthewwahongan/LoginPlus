package de.yellowphoenix18.loginplus.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.utils.PluginUtils;

@SuppressWarnings("deprecation")
public class LoginListener implements Listener {
	
	@EventHandler
	public void on(PlayerPreLoginEvent e) {
		String player = e.getName();
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(all.getName().equalsIgnoreCase(player)) {
				e.disallow(null, MessagesConfig.prefix + MessagesConfig.already_logged_in);
				break;
			}
		}
	}
	
	@EventHandler
	public void on(PlayerLoginEvent e) {
		String ip = e.getAddress().toString().substring(1 , e.getAddress().toString().length()).split(":")[0];
		if(PluginUtils.banned_ips.containsKey(ip)) {
			e.disallow(null, MessagesConfig.prefix + MessagesConfig.banned.replace("%BanTime%", PluginUtils.banned_ips.get(ip) + ""));
		}
	}

}
