package me.joao.cash.conexao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

import me.joao.cash.Main;

public class NovasQueries {
	private String driver = Main.getInstance().getConfig().getString("MySQL.Driver").toLowerCase();

	public boolean exists(Player p) {
		boolean result = false;
		ResultSet rs;
		Conexao con = new Conexao();
		con.conectar();
		String query = "select * from tbl_cash;";
		Statement pstmt = con.criarStatement();

		try {
			rs = pstmt.executeQuery(query);
			if (!driver.equalsIgnoreCase("sqlite")) {
				rs.first();
			}
			rs.next();
			result = rs.getString("UUID").equalsIgnoreCase(p.getUniqueId().toString());

		} catch (SQLException e) {
			System.out.println("Erro ao checar existencia de player novo.");
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("[CashErro] " + e.getMessage());
			}
			con.desconectar();
		}
		return result;
	}

	public boolean exists(String nick) {
		boolean result = false;
		ResultSet rs;
		Conexao con = new Conexao();
		con.conectar();
		String query = "select * from tbl_cash;";
		Statement pstmt = con.criarStatement();

		try {
			rs = pstmt.executeQuery(query);
			rs.first();
			result = rs.getString("Nick").equalsIgnoreCase(nick);

		} catch (SQLException e) {
			System.out.println("Erro ao checar existencia de player novo.");
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("[CashErro] " + e.getMessage());
			}
			con.desconectar();
		}
		return result;
	}

}
