package com.github.Gamecube762.BAH;

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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labal, String[] args) {
		int PlayersOnline = Bukkit.getServer().getOnlinePlayers().length;
		Player s = Bukkit.getServer().getPlayer(sender.getName());
		Location sLoc = s.getLocation();
		
		if(cmd.getName().equalsIgnoreCase("bringallhere")|cmd.getName().equalsIgnoreCase("bah")){
			if (PlayersOnline <= 1){s.sendMessage(ChatColor.RED + "No one is online to teleport to you ='(");return true;}
			for(Player p: Bukkit.getServer().getOnlinePlayers()){
				if (p!=s) {
					p.teleport(sLoc);
					p.sendMessage(ChatColor.GOLD + "Teleported all to " + s.getName());
				}
			}	
			s.sendMessage(ChatColor.GOLD + "Teleported all to you!");
			getLogger().info(s.getName() + " teleported all to him/her");
			return true;
		} else if(cmd.getName().equalsIgnoreCase("bringallto")|cmd.getName().equalsIgnoreCase("bah")){
			if (!(args.length > 0)) {s.sendMessage(ChatColor.RED + "Needs a Player to teleport to!"); return true;}
			Player Target = Bukkit.getServer().getPlayer(args[0]);
			if (Target != null) {
			Location tLoc = Target.getLocation();
					for(Player p: Bukkit.getServer().getOnlinePlayers()){
						if(p!=Target){p.teleport(tLoc);
						p.sendMessage(ChatColor.GOLD+"Teleported everyone to "+Target.getName());}
					}
					Target.sendMessage(ChatColor.GOLD + "Teleported everyone to you!");
					s.sendMessage(ChatColor.GOLD + "Teleported everyone to "+Target.getName()+"!");
					getLogger().info(s.getName() + " teleported all to " + Target.getName());
					return true;
				} else {
					s.sendMessage(ChatColor.RED + args[0] + " was not found");
					return true;
					}
		}
		return true;
	}
}
