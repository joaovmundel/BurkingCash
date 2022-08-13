package me.joao.cash.conexao;

import java.sql.SQLException;
import java.sql.Statement;

import me.joao.cash.Main;

public class CriarBanco {
	private final Conexao conexao;

	public CriarBanco(Conexao con) {
		this.conexao = con;
	}

	public void createTable() {
		String sql = null;
		if (Main.getInstance().getConfig().getString("MySQL.Driver").toLowerCase().equalsIgnoreCase("sqlite")) {
			sql = "CREATE TABLE IF NOT EXISTS tbl_cash(id integer PRIMARY KEY AUTOINCREMENT, UUID text NOT NULL UNIQUE, Nick text NOT NULL, Cash integer DEFAULT 0);";
		} else if (Main.getInstance().getConfig().getString("MySQL.Driver").toLowerCase().equalsIgnoreCase("mariadb")) {
			sql = "CREATE TABLE IF NOT EXISTS tbl_cash(id integer PRIMARY KEY AUTO_INCREMENT, UUID text NOT NULL UNIQUE, Nick text NOT NULL, Cash integer DEFAULT 0);";
		}
		conexao.conectar();
		Statement stmt = conexao.criarStatement();

		try {
			stmt.execute(sql);
			System.out.println("Tabela cash criada");
		} catch (SQLException e) {
			System.out.println("Erro ao criar tabela cash");
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conexao.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
