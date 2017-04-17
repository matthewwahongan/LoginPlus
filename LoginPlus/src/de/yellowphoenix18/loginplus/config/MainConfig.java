package de.yellowphoenix18.loginplus.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.yellowphoenix18.loginplus.utils.EncryptionType;

public class MainConfig {
	
	public static File f = new File("plugins/LoginPlus", "config.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
	public static boolean timer_enabled = true;
	public static int timer_time = 60;
	public static EncryptionType type = EncryptionType.SHA512;
	public static int login_attempts = 3;
	public static boolean reconnect_time_enabled = true;
	public static int reconnect_time = 30;
	public static boolean login_failed_ban = true;
	public static boolean login_failed_kick = true;
	public static int login_failed_ban_time = 120;
	public static boolean login_failed_commands_enabled = false;
	public static List<String> login_failed_commands = new ArrayList<String>();
	
	public static boolean login_captcha = false;
	
	
	public static void load() {
		login_failed_commands.add("deop %Player%");
		login_failed_commands.add("kick %Player%");
		
		
		timer_enabled = setObject("Timer.Enabled", timer_enabled);
		timer_time = setObject("Timer.Time", timer_time);
		type = EncryptionType.valueOf(setObject("Encryption.Type", type.toString()));
		login_attempts = setObject("Login.Max-Attempts", login_attempts);
		login_failed_ban = setObject("Login.Ban.Enabled", login_failed_ban);
		login_failed_kick = setObject("Login.Kick.Enabled", login_failed_kick);
		login_failed_ban_time = setObject("Login.Ban.Time", login_failed_ban_time);
		login_failed_commands_enabled = setObject("Login.Commands.Enabled", login_failed_commands_enabled);
		login_failed_commands = setObject("Login.Commands.Commands", login_failed_commands);
		reconnect_time_enabled = setObject("Reconnect-Time.Enabled", reconnect_time_enabled);
		reconnect_time = setObject("Reconnect-Time.Time", reconnect_time);
		login_captcha = setObject("Captcha.Enabled", login_captcha);
	}

	public static int setObject(String path, int obj) {
		if(cfg.contains(path)) {
			return cfg.getInt(path);
		} else {
			cfg.set(path, obj);
			save();
			return obj;
		}
	}
	
	public static String setObject(String path, String obj) {
		if(cfg.contains(path)) {
			return cfg.getString(path);
		} else {
			cfg.set(path, obj);
			save();
			return obj;
		}
	}
	
	public static boolean setObject(String path, boolean obj) {
		if(cfg.contains(path)) {
			return cfg.getBoolean(path);
		} else {
			cfg.set(path, obj);
			save();
			return obj;
		}
	}
	
	public static List<String> setObject(String path, List<String> obj) {
		if(cfg.contains(path)) {
			return cfg.getStringList(path);
		} else {
			cfg.set(path, obj);
			save();
			return obj;
		}
	}
	
	public static void save() {
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
