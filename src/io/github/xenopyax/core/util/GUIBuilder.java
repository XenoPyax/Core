package io.github.xenopyax.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIBuilder {
	
	private Inventory inv;
	
	public GUIBuilder(Player player, Integer size, String title) {
		inv = Bukkit.createInventory(player, size, title);
	}
	
	public GUIBuilder add(Integer slot, ItemStack item) {
		inv.setItem(slot, item);
		return this;
	}
	
	public Inventory build(Boolean fillAir) {
		if(fillAir == false || fillAir == null) {
			return inv;
		}else {
			for(int i = 0; i < inv.getSize(); i++) {
				if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
					inv.setItem(i, new IBItem().setType(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§3").build());
				}else if(inv.getItem(i).getType() == Material.BARRIER){
					inv.setItem(i, new IBItem().setType(Material.AIR).build());
				}
			}
			return inv;
		}
	}

}
