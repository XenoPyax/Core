package io.github.xenopyax.core.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import io.github.xenopyax.core.Main;

public class FileHandler {
	
	private File file;
	private YamlConfiguration cfg;
	
	public FileHandler(String name) {
		file = new File(Main.getInstance().getDataFolder(), name + ".yml");
		try {
			if(Main.getInstance().getDataFolder().mkdir()) ServerCM.send(Type.INFO, "Folder '§6%s' has been created successfully!", Main.getInstance().getDataFolder().getName());
			if(file.createNewFile()) ServerCM.send(Type.INFO, "§6%s has been created successfully!", file.getName());
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public YamlConfiguration getConfig() {
		return cfg;
	}
	
	public void save() {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
