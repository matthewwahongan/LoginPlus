package de.yellowphoenix18.loginplus.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.yellowphoenix18.loginplus.config.MessagesConfig;

public class CaptchaUtils {
	
	public static HashMap<Player, Integer> captcha_parts = new HashMap<Player, Integer>();
	
	public static Inventory createCaptchaInventory(Player p) {
		Random rnd = new Random();
		int captcha_items = rnd.nextInt(5)+4;
		captcha_parts.put(p, captcha_items);
		
		Inventory inv = Bukkit.createInventory(null, 27, MessagesConfig.captcha_name);
		
		for(int i = 0; i < 27; i++) {
			inv.setItem(i, ItemUtils.DyeCreator(MessagesConfig.captcha_dont_click, null, null, 1, DyeColor.GRAY));
		}
		List<String> slots = new ArrayList<String>();
		
		while(captcha_items > 0) {
			captcha_items--;
			int slot = rnd.nextInt(27);
			while(slots.contains(slot + "")) {
				slot = rnd.nextInt(27);
			}
			slots.add(slot + "");
			inv.setItem(slot, ItemUtils.DyeCreator(MessagesConfig.captcha_change, null, null, 1, DyeColor.RED));
		}
		
		return inv;
	}

}
