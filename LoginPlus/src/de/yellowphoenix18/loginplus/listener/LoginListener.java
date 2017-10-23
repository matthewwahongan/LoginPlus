package de.yellowphoenix18.loginplus.listener;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import de.yellowphoenix18.loginplus.LoginPlus;
import de.yellowphoenix18.loginplus.config.DataTranslator;
import de.yellowphoenix18.loginplus.config.MYSQLConfig;
import de.yellowphoenix18.loginplus.config.MYSQLMethods;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.config.PasswordConfig;
import de.yellowphoenix18.loginplus.utils.AccountObject;
import de.yellowphoenix18.loginplus.utils.EncryptionType;
import de.yellowphoenix18.loginplus.utils.PluginUtils;

@SuppressWarnings("deprecation")
public class LoginListener implements Listener {
	
	@EventHandler
	public void on(PlayerPreLoginEvent e) {
		String player = e.getName();
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(all.getName().toLowerCase().equalsIgnoreCase(player.toLowerCase())) {
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
		String uuid = e.getPlayer().getUniqueId().toString();
		if(MYSQLConfig.enabled) {
			Bukkit.getScheduler().runTaskAsynchronously(LoginPlus.m, new Runnable() {
				@Override
				public void run() {
					if(MYSQLMethods.isInMYSQLTable(uuid)) {
						AccountObject ao;
						try {
							ao = new AccountObject(uuid, MYSQLMethods.getPassword(uuid), EncryptionType.valueOf(MYSQLMethods.getHashType(uuid)), MYSQLMethods.isPremium(uuid));
							if(MYSQLMethods.isInMYSQLTable(uuid)) {
								DataTranslator.accounts.put(uuid, ao);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}			
			});
		} else {
			AccountObject ao = new AccountObject(uuid, PasswordConfig.getHashedPassword(uuid), PasswordConfig.getHashtype(uuid), PasswordConfig.getPremium(uuid));
			DataTranslator.accounts.put(uuid, ao);
		}
	}

}
