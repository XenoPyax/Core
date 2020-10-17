package io.github.xenopyax.core.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.xenopyax.core.util.GUIBuilder;
import io.github.xenopyax.core.util.IBItem;
import io.github.xenopyax.job.Blacksmith;
import io.github.xenopyax.job.Fisherman;
import io.github.xenopyax.job.Hunter;
import io.github.xenopyax.job.Lumberjack;
import io.github.xenopyax.job.Prospector;
import io.github.xenopyax.npc.customevents.InteractNPCEvent;

public class InteractNPCListener implements Listener {
	
	@EventHandler
	public void onInteractNPC(InteractNPCEvent e) {
		Player player = e.getPlayer();
		
		if(player.isSneaking() && player.hasPermission("npc.edit")) {
			player.openInventory(new GUIBuilder(player, 27, "Delete NPC '" + e.getNPC().getName() + "§r' ?")
			.add(11, new IBItem().setType(Material.LIME_STAINED_GLASS_PANE).setDisplayname("§aConfirm").setHiddenData("npc", e.getNPC().getId()).build())
			.add(15, new IBItem().setType(Material.RED_STAINED_GLASS_PANE).setDisplayname("§cCancel").build())
			.build(true));
			return;
		}
		
		if(e.getNPC().getName().equalsIgnoreCase("Lumberjack")) {
			Lumberjack.showMainMenu(player);
		}else if(e.getNPC().getName().equalsIgnoreCase("Prospector")) {
			Prospector.showMainMenu(player);
		}else if(e.getNPC().getName().equalsIgnoreCase("Hunter")) {
			Hunter.showMainMenu(player);
		}else if(e.getNPC().getName().equalsIgnoreCase("Fisherman")) {
			Fisherman.showMainMenu(player);
		}else if(e.getNPC().getName().equalsIgnoreCase("Blacksmith")) {
			Blacksmith.showMainMenu(player);
		}
		
	}

}
