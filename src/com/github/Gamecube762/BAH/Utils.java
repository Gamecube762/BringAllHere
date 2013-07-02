package com.github.Gamecube762.BAH;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Utils {
	
	public static String exemptPerm = "bah.teleport.exempt";
	
	public static String locToString(Location loc){return "["+loc.getWorld().getName()+", "+loc.getX()+", "+loc.getY()+", "+loc.getZ()+"]";}

	public static boolean checkIsInteger(String s) {try {Integer.parseInt(s);} catch (NumberFormatException e) {return false;}return true;}
	
	public static boolean isSenderPlayer(CommandSender sender) {if (sender instanceof Player) {return true;} else {return false;}}
	
	public static boolean isCmdBlock(CommandSender sender) {if (sender instanceof CommandBlock) {return true;} else {return false;}}
	
	public static Block returnCBBlock(BlockCommandSender sender) {if (sender instanceof CommandBlock) {return sender.getBlock();} else {return null;}}
	
	public static boolean hasPermission(CommandSender sender, String cmdPerm) {if (!isSenderPlayer(sender)){return true;}else{return sender.hasPermission(cmdPerm);}}
	
	public static Location sLocation(Player Sender) {if (isSenderPlayer(Sender)){return Sender.getLocation();}else{return null;}}
	
	public static boolean isInteger(String s) {try {Integer.parseInt(s);} catch (NumberFormatException e) {return false;}return true;}
	
	public static boolean isDouble(String s) {try {Double.parseDouble(s);} catch (NumberFormatException e) {return false;}return true;}
	
	public static World getCmdWorld(CommandSender sender, String args) {if (args==" ") {if (sender instanceof Player) {return Bukkit.getServer().getPlayer(sender.getName()).getLocation().getWorld();} else {sender.sendMessage(ChatColor.RED + "Needs World Argument!");return null;}}World world = Bukkit.getServer().getWorld(args);if (world==null) {sender.sendMessage(ChatColor.RED + "unknown World!");return null;}return world;}


	
}
