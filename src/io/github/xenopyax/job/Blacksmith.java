package io.github.xenopyax.job;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import io.github.xenopyax.core.util.GUIBuilder;
import io.github.xenopyax.core.util.IBItem;

public class Blacksmith {

	public static void showMainMenu(Player player) {
		player.openInventory(new GUIBuilder(player, 27, "Blacksmith Trade-In")
		.add(10, new IBItem().setType(Material.COAL).build())
		.add(11, new IBItem().setType(Material.CHARCOAL).build())
		.add(12, new IBItem().setType(Material.DIAMOND).build())
		.add(13, new IBItem().setType(Material.IRON_INGOT).build())
		.add(14, new IBItem().setType(Material.GOLD_INGOT).build())
		.add(15, new IBItem().setType(Material.NETHERITE_INGOT).build())
		.build(true));
	}

}
