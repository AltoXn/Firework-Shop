package com.alto.fireworkshop;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


import com.alto.fireworkshop.Main;
import com.alto.fireworkshop.Execute;

import com.feildmaster.lib.configuration.EnhancedConfiguration;

public class Commands extends JavaPlugin implements CommandExecutor, Listener {

	private Main plugin = Main.getInstance();
	private Execute execute = new Execute();
	String cmd = "/firework <set|check|create|reset> [flag|star|rocket] [value]";

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission(Main.Permission)) {
			File file = new File(plugin.getDataFolder(), "config.yml");
			EnhancedConfiguration config = new EnhancedConfiguration(file,plugin);
			
			config.load();
			if (!config.getBoolean(player.getName() + ".exists")) {
				config.set(player.getName() + ".trail", false);
				config.set(player.getName() + ".flicker", false);
				config.set(player.getName() + ".type", "small");
				config.set(player.getName() + ".height", 2);
				config.set(player.getName() + ".colours",
						new ArrayList<Integer>());
				config.set(player.getName() + ".coloursmess", "");
				config.set(player.getName() + ".fade", new ArrayList<Integer>());
				config.set(player.getName() + ".fademess", "");
				config.set(player.getName() + ".exists", true);
				config.save();
			}
		}
	}

	public void onEnable() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.hasPermission(Main.Permission)) {
				File file = new File(plugin.getDataFolder(), "config.yml");
				EnhancedConfiguration config = new EnhancedConfiguration(file,
						plugin);
				config.load();
				if (!config.getBoolean(player.getName() + ".exists")) {
					config.set(player.getName() + ".trail", false);
					config.set(player.getName() + ".flicker", false);
					config.set(player.getName() + ".type", "small");
					config.set(player.getName() + ".height", 2);
					config.set(player.getName() + ".colours",
							new ArrayList<Integer>());
					config.set(player.getName() + ".coloursmess", "");
					config.set(player.getName() + ".fade",
							new ArrayList<Integer>());
					config.set(player.getName() + ".fademess", "");
					config.set(player.getName() + ".exists", true);
					config.save();
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("firework")) {
			if (sender.hasPermission(Main.Permission)) {
				File file = new File(plugin.getDataFolder(), "config.yml");
				EnhancedConfiguration config = new EnhancedConfiguration(file, plugin);
				
				
				if (args.length == 0) {
					execute.help(player);
					
					
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("check")) {
						execute.check(player);
					} else if (args[0].equalsIgnoreCase("reset")) {
						execute.reset(player);
					} else {
						execute.help(player);
					}
					
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("create")) {
						if (args[1].equalsIgnoreCase("rocket")) {
							execute.createRocket(player, args);
						} else if (args[1].equalsIgnoreCase("star")) {
							execute.createStar(player, args); 
						} else {
							sender.sendMessage(ChatColor.RED + "Try /firework create <star|rocket>");
						}
					} else if (args[0].equalsIgnoreCase("set")) {
						sender.sendMessage(ChatColor.RED + "Try /firework set <trail|twinkle|type|colours|fade|height> [value]");
					}
				} else if (args.length > 2) {
					if (args[0].equalsIgnoreCase("set")) {
						config.load();
						if (args[1].equalsIgnoreCase("trail")) {
							if (args.length == 3) {
								execute.trail(player, args);
							} else {
								sender.sendMessage(ChatColor.RED + "Try /firework set trail <true|false|switch>");
							}
						} else if (args[1].equalsIgnoreCase("twinkle")) {
							if (args.length == 3) {
								execute.twinkle(player, args);
							} else {
								sender.sendMessage(ChatColor.RED+ "Try /firework set twinkle <true|false|switch>");
							}
						} else if (args[1].equalsIgnoreCase("type")) {
							if (args.length == 3) {
								execute.type(player, args);
							} else {
								sender.sendMessage(ChatColor.RED
										+ "Try /firework set type <large|small|burst|creeper|star>");
							}
						} else if (args[1].equalsIgnoreCase("colours") || args[1].equalsIgnoreCase("colour")) {
							if (args.length > 2) {
								execute.colour(player, args);
							} else {
								sender.sendMessage(ChatColor.RED + "No colours chosen");
								sender.sendMessage(ChatColor.GREEN + "Applicable colours: " + ChatColor.GRAY + "Blue, Purple, Red, Green, Cyan, LightGrey, Grey, Pink, Lime, Black, Brown, Yellow, Magenta, Orange, LightBlue, White, Reset.");
							}
						} else if (args[1].equalsIgnoreCase("fade")) {
							if (args.length > 2) {
								execute.fade(player, args);
							} else {
								sender.sendMessage(ChatColor.RED + "No colours chosen");
								sender.sendMessage(ChatColor.GREEN + "Applicable colours: " + ChatColor.GRAY + "Blue, Purple, Red, Green, Cyan, LightGrey, Grey, Pink, Lime, Black, Brown, Yellow, Magenta, Orange, LightBlue, White, Reset.");
							}
						} else if (args[1].equalsIgnoreCase("height")) {
							if (args.length == 3) {
								execute.height(player, args);
							} else {
								sender.sendMessage(ChatColor.RED+ "No colours chosen");
								sender.sendMessage(ChatColor.GREEN + "Applicable colours: " + ChatColor.GRAY + "Blue, Purple, Red, Green, Cyan, LightGrey, Grey, Pink, Lime, Black, Brown, Yellow, Magenta, Orange, LightBlue, White, Reset.");
							}
						} else {
							sender.sendMessage(ChatColor.RED
									+ "Try /firework set <trail|twinkle|type|colours|fade|height> [value]");
						}
					} else {
						Main.Usage(sender, cmd);
					}
				}

			} else {
				Main.noPermission(sender);
			}
		}
		return false;
	}
}