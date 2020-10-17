package io.github.xenopyax.job;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import io.github.xenopyax.core.util.GUIBuilder;
import io.github.xenopyax.core.util.IBItem;

public class Hunter {

	public static void showMainMenu(Player player) {
		player.openInventory(new GUIBuilder(player, 36, "Hunter Trade-In")
		.add(10, new IBItem().setType(Material.GUNPOWDER).build())
		.add(11, new IBItem().setType(Material.ROTTEN_FLESH).build())
		.add(12, new IBItem().setType(Material.BONE).build())
		.add(13, new IBItem().setType(Material.ENDER_PEARL).build())
		.add(14, new IBItem().setType(Material.SPIDER_EYE).build())
		.add(15, new IBItem().setType(Material.STRING).build())
		.add(16, new IBItem().setType(Material.BLAZE_ROD).build())
		.add(19, new IBItem().setType(Material.GHAST_TEAR).build())
		.add(20, new IBItem().setType(Material.MAGMA_CREAM).build())
		.add(21, new IBItem().setType(Material.SLIME_BALL).build())
		.build(true));
	}

}
