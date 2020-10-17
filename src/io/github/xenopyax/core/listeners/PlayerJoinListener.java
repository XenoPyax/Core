package io.github.xenopyax.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.core.util.ColorUtil;
import io.github.xenopyax.npc.util.PacketReader;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		sendWelcomeMesage(player);
		sendNpcPackets(player);

		PacketReader reader = new PacketReader();
		reader.inject(player);
		
		Main.getEconomyAPI().checkAccount(player);		
		Main.getScoreboardHandler().setBoard(player);
	}

	private void sendWelcomeMesage(Player player) {
		player.sendMessage(ColorUtil.insertFades("------------------------------------------", "#9b34eb", "#690099", false, false, false, true, false));
		player.sendMessage(ColorUtil.insertFades("               Welcome to HappySkies!", "#00fff7", "#348ceb", false, false, false, false, false));
	}
	
	private void sendNpcPackets(Player player) {
		if (Main.getNPCManager().getNPCs() == null || Main.getNPCManager().getNPCs().isEmpty()) {
			return;
		}
		Main.getNPCManager().addJoinPacket(player);
	}

}
