package me.joao.cash;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.joao.cash.conexao.Conexao;
import me.joao.cash.conexao.CriarBanco;
import me.joao.cash.conexao.Querys;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("cash").setExecutor(new Comandos());
		getCommand("devtestecash").setExecutor(new Comandos());
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		saveDefaultConfig();
		initCash();
	}

	@Override
	public void onDisable() {
		disableCash();
	}

	public static Main getInstance() {
		return (Main) Bukkit.getPluginManager().getPlugin("BurkingCash");
	}

	public void initCash() {
		File f = new File(this.getDataFolder() + "/data");
		if (!(f.exists())) {
			f.mkdir();
			System.out.println("[BurkingCash] Pasta data criada");
		}
		try {
			CriarBanco cb = new CriarBanco(new Conexao());
			cb.createTable();
		} catch (NullPointerException e) {
			System.out.println("[BurkingCash] Erro ao criar tabela.");
			System.out.println("[BurkingCash] " + e.getMessage());
		}

		if (Bukkit.getOnlinePlayers().size() > 0) {
			Querys qr = new Querys(new Conexao());
			for (Player all : Bukkit.getOnlinePlayers()) {
				Querys.cashes.put(all, qr.getCash(all));
			}
		}
	}

	public void disableCash() {
		try {
			Conexao con = new Conexao();
			if (Bukkit.getOnlinePlayers().size() > 0) {
				Querys qr = new Querys(con);
				for (Player all : Bukkit.getOnlinePlayers()) {
					qr.setCash(all, Querys.cashes.get(all));
				}
			}
			con.desconectar();
		} catch (NullPointerException e) {
			System.out.println("[BurkingCash] Conexao fechada.");
		}
	}

}
