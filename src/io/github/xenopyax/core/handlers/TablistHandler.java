package io.github.xenopyax.core.handlers;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.github.xenopyax.chat.ChatManager;
import io.github.xenopyax.core.Main;
import io.github.xenopyax.core.util.ColorUtil;
import io.github.xenopyax.core.util.ServerCM;
import io.github.xenopyax.core.util.Type;
import net.md_5.bungee.api.ChatColor;

public class TablistHandler {
	
	private Integer task;
	
	private void setTab(Player player) {
		
		player.setPlayerListName(new ChatManager(player).getTabListFormat());
		
		player.setPlayerListHeader(ColorUtil.insertFades("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#00fff7", "#348ceb", false, false, false, true, false)
				+ "\n"
				+ ColorUtil.insertFades("Welcome to HappySkies!", "#00fff7", "#348ceb", false, false, false, false, false)
				+ "\n"
				+ ColorUtil.insertFades("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#00fff7", "#348ceb", false, false, false, true, false)
				+ "\n\n"
				+ ChatColor.of("#00ff93") + "Players Online: " + ChatColor.of("#00c8fe") + Bukkit.getOnlinePlayers().size()
				+ "          "
				+ ChatColor.of("#00ff93") + "Your Ping: " + getPing(player) + "ms"
				+ "\n\n");
		
		player.setPlayerListFooter("\n\n"
				+ ColorUtil.insertFades("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#00fff7", "#348ceb", false, false, false, true, false)
				+ "\n"
				+ ColorUtil.insertFades("Website: www.happyskies.com", "#00fff7", "#348ceb", false, false, false, false, false) 
				+ "\n"
				+ ColorUtil.insertFades("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#00fff7", "#348ceb", false, false, false, true, false));
		
	}
	
	public void stopUpdater() {
		if(task == null) {
			ServerCM.send(Type.ERROR, "Tried to stop Tablist Updater task when none is running!");
			return;
		}
		Bukkit.getScheduler().cancelTask(task);
	}
	
	public void startUpdater() {
		if(task != null) {
			ServerCM.send(Type.ERROR, "Tried to start another Tablist Updater task!");
			return;
		}
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				Bukkit.getOnlinePlayers().forEach(player -> setTab(player));
				
			}
		}, 60, 60);
	}
	
	private String getPing(Player player) {
		int ping = ((CraftPlayer) player).getHandle().ping;
		String color = "#c4c4c4";
		if(ping < 30) {
			// Good
			 color = "#00ff0c";
		}else if(ping < 100) {
			// Ok
			color = "#fef600";
		}else if(ping < 200){
			// meh
			color = "#fe5900";
		}else{
			// Super Bad
			color = "#c90000";
		}
		
		return ChatColor.of(color) + "" + ping;
	}

}
