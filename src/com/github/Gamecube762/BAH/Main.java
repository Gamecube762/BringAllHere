package com.github.Gamecube762.BAH;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.Gamecube762.BAH.CommandHandler;

public class Main extends JavaPlugin{
	
	public Logger log;

	@Override
	public void onEnable() {
		this.log = this.getLogger();
		getCommand("bringallhere").setExecutor(new CommandHandler(this));
		getCommand("bringallto").setExecutor(new CommandHandler(this));
		getCommand("bringworldhere").setExecutor(new CommandHandler(this));
		getCommand("bringworldto").setExecutor(new CommandHandler(this));
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Goodbye! D=");	
	}

}
