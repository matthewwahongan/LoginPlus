package de.yellowphoenix18.loginplus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.utils.PluginUtils;
import de.yellowphoenix18.loginplus.versionutils.VersionUtils;

public class ChangePasswordCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("loginplus.*") || p.hasPermission("loginplus.changepw")) {
				VersionUtils.sendTitle(p, 20, 100, 20, MessagesConfig.title_changepw_title, MessagesConfig.title_changepw_subtitle);
				PluginUtils.changepw.add(p);
			}	
		} else {
			System.out.println("[LoginPlus] This Command can only be executed by Players");
		}
		
		return true;
	}

}
