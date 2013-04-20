package com.github.Gamecube762.BAH;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
		boolean sIsPlayer = isPlayer(sender);
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
				
				if (!(args.length > 0)) {sender.sendMessage(ChatColor.RED + "Needs a Player to teleport to!"); return true;}
				Player Target = Bukkit.getServer().getPlayer(args[0]);
				
				if (Target != null) {
					Location tLoc = Target.getLocation();
					
						for(Player p: Bukkit.getServer().getOnlinePlayers()){
							if(!(p.hasPermission("bah.teleport.exempt"))){
								BeforeMassTele.put(p, p.getLocation());
								p.teleport(tLoc);
								p.sendMessage(ChatColor.GOLD+"Teleported everyone to "+Target.getName());}
						}
						
						Target.sendMessage(ChatColor.GOLD + "Teleported everyone to you!");
						sender.sendMessage(ChatColor.GOLD + "Teleported everyone to "+Target.getName()+"!");
						if (sIsPlayer) {
						getLogger().info(s.getName() + " teleported all to " + Target.getName());
						}
						return true;
						
					} else {
						
						sender.sendMessage(ChatColor.RED + args[0] + " was not found");
						return true;
						
					}

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

	public boolean isPlayer(CommandSender sender) {if (sender instanceof Player) {return true;} else {return false;}}
	public boolean hasPermission(CommandSender sender, String cmdPerm) {if (!isPlayer(sender)){return true;}else{return sender.hasPermission(cmdPerm);}}
	public Location sLocation(Player Sender) {if (isPlayer(Sender)){return Sender.getLocation();}else{return null;}}
}
