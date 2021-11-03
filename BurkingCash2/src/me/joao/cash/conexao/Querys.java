package me.joao.cash.conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.entity.Player;

import me.joao.cash.CashUser;
import me.joao.cash.Main;

public class Querys {

	private String plugin = "[BurkingCash]";
	public static HashMap<Player, Integer> cashes = new HashMap<Player, Integer>();
	protected Conexao con;
	private String db = "tbl_cash";
	private String driver = Main.getInstance().getConfig().getString("MySQL.Driver").toLowerCase();

	public Querys(Conexao con) {
		this.con = con;
	}

	public Querys() {

	}

	public void inserir(CashUser cash) {
		String sqlInsert = "INSERT INTO " + db + " (id,UUID,Nick,Cash) VALUES(?,?,?,?);";
		con.conectar();
		PreparedStatement pstmt = this.con.criarPreparedStatement(sqlInsert);
		try {
			pstmt.setString(1, null);
			pstmt.setString(2, cash.getUuid());
			pstmt.setString(3, cash.getNick());
			pstmt.setInt(4, cash.getCash());
			int resultado = pstmt.executeUpdate();

			if (resultado == 1) {
				System.out.println("CashUser inserido.");
			} else {
				System.out.println("Houve um erro ao inserir o CashUser.");
			}
		} catch (SQLException e) {
			System.out.println("[BurkingCash] Erro ao inserir dados no banco.");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			con.desconectar();
		}
	}

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
			if (!driver.equalsIgnoreCase("sqlite")) {
				rs.first();
			}
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

	public int getCash(Player p) {
		String query = "select Cash from " + db + " where UUID = ?;";
		int cash = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con.conectar();
		pstmt = con.criarPreparedStatement(query);
		try {
			pstmt.setString(1, p.getUniqueId().toString());
			rs = pstmt.executeQuery();
			cash = rs.getInt("Cash");
		} catch (SQLException e) {
			System.out.println("Erro ao checar valores no banco");
		} finally {
			try {
				pstmt.close();
				rs.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println("erro ao fechar conexao");
			}
		}
		return cash;
	}


	public int getCash(String nick) {
		String query = "select Cash from " + db + " where Nick = ?;";
		int cash = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con.conectar();
		pstmt = con.criarPreparedStatement(query);
		try {
			pstmt.setString(1, nick);
			rs = pstmt.executeQuery();
			cash = rs.getInt("Cash");
		} catch (SQLException e) {
			System.out.println("Erro ao checar valores no banco");
		} finally {
			try {
				pstmt.close();
				rs.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println("erro ao fechar conexao");
			}
		}
		return cash;
	}

	public void setCash(Player p, int qtd) {
		String query = "update " + db + " set Cash = ? where UUID = ?;";

		PreparedStatement pstmt = null;
		con.conectar();
		pstmt = con.criarPreparedStatement(query);

		try {
			pstmt.setInt(1, qtd);
			pstmt.setString(2, p.getUniqueId().toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao setar cash do jogador.");
		} finally {
			try {
				pstmt.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println(plugin + "Erro ao desconectar/fechar statement");
				System.out.println(plugin + e.getMessage());
			}

		}
	}

	public void addCash(Player p, int qtd) {
		if (qtd > 0) {
			cashes.put(p, cashes.get(p) + qtd);
		}
	}

	public void setCash(Player p, int qtd, String nosql) {
		cashes.put(p, qtd);
	}

	public void removeCash(Player p, int qtd) {
		if (qtd >= cashes.get(p)) {
			cashes.put(p, cashes.get(p) - qtd);
		} else {
			cashes.put(p, 0);
		}
	}

	public void dropTable() {
		String sql = "drop table " + db + ";";
		this.con.conectar();

		Statement stmt = this.con.criarStatement();
		try {
			stmt.executeUpdate(sql);
			System.out.println("Tabela deletada.");
		} catch (SQLException e) {
			System.out.println("Erro ao deletar a tabela.");
		} finally {
			try {
				stmt.close();
				con.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.desconectar();
		}
	}

	public void removePlayer(Player p) {
		String sql = "delete from " + db + " where UUID = " + p.getUniqueId().toString() + ";";
		con.conectar();
		Statement stmt = con.criarStatement();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Erro ao deletar player");
		} finally {
			try {
				stmt.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println("Erro ao desconectar do banco");
			}
		}
	}

	public void removePlayer(String uid) {
		String sql = "delete from " + db + " where UUID = " + uid + ";";
		con.conectar();
		Statement stmt = con.criarStatement();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Erro ao deletar player");
		} finally {
			try {
				stmt.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println("Erro ao desconectar do banco");
			}
		}
	}
}
