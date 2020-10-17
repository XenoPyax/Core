package io.github.xenopyax.job;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import io.github.xenopyax.core.util.GUIBuilder;
import io.github.xenopyax.core.util.IBItem;

public class Lumberjack {

	public static void showMainMenu(Player player) {
		player.openInventory(new GUIBuilder(player, 27, "Lumberjack Trade-In")
		.add(10, new IBItem().setType(Material.OAK_LOG).build())
		.add(11, new IBItem().setType(Material.BIRCH_LOG).build())
		.add(12, new IBItem().setType(Material.SPRUCE_LOG).build())
		.add(13, new IBItem().setType(Material.JUNGLE_LOG).build())
		.add(14, new IBItem().setType(Material.ACACIA_LOG).build())
		.add(15, new IBItem().setType(Material.DARK_OAK_LOG).build())
		.build(true));
	}

}
