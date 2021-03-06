package de.yellowphoenix18.loginplus.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.yellowphoenix18.loginplus.LoginPlus;
import de.yellowphoenix18.loginplus.config.DataTranslator;
import de.yellowphoenix18.loginplus.config.MainConfig;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.utils.CaptchaUtils;
import de.yellowphoenix18.loginplus.utils.PluginUtils;
import de.yellowphoenix18.loginplus.utils.PremiumCheck;
import de.yellowphoenix18.loginplus.versionutils.VersionUtils;

public class JoinListener implements Listener {
	
	@EventHandler
	public void on(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PluginUtils.captcha.add(p);
		String uuid = p.getUniqueId().toString();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(LoginPlus.m, new Runnable() {
			@Override
			public void run() {
				PluginUtils.captcha.remove(p);
				if(DataTranslator.accounts.containsKey(uuid)) {
					if(MainConfig.captcha == true && MainConfig.captcha_on_login) {
						PluginUtils.captcha.add(p);
						Bukkit.getScheduler().scheduleSyncDelayedTask(LoginPlus.m, new Runnable() {
							@Override
							public void run() {
								p.openInventory(CaptchaUtils.createCaptchaInventory(p));
							}		
						}, 1);
					} else {
						if(DataTranslator.getAccountData(uuid).getPremium()) {
							if(!PremiumCheck.isPremium(p)) {
								p.kickPlayer(MessagesConfig.prefix + MessagesConfig.no_premium);
							}
							PluginUtils.login.remove(p);
							PluginUtils.timer.remove(p);
						} else {
							VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_login_title, MessagesConfig.title_login_subtitle);
							PluginUtils.login.add(p);
							if(MainConfig.timer_enabled) {
								PluginUtils.timer.put(p, 0);
							}
							PluginUtils.attempts.put(p, MainConfig.login_attempts);
						}
					}
				} else {
					if(MainConfig.captcha == true && MainConfig.captcha_on_register) {
						PluginUtils.captcha.add(p);
						Bukkit.getScheduler().scheduleSyncDelayedTask(LoginPlus.m, new Runnable() {
							@Override
							public void run() {
								p.openInventory(CaptchaUtils.createCaptchaInventory(p));
							}		
						}, 1);
					} else {
						VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_register_title, MessagesConfig.title_register_subtitle);
						PluginUtils.register.add(p);
					}
				}
			}			
		});
	}

}
