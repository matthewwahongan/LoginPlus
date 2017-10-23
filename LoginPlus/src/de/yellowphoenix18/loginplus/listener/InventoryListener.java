package de.yellowphoenix18.loginplus.listener;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.yellowphoenix18.loginplus.LoginPlus;
import de.yellowphoenix18.loginplus.config.MainConfig;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.config.PasswordConfig;
import de.yellowphoenix18.loginplus.utils.CaptchaUtils;
import de.yellowphoenix18.loginplus.utils.ItemUtils;
import de.yellowphoenix18.loginplus.utils.PluginUtils;
import de.yellowphoenix18.loginplus.utils.PremiumCheck;
import de.yellowphoenix18.loginplus.versionutils.VersionUtils;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void on(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(PluginUtils.login.contains(p) || PluginUtils.register.contains(p) || PluginUtils.captcha.contains(p)) {
			if(!e.getInventory().getName().equalsIgnoreCase(MessagesConfig.captcha_name)) {
				e.setCancelled(true);
			} else {
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().hasItemMeta()) {
						if(e.getCurrentItem().getItemMeta().hasDisplayName()) {
							String disp = e.getCurrentItem().getItemMeta().getDisplayName();
							if(disp.equalsIgnoreCase(MessagesConfig.captcha_dont_click)) {
								CaptchaUtils.captcha_parts.remove(p);
								p.kickPlayer(MessagesConfig.prefix + MessagesConfig.captcha_failed);
							} else if(disp.equalsIgnoreCase(MessagesConfig.captcha_change)) {
								int slot = e.getSlot();
								e.getInventory().setItem(slot, ItemUtils.DyeCreator(MessagesConfig.captcha_changed, null, null, 1, DyeColor.LIME));
								CaptchaUtils.captcha_parts.put(p, CaptchaUtils.captcha_parts.get(p)-1);
								if(CaptchaUtils.captcha_parts.get(p) <= 0) {
									PluginUtils.captcha.remove(p);
									p.closeInventory();
									if(PasswordConfig.isSet(p.getUniqueId().toString())) {
										if(PasswordConfig.getPremium(p.getUniqueId().toString())) {
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
									} else {
										VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_register_title, MessagesConfig.title_register_subtitle);
										PluginUtils.register.add(p);
									}
								}
							}
						}
					}
				}
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void on(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(PluginUtils.captcha.contains(p)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(LoginPlus.m, new Runnable() {
				@Override
				public void run() {
					p.openInventory(CaptchaUtils.createCaptchaInventory(p));
				}	
			}, 1);
		}
	}
	
	@EventHandler
	public void on(PlayerPickupItemEvent e) {
		Player p = (Player) e.getPlayer();
		if(PluginUtils.login.contains(p) || PluginUtils.register.contains(p) || PluginUtils.captcha.contains(p)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void on(PlayerDropItemEvent e) {
		Player p = (Player) e.getPlayer();
		if(PluginUtils.login.contains(p) || PluginUtils.register.contains(p) || PluginUtils.captcha.contains(p)) {
			e.setCancelled(true);
		}
	}

}
