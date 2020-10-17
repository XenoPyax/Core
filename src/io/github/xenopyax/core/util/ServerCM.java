package io.github.xenopyax.core.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

public class ServerCM {
	
	private static String prefix = "§5[Core] §r";
	
	public static void send(Type type, String message, Object... args) {
		switch(type) {
		case ERROR:
			prefix += "§4";
			message = String.format(message, getEdit("§4", args));
			break;
		case INFO:
			prefix += "§3";
			message = String.format(message, getEdit("§3", args));
			break;
		case NORMAL:
			prefix += "§f";
			message = String.format(message, getEdit("§f", args));
			break;
		case WARNING:
			prefix += "§e";
			message = String.format(message, getEdit("§e", args));
			break;
		}
		Bukkit.getConsoleSender().sendMessage(prefix + message);
	}
	
	private static Object[] getEdit(String color, Object... args) {
		List<Object> list = new ArrayList<>();
		for(Object o : args) {
			o += color;
			list.add(o);
		}
		return list.toArray();
	}

}
