package io.github.xenopyax.job;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import io.github.xenopyax.core.util.GUIBuilder;
import io.github.xenopyax.core.util.IBItem;

public class Prospector {

	public static void showMainMenu(Player player) {
		player.openInventory(new GUIBuilder(player, 36, "Prospector Trade-In")
		.add(10, new IBItem().setType(Material.COAL_ORE).build())
		.add(11, new IBItem().setType(Material.REDSTONE_ORE).build())
		.add(12, new IBItem().setType(Material.LAPIS_ORE).build())
		.add(13, new IBItem().setType(Material.IRON_ORE).build())
		.add(14, new IBItem().setType(Material.GOLD_ORE).build())
		.add(15, new IBItem().setType(Material.DIAMOND_ORE).build())
		.add(16, new IBItem().setType(Material.EMERALD_ORE).build())
		.add(19, new IBItem().setType(Material.NETHER_GOLD_ORE).build())
		.add(20, new IBItem().setType(Material.NETHER_QUARTZ_ORE).build())
		.build(true));
	}

}
