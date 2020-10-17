package io.github.xenopyax.npc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.npc.util.Skin;

public class NPCSpawnCMD implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return true;
		Player player = (Player)sender;
		
		if(args.length == 2) {
			String name = args[0];
			Skin skin = Skin.getByName(args[1]);
			
			Main.getNPCManager().createNPC(player, name, skin);
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list = new ArrayList<>();
		
		if(args.length == 2) {
			for(Skin skin : Skin.values()) {
				list.add(skin.name());
			}
		}
		return list;
	}

}
