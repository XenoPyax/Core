package io.github.xenopyax.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.xenopyax.core.listeners.InteractNPCListener;
import io.github.xenopyax.core.listeners.InventoryInteractListener;
import io.github.xenopyax.core.listeners.PlayerChatListener;
import io.github.xenopyax.core.listeners.PlayerJoinListener;
import io.github.xenopyax.core.listeners.PlayerQuitListener;
import io.github.xenopyax.core.util.FileHandler;
import io.github.xenopyax.core.handlers.ScoreboardHandler;
import io.github.xenopyax.core.handlers.TablistHandler;
import io.github.xenopyax.economy.EconomyAPI;
import io.github.xenopyax.economy.commands.EconomyCMD;
import io.github.xenopyax.npc.EntityNPCManager;
import io.github.xenopyax.npc.commands.NPCSpawnCMD;
import io.github.xenopyax.npc.util.PacketReader;
import io.github.xenopyax.npc.util.Skin;
import net.minecraft.server.v1_16_R2.EntityPlayer;

public class Main extends JavaPlugin implements Listener {
	
    private static Main instance;
    private static EconomyAPI eco;
    private static ScoreboardHandler sbh;
    private static FileHandler fh;
    private static EntityNPCManager npcManager;
    private static TablistHandler th;
    
	@Override
	public void onEnable() {
		instance = this;
		eco = new EconomyAPI();
		fh = new FileHandler("npc");
		sbh = new ScoreboardHandler();
		npcManager = new EntityNPCManager();
		th = new TablistHandler();
		
		if(getFileHandler().getConfig().contains("data")) {
			loadNPC();
		}
		
		if(!Bukkit.getOnlinePlayers().isEmpty()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				sbh.setBoard(player);
				th.startUpdater();
				PacketReader reader = new PacketReader();
				reader.inject(player);
			}
		}
		sbh.startUpdater();
		registerListeners();
		registerCommands();
	}
	
	@Override
	public void onDisable() {
		sbh.stopUpdater();
		for(Player player : Bukkit.getOnlinePlayers()) {
			sbh.remove(player);
			PacketReader reader = new PacketReader();
			reader.uninject(player);
			for(EntityPlayer npc : npcManager.getNPCs()) {
				npcManager.removeNPC(player, npc);
			}
		}
	}
	
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new InteractNPCListener(), this);
		pm.registerEvents(new InventoryInteractListener(), this);
		pm.registerEvents(new PlayerChatListener(), this);
	}
	
	private void registerCommands() {
		EconomyCMD ecoCMD = new EconomyCMD();
		getCommand("economy").setExecutor(ecoCMD);
		getCommand("economy").setTabCompleter(ecoCMD);
		
		NPCSpawnCMD npcCMD = new NPCSpawnCMD();
		getCommand("spawn").setExecutor(npcCMD);
		getCommand("spawn").setTabCompleter(npcCMD);
	}
	
	public static FileHandler getFileHandler() {
		return fh;
	}

	public static TablistHandler getTablistHandler() {
		return th;
	}

	public static ScoreboardHandler getScoreboardHandler() {
		return sbh;
	}
	
	public static EconomyAPI getEconomyAPI() {
		return eco;
	}
	
	public static EntityNPCManager getNPCManager() {
		return npcManager;
	}

	public static Main getInstance() {
		return instance;
	}
	
	private void loadNPC() {
		YamlConfiguration cfg = getFileHandler().getConfig();
		cfg.getConfigurationSection("data").getKeys(false).forEach(npc -> {
			Location location = new Location(Bukkit.getWorld(cfg.getString("data." + npc + ".World")),
					cfg.getDouble("data." + npc + ".X"), cfg.getDouble("data." + npc + ".Y"), cfg.getDouble("data." + npc + ".Z"),
					(float)cfg.getDouble("data." + npc + ".Yaw"), (float)cfg.getDouble("data." + npc + ".Pitch"));
			String name = cfg.getString("data." + npc + ".Name");
			Skin skin = Skin.getByName(cfg.getString("data." + npc + ".Skin"));
			npcManager.loadNPC(location, name, skin);
		});
	}

	
}
