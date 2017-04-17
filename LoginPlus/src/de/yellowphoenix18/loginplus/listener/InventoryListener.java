package de.yellowphoenix18.loginplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.yellowphoenix18.loginplus.utils.PluginUtils;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void on(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(PluginUtils.login.contains(p) || PluginUtils.register.contains(p)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void on(PlayerPickupItemEvent e) {
		Player p = (Player) e.getPlayer();
		if(PluginUtils.login.contains(p) || PluginUtils.register.contains(p)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void on(PlayerDropItemEvent e) {
		Player p = (Player) e.getPlayer();
		if(PluginUtils.login.contains(p) || PluginUtils.register.contains(p)) {
			e.setCancelled(true);
		}
	}

}
