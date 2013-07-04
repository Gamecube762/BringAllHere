package com.github.Gamecube762.BAH.Command;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.Gamecube762.BAH.Utils;

public class BringWorldHere {
	
	private static String sisterCmdName = "BringWorldTo";
	private static String notPlayer = ChatColor.RED+"You need to be a player to do this! Try "+ChatColor.YELLOW+"/"+sisterCmdName;
	private static String tellVictum = ChatColor.GOLD+"Everyone in %world have been teleported to ";
	private static String tellLog = "Teleported everyone in %world to ";
	
	public static boolean run(CommandSender sender, boolean isPlayer, String[] args, Logger log) {
		
		if (!isPlayer){sender.sendMessage(notPlayer);return false;}
		if (args.length < 1) {sender.sendMessage(ChatColor.RED+"Needs a World argument");return false;}
		
		Player p = (Player)sender;
		Location to = p.getLocation();
		
		World world = Utils.getCmdWorld(sender, args[0]); if (world==null){return true;}//it failed and the error message was allready sent
		
		for (Player v : world.getPlayers()){
			if( !p.hasPermission(Utils.exemptPerm) | !v.getDisplayName().equalsIgnoreCase(p.getDisplayName()) ){
				v.teleport(to);
				v.sendMessage( (tellVictum+p.getDisplayName()).replace("%world", to.getWorld().getName()) );
			}
		}
		log.info( (tellLog+p.getDisplayName()+" "+Utils.locToString(to)).replace("%world", to.getWorld().getName()) );
		return true;
	}
}
