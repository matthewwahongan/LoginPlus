package de.yellowphoenix18.loginplus;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.yellowphoenix18.loginplus.config.MYSQLConfig;
import de.yellowphoenix18.loginplus.utils.MYSQL;
import de.yellowphoenix18.loginplus.utils.PluginUtils;

public class LoginPlus extends JavaPlugin {
	
	public static LoginPlus m;
	
	public void onEnable() {
		m = this;
		PluginUtils.setUp();
	}
	
	public void onDisable() {
		for(Player all : PluginUtils.login) {
			all.kickPlayer("");
		}
		for(Player all : PluginUtils.register) {
			all.kickPlayer("");
		}
		for(Player all : PluginUtils.captcha) {
			all.kickPlayer("");
		}
		if(MYSQLConfig.enabled) {
			MYSQL.disconnect();
		}
	}

}
