package com.github.exloki.firework;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import com.feildmaster.lib.configuration.PluginWrapper;

import com.github.exloki.firework.commands.FireworkCommandv3;

public class Fireworks extends PluginWrapper {

	private static Fireworks plugin;

	@Override
	public void onEnable() {
		plugin = this;

		new FireworkCommandv3().onEnable();

		getCommand("firework").setExecutor(new FireworkCommandv3());

		getServer().getPluginManager().registerEvents(new FireworkCommandv3(),
				this);

		getLogger().info("Firework Plugin Enabled - Author: Exloki");
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
		getLogger().info("Firework Plugin Disabled - Author: Exloki");
	}

	public static void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin
				.getConfig().getString("no-permission-message")));
	}

	public static Fireworks getInstance() {
		return plugin;
	}

	public static void Usage(CommandSender sender, String cmd) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&cIncorrect command parameters."));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&cUsage: " + cmd));
	}
}
