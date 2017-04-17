package de.yellowphoenix18.loginplus.versionutils;

import org.bukkit.entity.Player;

import de.yellowphoenix18.loginplus.utils.PluginUtils;

public class VersionUtils {
	
	public static int getPing(Player p) {
		if(PluginUtils.version.equalsIgnoreCase("v1_8_R1")) {
			return Version_1_8_R1.getPing(p);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R2")) {
			return Version_1_8_R2.getPing(p);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R3")) {
			return Version_1_8_R3.getPing(p);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R1")) {
			return Version_1_9_R1.getPing(p);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R2")) {
			return Version_1_9_R2.getPing(p);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_10_R1")) {
			return Version_1_10_R1.getPing(p);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_11_R1")) {
			return Version_1_11_R1.getPing(p);
		} else {
			System.out.println("[VersionAPI] VersionUtils.getPing is not supported by your Spigot-Version!");
			return 1;
		}		
	}
	
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
		} else {
			System.out.println("[VersionAPI] VersionUtils.sendTitle is not supported by your Spigot-Version!");
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
		} else {
			System.out.println("[VersionAPI] VersionUtils.sendActionBar is not supported by your Spigot-Version!");
		}
    }


    public static void sendHeaderAndFooter(Player p, String head, String foot) {
		if(PluginUtils.version.equalsIgnoreCase("v1_8_R1")) {
			Version_1_8_R1.sendHeaderAndFooter(p, head, foot);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R2")) {
			Version_1_8_R2.sendHeaderAndFooter(p, head, foot);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_8_R3")) {
			Version_1_8_R3.sendHeaderAndFooter(p, head, foot);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R1")) {
			Version_1_9_R1.sendHeaderAndFooter(p, head, foot);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_9_R2")) {
			Version_1_9_R2.sendHeaderAndFooter(p, head, foot);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_10_R1")) {
			Version_1_10_R1.sendHeaderAndFooter(p, head, foot);
		} else if(PluginUtils.version.equalsIgnoreCase("v1_11_R1")) {
			Version_1_11_R1.sendHeaderAndFooter(p, head, foot);
		} else {
			System.out.println("[VersionAPI] VersionUtils.sendHeaderAndFooter is not supported by your Spigot-Version!");
		}
    }
}
