package io.github.xenopyax.npc.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.core.util.ServerCM;
import io.github.xenopyax.core.util.Type;
import io.github.xenopyax.npc.customevents.InteractNPCEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.Packet;
import net.minecraft.server.v1_16_R2.PacketPlayInUseEntity;

public class PacketReader {
	
	private Channel channel;
	public static HashMap<UUID, Channel> channels = new HashMap<>();
	
	public void inject(Player player) {
		CraftPlayer cp = (CraftPlayer) player;
		channel = cp.getHandle().playerConnection.networkManager.channel;
		channels.put(player.getUniqueId(), channel);
		
		if(channel.pipeline().get("PacketInjector") != null) return;
		
		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {

			@Override
			protected void decode(ChannelHandlerContext c, PacketPlayInUseEntity packet, List<Object> arg) throws Exception {
				arg.add(packet);
				readPacket(player, packet);
			}
		});
	}
	
	public void uninject(Player player) {
		channel = channels.get(player.getUniqueId());
		if(channel.pipeline().get("PacketInjector") != null) {
			channel.pipeline().remove("PacketInjector");
		}
	}
	
	public void readPacket(Player player, Packet<?> packet) {
		if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
			if(getValue(packet, "d") == null) return;
			if(getValue(packet, "action") == null) return;
			if(getValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND")) return;
			if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT")) return;
			if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")) return;
			
			int id = (int) getValue(packet, "a");
			
			if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {
				for(EntityPlayer npc : Main.getNPCManager().getNPCs()) {
					if(npc.getId() == id) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								Bukkit.getPluginManager().callEvent(new InteractNPCEvent(player, npc));
							}
							
						}, 0);
					}
				}
			}
			
		}
	}
	
	private Object getValue(Object instance, String name) {
		Object result = null;
		
		try {
			
			Field field = instance.getClass().getDeclaredField(name);
			
			field.setAccessible(true);
			
			result = field.get(instance);
			
			field.setAccessible(false);
			
		} catch(Exception e) {
			ServerCM.send(Type.ERROR, e.getMessage());
		}
		
		return result;
	}

}
