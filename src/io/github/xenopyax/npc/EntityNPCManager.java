package io.github.xenopyax.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.github.xenopyax.core.Main;
import io.github.xenopyax.npc.util.Skin;
import net.minecraft.server.v1_16_R2.DataWatcherRegistry;
import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.MinecraftServer;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R2.PlayerConnection;
import net.minecraft.server.v1_16_R2.PlayerInteractManager;
import net.minecraft.server.v1_16_R2.WorldServer;

public class EntityNPCManager {
	
	private List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
	
	public void createNPC(Player player, String npcName, Skin skin) {
		npcName = ChatColor.translateAlternateColorCodes('&', npcName).replace("_", " ");
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer worldServer = ((CraftWorld)Bukkit.getWorld(player.getWorld().getName())).getHandle();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), npcName.length() > 16 ? npcName.substring(0, 16) : npcName);
		EntityPlayer npc = new EntityPlayer(server, worldServer, gameProfile, new PlayerInteractManager(worldServer));
		npc.setLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
		
		gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));
		
		addNpcPacket(npc);
		NPC.add(npc);
		
		int count = 1;
		if(Main.getFileHandler().getConfig().contains("data")) count = Main.getFileHandler().getConfig().getConfigurationSection("data").getKeys(false).size() + 1;
		Main.getFileHandler().getConfig().set("data." + count + ".X", player.getLocation().getX());
		Main.getFileHandler().getConfig().set("data." + count + ".Y", player.getLocation().getY());
		Main.getFileHandler().getConfig().set("data." + count + ".Z", player.getLocation().getZ());
		Main.getFileHandler().getConfig().set("data." + count + ".Pitch", player.getLocation().getPitch());
		Main.getFileHandler().getConfig().set("data." + count + ".Yaw", player.getLocation().getYaw());
		Main.getFileHandler().getConfig().set("data." + count + ".World", player.getLocation().getWorld().getName());
		Main.getFileHandler().getConfig().set("data." + count + ".Name", npcName.replace("§", "&").replace(" ", "_"));
		Main.getFileHandler().getConfig().set("data." + count + ".Skin", skin.name());
		Main.getFileHandler().save();
	}
	
	public void loadNPC(Location location, String npcName, Skin skin) {
		npcName = ChatColor.translateAlternateColorCodes('&', npcName).replace("_", " ");
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer worldServer = ((CraftWorld)Bukkit.getWorld(location.getWorld().getName())).getHandle();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), npcName.length() > 16 ? npcName.substring(0, 16) : npcName);
		EntityPlayer npc = new EntityPlayer(server, worldServer, gameProfile, new PlayerInteractManager(worldServer));
		npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		
		gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));
		
		NPC.add(npc);
		addNpcPacket(npc);
	}
	
	public void addNpcPacket(EntityPlayer npc) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
			connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc,  (byte) (npc.yaw *256 /360)));
			npc.getDataWatcher().set(DataWatcherRegistry.a.a(16), (byte)127); 
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
				}
			}, 80);
		}
	}
	
	public void removeNPC(Player player, EntityPlayer npc) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
	}
	
	public void addJoinPacket(Player player) {
		for(EntityPlayer npc : NPC) {
			PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
			connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc,  (byte) (npc.yaw *256 /360)));
			npc.getDataWatcher().set(DataWatcherRegistry.a.a(16), (byte)127); 
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
				}
			}, 80);
		}
	}
	
	public EntityPlayer getNPCById(int id) {
		for(EntityPlayer npc : NPC) {
			if(npc.getId() == id) {
				return npc;
			}
		}
		return null;
	}

	public Boolean deleteNPC(Player player, EntityPlayer npc) {
		Boolean success = false;
		String name = npc.getName();
		double x = npc.locX();
		double y = npc.locY();
		double z = npc.locZ();
		String world = npc.getWorldServer().getWorld().getName();
		YamlConfiguration cfg = Main.getFileHandler().getConfig();
		
		for(String num : Main.getFileHandler().getConfig().getConfigurationSection("data").getKeys(false)){
			if(cfg.getDouble("data." + num + ".X") == x && cfg.getDouble("data." + num + ".Y") == y && cfg.getDouble("data." + num + ".Z") == z
					&& cfg.getString("data." + num + ".Name").equals(name) && cfg.getString("data." + num + ".World").equals(world)) {
				Bukkit.getOnlinePlayers().forEach(p -> {
					removeNPC(p, npc);
				});
				NPC.remove(npc);
				cfg.set("data." + num, null);
				Main.getFileHandler().save();
				success = true;
			}
		}
		return success;
	}
	
	public List<EntityPlayer> getNPCs() {
		return NPC;
	}

}
