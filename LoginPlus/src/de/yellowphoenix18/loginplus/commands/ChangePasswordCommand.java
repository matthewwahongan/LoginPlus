package de.yellowphoenix18.loginplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.yellowphoenix18.loginplus.config.DataTranslator;
import de.yellowphoenix18.loginplus.config.MainConfig;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.utils.EncryptionUtils;
import de.yellowphoenix18.loginplus.utils.PluginUtils;
import de.yellowphoenix18.loginplus.versionutils.VersionUtils;

public class ChangePasswordCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				if(p.hasPermission("loginplus.*") || p.hasPermission("loginplus.changepw")) {
					VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_changepw_title, MessagesConfig.title_changepw_subtitle);
					PluginUtils.changepw.add(p);
				} else {
					p.sendMessage(MessagesConfig.prefix + MessagesConfig.no_permission);
				}
			} else if(args.length == 2) {
				if(p.hasPermission("loginplus.*")) {
					Player k = Bukkit.getPlayerExact(args[0]);
					if(k != null) {
						DataTranslator.setPassword(k.getUniqueId().toString(), EncryptionUtils.hashPassword(args[1], MainConfig.type), MainConfig.type.toString());
					} else {
						p.sendMessage(MessagesConfig.prefix + MessagesConfig.player_offline);
					}
				}
			} else {
				p.sendMessage(MessagesConfig.prefix + MessagesConfig.wrong_arguments);
			}
		} else {
			if(sender instanceof ConsoleCommandSender) {
				if(args.length == 2) {
					Player k = Bukkit.getPlayerExact(args[0]);
					if(k != null) {
						DataTranslator.setPassword(k.getUniqueId().toString(), EncryptionUtils.hashPassword(args[1], MainConfig.type), MainConfig.type.toString());
					} else {
						System.out.println("[LoginPlus] The player is not online!");
					}					
				} else {
					System.out.println("[LoginPlus] Wrong arguments");
				}
			}
		}
		
		return true;
	}

}
