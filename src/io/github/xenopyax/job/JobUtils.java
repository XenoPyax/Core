package io.github.xenopyax.job;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.job.data.TradeInPrice;
import net.md_5.bungee.api.ChatColor;

public class JobUtils {
	
	public static void tradeIn(Material material, Player player, Integer amount) {
		if(amount == -1) {
			if(getAmountInInventory(player.getInventory(), material) >= 1) {
				amount = getAmountInInventory(player.getInventory(), material);
				
				if(removeMats(player.getInventory(), material, amount)) {
					player.sendMessage("§8>> §3You've traded §b" + amount + "§3 " + localize(material) + " for " 
							+ ChatColor.of("#1ac74b") + "$" + TradeInPrice.getPriceByMaterial(material).getPrice()*amount);
					Main.getEconomyAPI().addPurse(player, TradeInPrice.getPriceByMaterial(material).getPrice()*amount);
				}else {
					player.sendMessage("§8>> §4I was not able to take those materials.!");
				}
			}else {
				player.sendMessage("§8>> §4You dont have enough materials for that Trade-In.");
			}
		}else {
			if(getAmountInInventory(player.getInventory(), material) >= amount) {
				if(removeMats(player.getInventory(), material, amount)) {
					player.sendMessage("§8>> §3You've traded §b" + amount + "§3 " + localize(material) + " for " 
							+ ChatColor.of("#1ac74b") + "$" + TradeInPrice.getPriceByMaterial(material).getPrice()*amount);
					Main.getEconomyAPI().addPurse(player, TradeInPrice.getPriceByMaterial(material).getPrice()*amount);
				}else {
					player.sendMessage("§8>> §4I was not able to take those materials.!");
				}
			}else {
				player.sendMessage("§8>> §4You dont have enough materials for that Trade-In.");
			}
		}
	}
	
	public static Boolean removeMats(Inventory inv, Material type, int amount) {
		int left = amount;
		for(int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if(item != null && !item.hasItemMeta() && item.getType() == type) {
				if(item.getAmount() >= left) {
					inv.getItem(i).setAmount(inv.getItem(i).getAmount() - left);
					return true;
				}else {
					left -= item.getAmount();
					item.setAmount(0);
				}
			}
		}
		return false;
	}

	public static int getAmountInInventory(Inventory inv, Material material) {
		int amount = 0;
		for(ItemStack i : inv.getContents()) {
			if(i != null && i.getType() == material && !i.hasItemMeta()) {
				amount += i.getAmount();
			}
		}
		return amount;
	}

	public static String localize(Material mat) {
		String[] name = mat.name().split("_");
		String finalName = "";
		
		for(String s : name) {
			if(finalName.equals("")) {
				finalName = s.substring(0, 1) + s.substring(1).toLowerCase();
			}else {
				finalName += " " + s.substring(0, 1) + s.substring(1).toLowerCase();
			}
		}
		return finalName;
	}

}
