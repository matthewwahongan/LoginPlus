package de.yellowphoenix18.loginplus.utils;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import de.yellowphoenix18.loginplus.LoginPlus;
import de.yellowphoenix18.loginplus.commands.ChangePasswordCommand;
//import de.yellowphoenix18.loginplus.commands.PremiumCommand;
import de.yellowphoenix18.loginplus.config.DataTranslator;
import de.yellowphoenix18.loginplus.config.MYSQLConfig;
import de.yellowphoenix18.loginplus.config.MYSQLMethods;
import de.yellowphoenix18.loginplus.config.MainConfig;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.config.PasswordConfig;
import de.yellowphoenix18.loginplus.listener.BlockListener;
import de.yellowphoenix18.loginplus.listener.ChatListener;
import de.yellowphoenix18.loginplus.listener.CommandListener;
import de.yellowphoenix18.loginplus.listener.InteractListener;
import de.yellowphoenix18.loginplus.listener.InventoryListener;
import de.yellowphoenix18.loginplus.listener.JoinListener;
import de.yellowphoenix18.loginplus.listener.LoginListener;
import de.yellowphoenix18.loginplus.listener.MoveListener;
import de.yellowphoenix18.yellowphoenix18stats.YellowPhoenix18Stats;

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
		//This section is under Copyright, dont remove this Logo!
		System.out.println("   __             _         ___ _           ");
		System.out.println("  / /  ___   __ _(_)_ __   / _ \\ |_   _ ___ ");
		System.out.println(" / /  / _ \\ / _` | | '_ \\ / /_)/ | | | / __|");
		System.out.println("/ /__| (_) | (_| | | | | / ___/| | |_| \\__ \\");
		System.out.println("\\____/\\___/ \\__, |_|_| |_\\/    |_|\\__,_|___/");
		System.out.println("            |___/                           ");	
		System.out.println("                                            ");		
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		sender.sendMessage("§eThank you for using LoginPlus by YellowPhoenix18");
		
		version = getServerVersion();
		if(!version.equalsIgnoreCase("v1_8_R1") && !version.equalsIgnoreCase("v1_8_R2") && !version.equalsIgnoreCase("v1_8_R3") && !version.equalsIgnoreCase("v1_9_R1") &&
		   !version.equalsIgnoreCase("v1_9_R2") && !version.equalsIgnoreCase("v1_10_R1") && !version.equalsIgnoreCase("v1_11_R1") && !version.equalsIgnoreCase("v1_12_R1")) {
			System.out.println("[LoginPlus] Your Spigot-Version is not supported, disabling LoginPlus...");
			Bukkit.getPluginManager().disablePlugin(LoginPlus.m);
		} else {
			loadConfigs();
			loadTimer();
			loadListener();
			loadCommands();
			loadMYSQL();
			if(MYSQLConfig.enabled) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(LoginPlus.m, new Runnable() {
					@Override
					public void run() {
						loadPlayerData();
					}				
				}, 20);
			} else {
				loadPlayerData();
			}
			new YellowPhoenix18Stats("sw9Z6c1f", LoginPlus.m);	
		}
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
	
	public static void loadPlayerData() {
		for(Player all : Bukkit.getOnlinePlayers()) {
			String uuid = all.getUniqueId().toString();
			if(MYSQLConfig.enabled) {
				Bukkit.getScheduler().runTaskAsynchronously(LoginPlus.m, new Runnable() {
					@Override
					public void run() {
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
				});
			} else {
				AccountObject ao = new AccountObject(uuid, PasswordConfig.getHashedPassword(uuid), PasswordConfig.getHashtype(uuid), PasswordConfig.getPremium(uuid));
				DataTranslator.accounts.put(uuid, ao);
			}
		}
	}
	
	public static void loadMYSQL() {
		if(MYSQLConfig.enabled) {
			Bukkit.getScheduler().runTaskAsynchronously(LoginPlus.m, new Runnable() {
				@Override
				public void run() {				
					MYSQL.connect(MYSQLConfig.address, MYSQLConfig.port, MYSQLConfig.database, MYSQLConfig.username, MYSQLConfig.password);
				
					try {
						Statement st = MYSQL.con.createStatement();
						st.executeUpdate("CREATE TABLE IF NOT EXISTS `UserData`(`UUID` varchar(100),`Password` varchar(512),`HashType` varchar(10),`Premium` boolean)");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}		
			});
		}
	}
	
	public static void loadCommands() {
		LoginPlus.m.getCommand("changepw").setExecutor(new ChangePasswordCommand());
		//LoginPlus.m.getCommand("premium").setExecutor(new PremiumCommand());
	}
	
	public static void loadConfigs() {
		MessagesConfig.load();
		MainConfig.load();
		MYSQLConfig.load();
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
