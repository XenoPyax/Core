package io.github.xenopyax.core.handlers;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.mrmicky.fastboard.FastBoard;
import io.github.xenopyax.core.Main;
import io.github.xenopyax.core.util.ColorUtil;
import io.github.xenopyax.core.util.ServerCM;
import io.github.xenopyax.core.util.Type;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardHandler {
	
    private HashMap<UUID, FastBoard> boards = new HashMap<>();
    private Integer task;
	
	public void setBoard(Player player) {
		FastBoard board = new FastBoard(player);
		board.updateTitle(ColorUtil.insertFades("<!> <!> HappySkies <!> <!>", "#00fff7", "#348ceb", false, false, false, false, false));
		board.updateLine(0, ColorUtil.insertFades("━━━━━━━━━━━━━━━━━━━━━━━", "#00fff7", "#348ceb", false, false, false, true, false));
		board.updateLine(1, "§3Money: " + ChatColor.of("#1ac74b") + "$" + Main.getEconomyAPI().getPurseFormatted(player));
		boards.put(player.getUniqueId(), board);
	}
	
	public void remove(Player player) {
		boards.remove(player.getUniqueId());
	}
	
	public void stopUpdater() {
		if(task == null) {
			ServerCM.send(Type.ERROR, "Tried to stop Scoreboard Updater task when none is running!");
			return;
		}
		Bukkit.getScheduler().cancelTask(task);
	}
	
	public void startUpdater() {
		if(task != null) {
			ServerCM.send(Type.ERROR, "Tried to start another Scoreboard Updater task!");
			return;
		}
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				for(Entry<UUID, FastBoard> fb : boards.entrySet()) {
					FastBoard board = fb.getValue();
					Player player = Bukkit.getPlayer(fb.getKey());
					board.updateLine(1, "§3Money: " + ChatColor.of("#1ac74b") + "$" + Main.getEconomyAPI().getPurseFormatted(player));
					boards.put(player.getUniqueId(), board);
				}
				
			}
		}, 60, 60);
	}

}
