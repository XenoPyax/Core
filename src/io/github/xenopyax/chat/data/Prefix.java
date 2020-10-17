package io.github.xenopyax.chat.data;

import io.github.xenopyax.core.util.ColorUtil;

public enum Prefix {
	
	ADMIN(ColorUtil.insertFades("ADMIN", "#910000", "#b40202", true, false, false, false, false), "#f91c02", "#f01900"),
	USER("", "#00c87c", "#00ff9e");
	
	String prefix;
	String nameFadeStart;
	String nameFadeEnd;
	
	Prefix(String prefix, String nameFadeStart, String nameFadeEnd) {
		this.prefix = prefix;
		this.nameFadeStart = nameFadeStart;
		this.nameFadeEnd = nameFadeEnd;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getNameFadeStart() {
		return nameFadeStart;
	}

	public String getNameFadeEnd() {
		return nameFadeEnd;
	}

	public static Prefix getPrefixByName(String name) {
		for(Prefix prefix : Prefix.values()) {
			if(prefix.name().equalsIgnoreCase(name)) {
				return prefix;
			}
		}
		return null;
	}

}
