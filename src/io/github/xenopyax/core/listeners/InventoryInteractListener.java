package io.github.xenopyax.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.core.util.GUIBuilder;
import io.github.xenopyax.core.util.IBItem;
import io.github.xenopyax.core.util.PlayerPM;
import io.github.xenopyax.core.util.Type;
import io.github.xenopyax.job.Blacksmith;
import io.github.xenopyax.job.Fisherman;
import io.github.xenopyax.job.Hunter;
import io.github.xenopyax.job.JobUtils;
import io.github.xenopyax.job.Lumberjack;
import io.github.xenopyax.job.Prospector;
import net.minecraft.server.v1_16_R2.EntityPlayer;

public class InventoryInteractListener implements Listener {
	
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		String title = e.getView().getTitle();
		if(title.startsWith("Delete NPC")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(!e.getCurrentItem().hasItemMeta()) return;
			if(e.getClickedInventory() == null) return;
			
			int npcId = Integer.parseInt(IBItem.getNBT(inv.getItem(11)).getString("npc"));
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aConfirm")) {
				EntityPlayer npc = Main.getNPCManager().getNPCById(npcId);
				if(Main.getNPCManager().deleteNPC(player, npc)) PlayerPM.send(player, Type.INFO, "NPC '§6%s' has been successfully deleted.", npc.getName());
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						e.getView().close();
					}
				});
				
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCancel")) {
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						e.getView().close();
					}
				});
			}
			
		}else if(title.startsWith("Lumberjack Trade-In")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(e.getClickedInventory() == null) return;
			if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
			
			showTradeInMenu(player, e.getCurrentItem(), "Lumberjack");
			
		}else if(title.startsWith("Prospector Trade-In")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(e.getClickedInventory() == null) return;
			if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
			
			showTradeInMenu(player, e.getCurrentItem(), "Prospector");
			
		}else if(title.startsWith("Hunter Trade-In")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(e.getClickedInventory() == null) return;
			if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
			
			showTradeInMenu(player, e.getCurrentItem(), "Hunter");
			
		}else if(title.startsWith("Blacksmith Trade-In")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(e.getClickedInventory() == null) return;
			if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
			
			showTradeInMenu(player, e.getCurrentItem(), "Blacksmith");
			
		}else if(title.startsWith("Fisherman Trade-In")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(e.getClickedInventory() == null) return;
			if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
			
			showTradeInMenu(player, e.getCurrentItem(), "Fisherman");
			
		}else if(inv.getItem(0) != null && IBItem.getNBT(inv.getItem(0)).hasKey("job")) {
			String job = IBItem.getNBT(inv.getItem(0)).getString("job");
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(e.getClickedInventory() == null) return;
			if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
			if(e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
				switch(job) {
				case "Hunter":
					Hunter.showMainMenu(player);
					break;
				case "Lumberjack":
					Lumberjack.showMainMenu(player);
					break;
				case "Prospector":
					Prospector.showMainMenu(player);
					break;
				case "Fisherman":
					Fisherman.showMainMenu(player);
					break;
				case "Blacksmith":
					Blacksmith.showMainMenu(player);
					break;
				case "Enchanter":
					// TODO: Enchanter.showMainMenu(player);
					break;
				case "Alchemist":
					// TODO: Alchemist.showMainMenu(player);
					break;
				}
				return;
			}
			
			runLogic(inv, player);
		}
		
	}
	
	private static void runLogic(Inventory inv, Player player) {
		ItemStack item = inv.getItem(10);
		if(item == null) return;
		
		switch(item.getItemMeta().getDisplayName().split(" ")[1]) {
			case "All":
				JobUtils.tradeIn(item.getType(), player, -1);
				break;
			case "1x":
				JobUtils.tradeIn(item.getType(), player, 1);
				break;
			case "8x":
				JobUtils.tradeIn(item.getType(), player, 8);
				break;
			case "16x":
				JobUtils.tradeIn(item.getType(), player, 16);
				break;
			case "32x":
				JobUtils.tradeIn(item.getType(), player, 32);
				break;
			case "64x":
				JobUtils.tradeIn(item.getType(), player, 64);
				break;
		}
		
	}
	
	private void showTradeInMenu(Player player, ItemStack item, String job) {
		Inventory gui = new GUIBuilder(player, 36, JobUtils.localize(item.getType()) + " Trade-In")
				.add(0, new IBItem().setDisplayname("§0").setType(Material.BLACK_STAINED_GLASS_PANE).setHiddenData("job", job).build())
				.add(10, new IBItem().setDisplayname("§aTrade 1x").setType(item.getType()).build())
				.add(11, new IBItem().setDisplayname("§aTrade 8x").setType(item.getType()).setAmount(8).build())
				.add(12, new IBItem().setDisplayname("§aTrade 16x").setType(item.getType()).setAmount(16).build())
				.add(13, new IBItem().setDisplayname("§aTrade 32x").setType(item.getType()).setAmount(32).build())
				.add(14, new IBItem().setDisplayname("§aTrade 64x").setType(item.getType()).setAmount(64).build())
				.add(16, new IBItem().setDisplayname("§aTrade All").setType(Material.CHEST).build())
				.add(31, new IBItem().setDisplayname("§cBack").setType(Material.RED_STAINED_GLASS_PANE).build())
				.build(true);
		player.openInventory(gui);
		
	}
	
}
