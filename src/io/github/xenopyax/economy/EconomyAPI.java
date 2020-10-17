package io.github.xenopyax.economy;

import org.bukkit.entity.Player;

import io.github.xenopyax.core.util.CurrencyFormat;
import io.github.xenopyax.core.util.ServerCM;
import io.github.xenopyax.core.util.Type;
import io.github.xenopyax.economy.data.EconomyProfile;
import io.github.xenopyax.economy.util.EconomyFile;

public class EconomyAPI {
	
	private EconomyFile ecoFile;
	
	public EconomyAPI() {
		ecoFile = new EconomyFile();
	}
	
	public void checkAccount(Player player) {
		if(!ecoFile.hasAccount(player)) {
			ecoFile.updateEconomyData(new EconomyProfile(player.getUniqueId().toString(), 0L, 0L));
			ServerCM.send(Type.INFO, "A new economy account for '%s' has been created!", "§6" + player.getName());
		}
	}
	
	public Long getPurse(Player player) {
		return ecoFile.getEconomyData(player).getPurse();
	}
	
	public String getPurseFormatted(Player player) {
		return CurrencyFormat.format(ecoFile.getEconomyData(player).getPurse());
	}
	
	public void addPurse(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		ep.setPurse(ep.getPurse() + amount.longValue());
		ecoFile.updateEconomyData(ep);
	}
	
	public void removePurse(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		if(hasEnoughPurse(player, amount)) {
			ep.setPurse(ep.getPurse() - amount.longValue());
		}else {
			ep.setPurse(0L);
		}
		ecoFile.updateEconomyData(ep);
	}
	
	public Boolean hasEnoughPurse(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		return ep.getPurse() >= amount.longValue();
	}
	
	public void setPurse(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		ep.setPurse(amount.longValue());
		ecoFile.updateEconomyData(ep);
	}
	
	
	public Long getBank(Player player) {
		return ecoFile.getEconomyData(player).getBank();
	}
	
	public String getBankFormatted(Player player) {
		return CurrencyFormat.format(ecoFile.getEconomyData(player).getBank());
	}
	
	public void addBank(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		ep.setBank(ep.getBank() + amount.longValue());
		ecoFile.updateEconomyData(ep);
	}
	
	public void removeBank(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		if(hasEnoughBank(player, amount)) {
			ep.setBank(ep.getBank() - amount.longValue());
		}else {
			ep.setBank(0L);
		}
		ecoFile.updateEconomyData(ep);
	}
	
	public Boolean hasEnoughBank(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		return ep.getBank() >= amount.longValue();
	}
	
	public void setBank(Player player, Number amount) {
		EconomyProfile ep = ecoFile.getEconomyData(player);
		ep.setBank(amount.longValue());
		ecoFile.updateEconomyData(ep);
	}
	
}
