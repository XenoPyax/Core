package io.github.xenopyax.economy.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.core.util.ServerCM;
import io.github.xenopyax.core.util.Type;
import io.github.xenopyax.economy.data.EconomyProfile;

public class EconomyFile {
	
	private File file;
	private YamlConfiguration cfg;
	
	public EconomyFile() {
		
		Main.getInstance().getDataFolder().mkdir();
		file = new File(Main.getInstance().getDataFolder(), "Economy.yml");
		try {
			if(file.createNewFile()) ServerCM.send(Type.INFO, file.getName() + " has been created successfully.");
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			ServerCM.send(Type.ERROR, e.getMessage());
		}
		
	}
	
	public Boolean hasAccount(Player player) {
		if(cfg.get("economy." + player.getUniqueId().toString()) != null) {
			return true;
		}
		return false;
	}
	
	public EconomyProfile getEconomyData(Player player) {
		return new EconomyProfile(player.getUniqueId().toString(),
				cfg.getLong("economy." + player.getUniqueId().toString() + ".bank"),
				cfg.getLong("economy." + player.getUniqueId().toString() + ".purse"));
	}
	
	public void updateEconomyData(EconomyProfile profile) {
		cfg.set("economy." + profile.getPlayerId() + ".bank", profile.getBank());
		cfg.set("economy." + profile.getPlayerId() + ".purse", profile.getPurse());
		try {
			cfg.save(file);
		} catch (IOException e) {
			ServerCM.send(Type.ERROR, e.getMessage());
		}
	}

}
