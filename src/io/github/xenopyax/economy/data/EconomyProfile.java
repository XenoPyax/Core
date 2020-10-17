package io.github.xenopyax.economy.data;

public class EconomyProfile {
	
	private String playerId;
	private Long bank, purse;
	
	public EconomyProfile(String playerId, Long bank, Long purse) {
		this.playerId = playerId;
		this.bank = bank;
		this.purse = purse;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public Long getBank() {
		return bank;
	}

	public void setBank(Long bank) {
		this.bank = bank;
	}

	public Long getPurse() {
		return purse;
	}

	public void setPurse(Long purse) {
		this.purse = purse;
	}

}
