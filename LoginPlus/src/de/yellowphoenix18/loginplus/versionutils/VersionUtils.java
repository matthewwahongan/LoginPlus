package de.yellowphoenix18.loginplus.versionutils;

import org.bukkit.entity.Player;

import de.yellowphoenix18.loginplus.utils.PluginUtils;

public class VersionUtils {
	
	public static void sendTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		if(PluginUtils.version.equalsIgnoreCase("v1_8_R1")) {
			Version_1_8_R1.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R2")) {
			Version_1_8_R2.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R3")) {
			Version_1_8_R3.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R1")) {
			Version_1_9_R1.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R2")) {
			Version_1_9_R2.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_10_R1")) {
			Version_1_10_R1.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_11_R1")) {
			Version_1_11_R1.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_12_R1")) {
			Version_1_12_R1.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
		} else {
			System.out.println("[LoginPlus] VersionUtils.sendTitle is not supported by your Spigot-Version!");
		}
    }


    public static void sendActionBar(Player p, String msg) {
		if(PluginUtils.version.equalsIgnoreCase("v1_8_R1")) {
			Version_1_8_R1.sendActionBar(p, msg);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R2")) {
			Version_1_8_R2.sendActionBar(p, msg);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R3")) {
			Version_1_8_R3.sendActionBar(p, msg);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R1")) {
			Version_1_9_R1.sendActionBar(p, msg);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R2")) {
			Version_1_9_R2.sendActionBar(p, msg);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_10_R1")) {
			Version_1_10_R1.sendActionBar(p, msg);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_11_R1")) {
			Version_1_11_R1.sendActionBar(p, msg);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_12_R1")) {
			Version_1_12_R1.sendActionBar(p, msg);
		} else {
			System.out.println("[LoginPlus] VersionUtils.sendActionBar is not supported by your Spigot-Version!");
		}
    }
}
