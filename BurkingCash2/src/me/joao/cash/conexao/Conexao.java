package me.joao.cash.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import me.joao.cash.Main;

public class Conexao {
	private Connection conexao;

	/*
	 * conecta a um banco ou cria se ele nao existir
	 */
	public boolean conectar() {
		try {
			if (Main.getInstance().getConfig().getString("MySQL.Driver").toLowerCase().equalsIgnoreCase("sqlite")) {
				String url = "jdbc:sqlite:E:/Mine/SurvivalUP 1.12/plugins/BurkingCash/data/cash.db";
				this.conexao = DriverManager.getConnection(url);
			} else if (Main.getInstance().getConfig().getString("MySQL.Driver").toLowerCase()
					.equalsIgnoreCase("mariadb")) {
				String user = Main.getInstance().getConfig().getString("MySQL.User");
				String pass = Main.getInstance().getConfig().getString("MySQL.Password");
				String host = Main.getInstance().getConfig().getString("MySQL.Host");
				String db = Main.getInstance().getConfig().getString("MySQL.Db");
				String url = "jdbc:mariadb://" + host + "/" + db;
				this.conexao = DriverManager.getConnection(url, user, pass);
			} else {
				System.out.println("[BurkingCash] Erro: Driver SQL nao compativel.");
			}

		} catch (SQLException e) {
			System.out.println("[BurkingCash] Erro ao conectar ao banco.");
			System.out.println("[BurkingCash] " + e.getMessage());
		}
		return true;
	}

	public boolean conectar(int a) {
		try {
			String url = "jdbc:mariadb://127.0.0.1/cash";
			this.conexao = DriverManager.getConnection(url, "root", "");
			System.out.println("Conectado");
		} catch (SQLException e) {
			System.out.println("[BurkingCash] Erro ao conectar ao banco.");
			System.out.println("[BurkingCash] " + e.getMessage());
		}
		return true;
	}

	public boolean desconectar() {
		try {
			if (this.conexao.isClosed() == false) {
				this.conexao.close();
			}
		} catch (SQLException e) {
			System.out.println("[BurkingCash] Erro ao desconectar do banco de dados");
			return false;
		}
		return true;
	}

	/*
	 * criar os staatements para os nossos sqls serem executados no caso as
	 * instruções cruas do sql
	 */
	public Statement criarStatement() {
		try {
			return this.conexao.createStatement();
		} catch (SQLException e) {
			System.out.println("[BurkingCash] Erro ao criar statement.");
			return null;
		}
	}

	public PreparedStatement criarPreparedStatement(String sql) {
		try {
			return this.conexao.prepareStatement(sql);
		} catch (SQLException e) {
			System.out.println("[BurkingCash] Erro ao criar prepared statement.");
			System.out.println("[BurkingCash] " + e.getMessage());
			return null;
		}
	}

	public Connection getConexao() {
		return this.conexao;
	}
}
