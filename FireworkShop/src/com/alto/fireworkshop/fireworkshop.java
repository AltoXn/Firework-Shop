package com.alto.fireworkshop;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import com.feildmaster.lib.configuration.PluginWrapper;
import com.alto.fireworkshop.Commands;


public class fireworkshop extends PluginWrapper {

	private static fireworkshop plugin;
	public static final String Permission = "firework.use";

	@Override
	public void onEnable() {
		plugin = this;

		new Commands().onEnable();

		getCommand("firework").setExecutor(new Commands());

		getServer().getPluginManager().registerEvents(new Commands(),this);

		getLogger().info("Firework Plugin Enabled - Author: AltoXn");
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
		getLogger().info("Firework Plugin Disabled - Author: AltoXn");
	}

	public static void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin
				.getConfig().getString("no-permission-message")));
	}

	public static fireworkshop getInstance() {
		return plugin;
	}

	public static void Usage(CommandSender sender, String cmd) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&cIncorrect command parameters."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&cUsage: " + cmd));
	}
}

