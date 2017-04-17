package de.yellowphoenix18.loginplus.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.yellowphoenix18.loginplus.utils.EncryptionType;

public class PasswordConfig {
	
	public static File f = new File("plugins/LoginPlus", "passwords.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
	public static void setPassword(String uuid, String password_hash, String hashtype) {
		cfg.set(uuid + ".Password", password_hash);
		cfg.set(uuid + ".EncryptionType", hashtype);
		save();
	}
	
	public static String getHashedPassword(String uuid) {
		return cfg.getString(uuid + ".Password");
	}
	
	public static EncryptionType getHashtype(String uuid) {
		return EncryptionType.valueOf(cfg.getString(uuid + ".EncryptionType"));
	}
	
	public static boolean isSet(String uuid) {
		if(cfg.contains(uuid + ".Password")) {
			return true;
		} else {
			return false;
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
