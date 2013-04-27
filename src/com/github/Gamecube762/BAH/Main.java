package com.github.Gamecube762.BAH;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	

	@Override
	public void onEnable() {
		getLogger().info("it worked! =D");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Goodbye! D=");	
	}

	HashMap<Player, Location> BeforeMassTele = new HashMap<Player, Location>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labal, String[] args) {
		String cmdName = cmd.getName();
		String cmdPerm = cmd.getPermission();
		int PlayersOnline = Bukkit.getServer().getOnlinePlayers().length;
		boolean sIsPlayer = isSenderPlayer(sender);
		boolean sHasPerm = hasPermission(sender, cmdPerm);
		
		Player s = Bukkit.getServer().getPlayer(sender.getName());
		Location sLoc = sLocation(s); //gets their location, returns null if not player
			
		
		if(cmdName.equalsIgnoreCase("bringallhere")|cmdName.equalsIgnoreCase("bah")){
			if (!sIsPlayer) {sender.sendMessage(ChatColor.RED + "You haft to be a player to run this command! Try " + ChatColor.YELLOW + "BringAllTo <name>");return true;}
			if (!sHasPerm) {s.sendMessage(ChatColor.RED + "You don't have permission!");return true;}
				
			if (PlayersOnline <= 1){s.sendMessage(ChatColor.RED + "No one is online to teleport to you ='(");return true;}
				
				for(Player p: Bukkit.getServer().getOnlinePlayers()){
					if (!(p.hasPermission("bah.teleport.exempt")))
						BeforeMassTele.put(p, p.getLocation());
						p.teleport(sLoc);
						p.sendMessage(ChatColor.GOLD + "Teleported all to " + s.getName());
				}
				
				s.sendMessage(ChatColor.GOLD + "Teleported all to you!");
				getLogger().info(s.getName() + " teleported all to him/her");
				return true;
			

		} else if(cmdName.equalsIgnoreCase("bringallto")|cmdName.equalsIgnoreCase("bah")){
			if (!sHasPerm) {sender.sendMessage(ChatColor.RED + "You don't have permission!");return true;}
				
				if (args.length < 1) {sender.sendMessage(ChatColor.RED + "Needs a Player/Location to teleport to!"); return false;} else
				if ((args.length < 2)&(isInteger(args[0])|isDouble(args[0]))) {sender.sendMessage(ChatColor.RED + "Invalid Cordinants!"); return false;}
				
				Player Target = Bukkit.getServer().getPlayer(args[0]);
				
				if (isDouble(args[0])|isInteger(args[0])) {
					
					if (!sIsPlayer) {sender.sendMessage(ChatColor.RED + "Sorry, haft to be in game for this to work! Will be fixed later!");}
					
					if (args.length < 1) {sender.sendMessage(ChatColor.RED + "No argument for X"); return false;}
					if (!isInteger(args[0])|!isDouble(args[0])) {sender.sendMessage(ChatColor.RED + "Bad argument for x"); return false;}
					if (args.length < 2) {sender.sendMessage(ChatColor.RED + "No argument for Y"); return false;}
					if (!isInteger(args[1])|!isDouble(args[1])) {sender.sendMessage(ChatColor.RED + "Bad argument for Y"); return false;}
					if (args.length < 3) {sender.sendMessage(ChatColor.RED + "No argument for Z"); return false;}
					if (!isInteger(args[2])|!isDouble(args[2])) {sender.sendMessage(ChatColor.RED + "Bad argument for Z"); return false;}
					
					String world = null;
					if (args.length < 4) {world = " ";} else {world = args[3];}
					
					
					if (!sIsPlayer) {
					
						World w = getCmdWorld(sender, world);
						if (w==null) {return false;}
						double x = Double.parseDouble(args[0]);
						double y = Double.parseDouble(args[1]);
						double z = Double.parseDouble(args[2]);
						Location tLoc = new Location(w, x, y, z);
						getLogger().info(tLoc.toString());
						
						for(Player p: Bukkit.getServer().getOnlinePlayers()){
							if(!(p.hasPermission("bah.teleport.exempt"))){
								BeforeMassTele.put(p, p.getLocation());
								p.teleport(tLoc);
								p.sendMessage(ChatColor.GOLD+"Teleported everyone to " + tLoc.toString());}
						}
						sender.sendMessage(ChatColor.GOLD + "Teleported everyone to " + tLoc.toString() + "!");
						if (sIsPlayer) {getLogger().info(s.getName() + " teleported all to " +  tLoc.toString());}
						return true;
					
					} else if (sIsPlayer) {
						World w = getCmdWorld(sender, world);if (w==null) {return false;}
						double x = Double.parseDouble(args[0]);
						double y = Double.parseDouble(args[1]);
						double z = Double.parseDouble(args[2]);
						Location tLoc = new Location(w, x, y, z);
					
						for(Player p: Bukkit.getServer().getOnlinePlayers()){
							if(!(p.hasPermission("bah.teleport.exempt"))){
								BeforeMassTele.put(p, p.getLocation());
								p.teleport(tLoc);
								p.sendMessage(ChatColor.GOLD+"Teleported everyone to " + tLoc.toString());}
						}
						sender.sendMessage(ChatColor.GOLD + "Teleported everyone to " + tLoc.toString() + "!");
						if (sIsPlayer) {getLogger().info(s.getName() + " teleported all to " +  tLoc.toString());}
						return true;
					}
					return true;
					
				} else if (Target != null) {
					Location tLoc = Target.getLocation();
					
					for(Player p: Bukkit.getServer().getOnlinePlayers()){
						if(!(p.hasPermission("bah.teleport.exempt"))){
							BeforeMassTele.put(p, p.getLocation());
							p.teleport(tLoc);
							p.sendMessage(ChatColor.GOLD+"Teleported everyone to "+Target.getName());}
					}
					Target.sendMessage(ChatColor.GOLD + "Teleported everyone to you!");
					sender.sendMessage(ChatColor.GOLD + "Teleported everyone to " + Target.getName() + "!");
					if (sIsPlayer) {getLogger().info(s.getName() + " teleported all to " + Target.getName());}
					return true;
					
				} else {sender.sendMessage(ChatColor.RED + args[0] + " was not found");return true;}
				
				
				

		} else if (cmdName.equalsIgnoreCase("bringmeback")|cmdName.equalsIgnoreCase("bab")) {
			if (!sIsPlayer) {sender.sendMessage(ChatColor.RED + "You haft to be a player to run this command! Try " + ChatColor.YELLOW + "PutEveryoneBack" + ChatColor.RED + "or" + ChatColor.YELLOW + "PutAllBack");return true;}
			if (!sHasPerm) {s.sendMessage(ChatColor.RED + "You don't have permission!");return true;}

				if (BeforeMassTele.get(s)==null) {
					s.sendMessage("You were in no recent mass teleports!");
				} else {
					s.teleport(BeforeMassTele.get(s));
					BeforeMassTele.put(s, null);
					s.sendMessage(ChatColor.GOLD + "You were teleported back!");
				}
		} else if (cmdName.equalsIgnoreCase("puteveryoneback")|cmdName.equalsIgnoreCase("putallback")|cmdName.equalsIgnoreCase("peb")|cmdName.equalsIgnoreCase("pab")) {
			if (!sHasPerm) {s.sendMessage(ChatColor.RED + "You don't have permission!");return true;}
			
				for(Player p: Bukkit.getServer().getOnlinePlayers()) {
					if (BeforeMassTele.get(p)!=null) {
						p.teleport(BeforeMassTele.get(p));
						BeforeMassTele.put(p, null);
						p.sendMessage(ChatColor.GOLD + "You were teleported back!");
					}
				}
			sender.sendMessage(ChatColor.GOLD + "Put everyone back to where they once were!");
			return true;
		}
		return true;
	}

	public boolean isSenderPlayer(CommandSender sender) {if (sender instanceof Player) {return true;} else {return false;}}
	public boolean isCmdBlock(CommandSender sender) {if (sender instanceof CommandBlock) {return true;} else {return false;}}
	public Block returnCBBlock(BlockCommandSender sender) {if (sender instanceof CommandBlock) {return sender.getBlock();} else {return null;}}
	public boolean hasPermission(CommandSender sender, String cmdPerm) {if (!isSenderPlayer(sender)){return true;}else{return sender.hasPermission(cmdPerm);}}
	public Location sLocation(Player Sender) {if (isSenderPlayer(Sender)){return Sender.getLocation();}else{return null;}}
	public boolean isInteger(String s) {try {Integer.parseInt(s);} catch (NumberFormatException e) {return false;}return true;}
	public boolean isDouble(String s) {try {Double.parseDouble(s);} catch (NumberFormatException e) {return false;}return true;}
	public World getCmdWorld(CommandSender sender, String args) {if (args==" ") {if (sender instanceof Player) {return Bukkit.getServer().getPlayer(sender.getName()).getLocation().getWorld();} else {sender.sendMessage(ChatColor.RED + "Needs World Argument!");return null;}}World world = Bukkit.getServer().getWorld(args);if (world==null) {sender.sendMessage(ChatColor.RED + "unknown World!");return null;}return world;}
}
