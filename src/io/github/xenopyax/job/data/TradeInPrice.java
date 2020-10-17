package io.github.xenopyax.job.data;

import org.bukkit.Material;

public enum TradeInPrice {
	
	// Lumberjack
	OAK_LOG(1, 2), BIRCH_LOG(1, 3), SPRUCE_LOG(2, 4), JUNGLE_LOG(3, 6), ACACIA_LOG(2, 4), DARK_OAK_LOG(4, 8),
	
	COAL_ORE(1, 2), REDSTONE_ORE(1, 2), LAPIS_ORE(1, 2), IRON_ORE(1, 2), GOLD_ORE(2, 4), DIAMOND_ORE(3, 6), EMERALD_ORE(6, 8), NETHER_GOLD_ORE(2, 4), NETHER_QUARTZ_ORE(2, 4);
	
	Integer price;
	Integer progress;
	
	TradeInPrice(Integer price, Integer progress) {
		this.price = price;
		this.progress = progress;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getProgress() {
		return progress;
	}
	
	
	public static TradeInPrice getPriceByMaterial(Material mat) {
		for(TradeInPrice tip : TradeInPrice.values()) {
			if(tip.name().equalsIgnoreCase(mat.name())) {
				return tip;
			}
		}
		return null;
	}
}
