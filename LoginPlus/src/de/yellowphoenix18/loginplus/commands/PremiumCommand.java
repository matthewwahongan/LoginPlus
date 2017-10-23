package de.yellowphoenix18.loginplus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.yellowphoenix18.loginplus.config.DataTranslator;
import de.yellowphoenix18.loginplus.config.MessagesConfig;
import de.yellowphoenix18.loginplus.utils.PremiumCheck;

public class PremiumCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			String uuid = p.getUniqueId().toString();
			if(p.hasPermission("loginplus.*") || p.hasPermission("loginplus.premium")) {
				if(DataTranslator.getAccountData(uuid).getPremium() == false) {
					if(PremiumCheck.isPremium(p)) {
						DataTranslator.setPremium(uuid, true);
						p.sendMessage(MessagesConfig.prefix + MessagesConfig.premium_enabled);
					} else {
						p.sendMessage(MessagesConfig.prefix + MessagesConfig.no_premium);
					}
				} else {
					DataTranslator.setPremium(uuid, false);
					p.sendMessage(MessagesConfig.prefix + MessagesConfig.premium_disabled);
				}
			} else {
				p.sendMessage(MessagesConfig.prefix + MessagesConfig.no_permission);
			}
		} else {
			System.out.println("[LoginPlus] This Command can only be executed by Players");
		}
		
		return true;
	}

}
