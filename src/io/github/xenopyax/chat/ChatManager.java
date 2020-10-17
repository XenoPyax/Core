package io.github.xenopyax.chat;

import org.bukkit.entity.Player;

import io.github.xenopyax.chat.data.Prefix;
import io.github.xenopyax.core.util.ColorUtil;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatManager {
	
	private Player player;
	private PermissionUser pexUser;
	
	public ChatManager(Player player) {
		this.player = player;
		this.pexUser = PermissionsEx.getUser(player);
	}
	
	public String getChatFormat(String message) {		
		return (getPrefix().getPrefix().equals("") ? "" :getPrefix().getPrefix() + " ") + ColorUtil.insertFades(player.getName(), getPrefix().getNameFadeStart(), getPrefix().getNameFadeEnd(), false, false, false, false, false) 
			+ "§7: " + message;
	}
	
	public String getTabListFormat() {		
		return (getPrefix().getPrefix().equals("") ? "" :getPrefix().getPrefix() + " ") 
				+ ColorUtil.insertFades(player.getName(), getPrefix().getNameFadeStart(), getPrefix().getNameFadeEnd(), false, false, false, false, false);
	}
	
	public Prefix getPrefix() {
		return Prefix.getPrefixByName(pexUser.getParents().get(0).getName());
	}
	
}
