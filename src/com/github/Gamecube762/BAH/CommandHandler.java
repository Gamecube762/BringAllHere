package com.github.Gamecube762.BAH;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.github.Gamecube762.BAH.Command.BringAllHere;
import com.github.Gamecube762.BAH.Command.BringAllTo;

public class CommandHandler implements CommandExecutor {
	
	private Plugin plugin;
	private Logger log;
	
	public CommandHandler(Main plugin) {
		this.plugin = plugin;
		log = this.plugin.getLogger();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labal, String[] args) {
		String cmdName = cmd.getName();
		String cmdPerm = cmd.getPermission();
		
		boolean isPlayer = Utils.isSenderPlayer(sender);
		boolean hasPerm = Utils.hasPermission(sender, cmdPerm);


		if (!hasPerm) {sender.sendMessage(ChatColor.RED + "You don't have permission!");return true;}
		
		if (cmdName.equalsIgnoreCase("BringAllHere")) {return BringAllHere.run(sender, isPlayer, args, this.log);}else
		if (cmdName.equalsIgnoreCase("BringAllTo")) {return BringAllTo.run(sender, isPlayer, args, this.log);}
		
		return false;
	}
}
