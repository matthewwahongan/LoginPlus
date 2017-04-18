package de.yellowphoenix18.loginplus.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import de.yellowphoenix18.loginplus.LoginPlus;
import de.yellowphoenix18.loginplus.commands.ChangePasswordCommand;
import de.yellowphoenix18.loginplus.config.MainConfig;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.listener.BlockListener;
import de.yellowphoenix18.loginplus.listener.ChatListener;
import de.yellowphoenix18.loginplus.listener.CommandListener;
import de.yellowphoenix18.loginplus.listener.InteractListener;
import de.yellowphoenix18.loginplus.listener.InventoryListener;
import de.yellowphoenix18.loginplus.listener.JoinListener;
import de.yellowphoenix18.loginplus.listener.LoginListener;
import de.yellowphoenix18.loginplus.listener.MoveListener;

public class PluginUtils {
	
	
	public static List<Player> login = new ArrayList<Player>();
	public static List<Player> captcha = new ArrayList<Player>();
	public static List<Player> changepw = new ArrayList<Player>();
	public static List<Player> kick = new ArrayList<Player>();
	public static List<Player> register = new ArrayList<Player>();
	public static List<Player> timer_rem = new ArrayList<Player>();
	public static HashMap<Player, Integer> timer = new HashMap<Player, Integer>();
	public static HashMap<String, Integer> banned_ips = new HashMap<String, Integer>();
	public static HashMap<Player, Integer> attempts = new HashMap<Player, Integer>();
	public static int timer_c;
	public static String version = "";
	
	public static void setUp() {
		version = getServerVersion();
		if(!version.equalsIgnoreCase("v1_8_R1") && !version.equalsIgnoreCase("v1_8_R2") && !version.equalsIgnoreCase("v1_8_R3") && !version.equalsIgnoreCase("v1_9_R1") &&
		   !version.equalsIgnoreCase("v1_9_R2") && !version.equalsIgnoreCase("v1_10_R1") && !version.equalsIgnoreCase("v1_11_R1")) {
			System.out.println("[LoginPlus] Your Spigot-Version is not supported, disabling LoginPlus...");
			Bukkit.getPluginManager().disablePlugin(LoginPlus.m);
		}
		
		loadConfigs();
		loadTimer();
		loadListener();
		loadCommands();
	}
	
	public static String getServerVersion() {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		return version;
	}
	
	public static void loadListener() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new JoinListener(), LoginPlus.m);
		pm.registerEvents(new LoginListener(), LoginPlus.m);
		pm.registerEvents(new MoveListener(), LoginPlus.m);
		pm.registerEvents(new ChatListener(), LoginPlus.m);
		pm.registerEvents(new BlockListener(), LoginPlus.m);
		pm.registerEvents(new CommandListener(), LoginPlus.m);
		pm.registerEvents(new InteractListener(), LoginPlus.m);
		pm.registerEvents(new InventoryListener(), LoginPlus.m);
	}
	
	public static void loadCommands() {
		LoginPlus.m.getCommand("changepw").setExecutor(new ChangePasswordCommand());
	}
	
	public static void loadConfigs() {
		MessagesConfig.load();
		MainConfig.load();
	}
	
	public static void loadTimer() {
		timer_c = Bukkit.getScheduler().scheduleSyncRepeatingTask(LoginPlus.m, new Runnable() {
			@Override
			public void run() {
				for(Player all : timer.keySet()) {
					timer.put(all, timer.get(all)+1);
					if(timer.get(all) > MainConfig.timer_time) {
						timer_rem.add(all);
					}
				}
				for(Player all : kick) {
					if(MainConfig.login_failed_commands_enabled == true) {
						for(String command : MainConfig.login_failed_commands) {
							Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%Player%", all.getName()));
						}
					}
					if(MainConfig.login_failed_kick == true) {
						all.kickPlayer(MessagesConfig.prefix + MessagesConfig.ban_inform.replace("%Time%", MainConfig.login_failed_ban_time + ""));
					}
				}
				kick.clear();
				for(String banned : banned_ips.keySet()) {
					banned_ips.put(banned, banned_ips.get(banned)-1);
					if(banned_ips.get(banned) <= 0) {
						banned_ips.remove(banned);
					}
				}
				for(Player all : timer_rem) {
					all.kickPlayer(MessagesConfig.prefix + MessagesConfig.timer_over.replace("%Reconnect-Time%", MainConfig.reconnect_time + ""));
					String ip = all.getAddress().getAddress().toString().replace("/", "");
					if(MainConfig.reconnect_time_enabled == true) {
						banned_ips.put(ip, MainConfig.reconnect_time);
					}
					timer.remove(all);
				}
				timer_rem.clear();
			}	
		}, 15, 20);
	}

}
