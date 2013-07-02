package com.github.Gamecube762.BAH.Command;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.Gamecube762.BAH.Utils;

public class BringAllHere {
	
	private static String sisterCmdName = "BringAllTo";
	private static String notPlayer = ChatColor.RED+"You need to be a player to do this! Try "+ChatColor.YELLOW+"/"+sisterCmdName;
	private static String tellVictum = ChatColor.GOLD+"You have been teleported to ";
	private static String tellLog = "Teleported everyone to ";
	
	public static boolean run(CommandSender sender, boolean isPlayer, String[] args, Logger log) {
		if (!isPlayer){sender.sendMessage(notPlayer);return false;}
		Player p = (Player)sender;
		Location to = p.getLocation();
		
		for (Player v : Bukkit.getOnlinePlayers()){
			if( !p.hasPermission(Utils.exemptPerm) | !v.getDisplayName().equalsIgnoreCase(p.getDisplayName()) ){
				v.teleport(to);
				v.sendMessage(tellVictum+p.getDisplayName());
			}
		}
		log.info(tellLog+p.getDisplayName()+" "+Utils.locToString(to));
		return true;
	}
}
