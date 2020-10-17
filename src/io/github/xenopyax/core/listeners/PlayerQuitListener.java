package io.github.xenopyax.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.npc.util.PacketReader;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		Main.getScoreboardHandler().remove(player);
		
		PacketReader reader = new PacketReader();
		reader.uninject(player);
	}

}
