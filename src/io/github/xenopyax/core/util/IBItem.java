package io.github.xenopyax.core.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_16_R2.NBTTagCompound;

public class IBItem {
	
	ItemStack item;
	ItemMeta meta;
	
	public IBItem() {
		item = new ItemStack(Material.STICK);
		meta = item.getItemMeta();
	}
	
	public IBItem(ItemStack item, ItemMeta meta) {
		this.item = item;
		this.meta = meta;
	}
	
	public IBItem setType(Material type) {
		item.setType(type);
		return this;
	}

    public IBItem setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }
    
    // ITEMMETA CHANGES
	public IBItem setDisplayname(String name) {
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}
	
	public IBItem setHiddenData(String tag, Object data) {
		net.minecraft.server.v1_16_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemcompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		itemcompound.setString(tag, data.toString());
		nmsItem.setTag(itemcompound);
		item = CraftItemStack.asBukkitCopy(nmsItem);
		return this;
	}
	
    public IBItem setDurability(int damage) {
    	Damageable d = (Damageable)meta;
    	Integer dmg = damage - item.getType().getMaxDurability();
    	d.setDamage(dmg);
    	item.setItemMeta((ItemMeta) d);
    	return this;
    }
	
	public IBItem setLore(List<String> lore) {
		meta.setLore(lore);
		item.setItemMeta(meta);
		return this;
	}
	
	public IBItem setUnbreakable(boolean unbreakable) {
		meta.setUnbreakable(unbreakable);
		item.setItemMeta(meta);
		return this;
	}
	
	public IBItem toggleUnbreakable() {
		if(meta.isUnbreakable()) {
			meta.setUnbreakable(false);
		}else {
			meta.setUnbreakable(true);
		}
		item.setItemMeta(meta);
		return this;
	}
	
	public IBItem addItemFlags(ItemFlag... itemFlags) {
		meta.addItemFlags(itemFlags);
		item.setItemMeta(meta);
		return this;
	}
	
	public IBItem removeItemFlags(ItemFlag... itemFlags) {
		meta.removeItemFlags(itemFlags);
		item.setItemMeta(meta);
        return this;
		
	}
	
	public IBItem addEnchantments(Map<Enchantment, Integer> enchantments, boolean ignoreRestriction) {
        for(Entry<Enchantment, Integer> e : enchantments.entrySet()) {
        	meta.addEnchant(e.getKey(), e.getValue(), ignoreRestriction);
        }
		item.setItemMeta(meta);
        return this;
    }

 
    public IBItem addEnchantment(Enchantment ench, int level, boolean ignoreRestriction) {
        meta.addEnchant(ench, level, ignoreRestriction);
		item.setItemMeta(meta);
        return this;
    }
    
    public IBItem removeEnchant(Enchantment ench) {
    	meta.removeEnchant(ench);
		item.setItemMeta(meta);
        return this;
    }
    
    
    // General
    public ItemStack build() {
    	return item;
    }

	public static IBItem fromItemStack(ItemStack item) {
		return new IBItem(item, item.getItemMeta());
	}
	
	public static NBTTagCompound getNBT(ItemStack item) {
		net.minecraft.server.v1_16_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemcompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return itemcompound;
	}
}
