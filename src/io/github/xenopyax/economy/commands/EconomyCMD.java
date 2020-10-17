package io.github.xenopyax.economy.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.core.util.PlayerPM;
import io.github.xenopyax.core.util.Type;
import io.github.xenopyax.core.util.ServerCM;

public class EconomyCMD implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args.length == 4) {
			Player target = Bukkit.getPlayerExact(args[1]);
			String type = args[2];
			Long amount = Long.parseLong(args[3]);
			
			if(args[0].equalsIgnoreCase("set")) {
				if(type.equalsIgnoreCase("bank")) {
					Main.getEconomyAPI().setBank(target, amount);
				}else if(type.equalsIgnoreCase("purse")) {
					Main.getEconomyAPI().setPurse(target, amount);
				}else {
					if(sender instanceof Player) {
						PlayerPM.send((Player)sender, Type.ERROR, "Unknown account type '%s', transaction cancelled!", "§6"+type);
					}else {
						ServerCM.send(Type.ERROR, "Unknown account type '%s', transaction cancelled!", "§6"+type);
					}
				}
			}else if(args[0].equalsIgnoreCase("add")) {
				if(type.equalsIgnoreCase("bank")) {
					Main.getEconomyAPI().addBank(target, amount);
				}else if(type.equalsIgnoreCase("purse")) {
					Main.getEconomyAPI().addPurse(target, amount);
				}else {
					if(sender instanceof Player) {
						PlayerPM.send((Player)sender, Type.ERROR, "Unknown account type '%s', transaction cancelled!", "§6"+type);
					}else {
						ServerCM.send(Type.ERROR, "Unknown account type '%s', transaction cancelled!", "§6"+type);
					}
				}
			}else if(args[0].equalsIgnoreCase("remove")) {
				if(type.equalsIgnoreCase("bank")) {
					Main.getEconomyAPI().removeBank(target, amount);
				}else if(type.equalsIgnoreCase("purse")) {
					Main.getEconomyAPI().removePurse(target, amount);
				}else {
					if(sender instanceof Player) {
						PlayerPM.send((Player)sender, Type.ERROR, "Unknown account type '%s', transaction cancelled!", "§6"+type);
					}else {
						ServerCM.send(Type.ERROR, "Unknown account type '%s', transaction cancelled!", "§6"+type);
					}
				}
			}
		}
		
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list = new ArrayList<>();
		
		if(args.length == 1) {
			list.addAll(Arrays.asList("Set", "Add", "Remove"));
		}else if(args.length == 2) {
			list.addAll(Arrays.asList(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new)));
		}else if(args.length == 3) {
			list.addAll(Arrays.asList("Purse", "Bank"));
		}
		
		return list;
	}

}
