package de.yellowphoenix18.loginplus.config;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;

import de.yellowphoenix18.loginplus.LoginPlus;
import de.yellowphoenix18.loginplus.utils.AccountObject;
import de.yellowphoenix18.loginplus.utils.EncryptionType;

public class DataTranslator {
	
	public static HashMap<String, AccountObject> accounts = new HashMap<String, AccountObject>();
	
	public static void setPremium(String uuid, boolean premium) {
		if(MYSQLConfig.enabled == true) {
			if(accounts.containsKey(uuid)) {
				AccountObject ao = accounts.get(uuid);
				Bukkit.getScheduler().runTaskAsynchronously(LoginPlus.m, new Runnable() {
					@Override
					public void run() {
						try {
							MYSQLMethods.changeAccount(uuid, ao.getPassword(), ao.getHashType().toString(), premium);
						} catch (SQLException e) {							
							e.printStackTrace();
						}
					}			
				});
				ao.setPremium(premium);
				accounts.put(uuid, ao);
			} else {
				System.out.println("[LoginPlus] Error no Data found!");
			}
		} else {
			PasswordConfig.setPremium(uuid, premium);
		}
	}
	
	public static void setPassword(String uuid, String password, String hashtype) {
		if(MYSQLConfig.enabled == true) {
			if(accounts.containsKey(uuid)) {
				AccountObject ao = accounts.get(uuid);
				Bukkit.getScheduler().runTaskAsynchronously(LoginPlus.m, new Runnable() {
					@Override
					public void run() {
						try {
							MYSQLMethods.changeAccount(uuid, password, hashtype, ao.getPremium());
						} catch (SQLException e) {							
							e.printStackTrace();
						}
					}			
				});
				ao.setHashType(EncryptionType.valueOf(hashtype));
				ao.setPassword(password);
				accounts.put(uuid, ao);
			} else {
				System.out.println("[LoginPlus] Error no Data found!");
			}
		} else {
			PasswordConfig.setPassword(uuid, password, hashtype.toString());
		}
	}
	
	public static AccountObject getAccountData(String uuid) {
		if(accounts.containsKey(uuid)) {
			return accounts.get(uuid);
		} else {
			return null;
		}
	}

}
