package io.github.xenopyax.job;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import io.github.xenopyax.core.util.GUIBuilder;
import io.github.xenopyax.core.util.IBItem;

public class Fisherman {

	public static void showMainMenu(Player player) {
		player.openInventory(new GUIBuilder(player, 27, "Fisherman Trade-In")
		.add(10, new IBItem().setType(Material.COD).build())
		.add(11, new IBItem().setType(Material.PUFFERFISH).build())
		.add(12, new IBItem().setType(Material.TROPICAL_FISH).build())
		.add(13, new IBItem().setType(Material.SALMON).build())
		.build(true));
	}

}
