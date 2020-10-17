package io.github.xenopyax.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.github.xenopyax.chat.ChatManager;

public class PlayerChatListener implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		
		e.setFormat(new ChatManager(player).getChatFormat(e.getMessage()));
	}

}
