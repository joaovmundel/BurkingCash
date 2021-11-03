package me.joao.cash;

public class CashUser {
	private String nick;
	private String uuid;
	private int cash;

	public CashUser() {

	}

	public CashUser(String nick, String UUID, int cash) {
		this.setNick(nick);
		this.setUuid(UUID);
		this.setCash(cash);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
