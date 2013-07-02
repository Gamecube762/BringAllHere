package com.github.Gamecube762.BAH.Command;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.Gamecube762.BAH.Utils;

public class BringAllTo {
	
	private static String sisterCmdName = "BringAllTo";
	private static String notPlayer = ChatColor.RED+"You need to be a player to do this! Try "+ChatColor.YELLOW+"/"+sisterCmdName;
	private static String tellVictum = ChatColor.GOLD+"You have been teleported to ";
	private static String tellLog = "Teleported everyone to ";
	
	public static boolean run(CommandSender sender, boolean isPlayer, String[] args, Logger log) {
		if (!isPlayer){sender.sendMessage(notPlayer);return false;}
		boolean a = false; if(args.length > 0){a=true;}
		Player p = (Player)sender;
		Location to = p.getLocation();
		
		String tellV = tellVictum+p.getDisplayName();
		String tellL = tellLog+p.getDisplayName();
		
		if (a) {
			Player To = Bukkit.getServer().getPlayer(args[0]);
			
			if ( To!=null ){
				
				to = Bukkit.getServer().getPlayer(args[0]).getLocation();
				tellV = tellVictum+To.getDisplayName();
				tellL = tellLog+To.getDisplayName()+" "+Utils.locToString(to);
				
			} else {
				if (args.length < 2) {sender.sendMessage(ChatColor.RED+"Need Argument for Y");return false;}
				if (args.length < 3) {sender.sendMessage(ChatColor.RED+"Need Argument for Z");return false;}
				
				if ( !Utils.isInteger(args[0]) | !Utils.isDouble(args[0])){sender.sendMessage(ChatColor.RED+"Bad Argument for X");return false;}
				if ( !Utils.isInteger(args[1]) | !Utils.isDouble(args[0])){sender.sendMessage(ChatColor.RED+"Bad Argument for Y");return false;}
				if ( !Utils.isInteger(args[2]) | !Utils.isDouble(args[0])){sender.sendMessage(ChatColor.RED+"Bad Argument for Z");return false;}
				
				World w; if (args.length > 2) {w = Utils.getCmdWorld(sender, args[3]);if(w==null){return false;}} else {w = p.getWorld();}
				double x = Double.parseDouble(args[0]);
				double y = Double.parseDouble(args[1]);
				double z = Double.parseDouble(args[2]);
				
				to = new Location(w, x, y, z);
				tellV = tellVictum+Utils.locToString(to);
				tellL = tellLog+Utils.locToString(to);
			}
		}
		
		for (Player v : Bukkit.getOnlinePlayers()){
			if( !p.hasPermission(Utils.exemptPerm) | !v.getDisplayName().equalsIgnoreCase(p.getDisplayName()) ){
				v.teleport(to);
				v.sendMessage(tellV);
			}
		}
		log.info(tellL);
		return true;
	}
}
