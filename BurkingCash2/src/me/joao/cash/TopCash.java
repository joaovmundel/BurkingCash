package me.joao.cash;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.joao.cash.conexao.Conexao;

public class TopCash {

	private String top1 = "#Vago", top2 = "#Vago", top3 = "#Vago", top4 = "#Vago", top5 = "#Vago";
	private int cash1 = 0, cash2 = 0, cash3 = 0, cash4 = 0, cash5 = 0;

	public void sortTop() {
		Conexao con = new Conexao();

		ResultSet rs = null;
		String sql = "SELECT * FROM tbl_cash order by cash desc limit 5;";

		PreparedStatement pstmt = null;

		con.conectar();
		pstmt = con.criarPreparedStatement(sql);

		try {
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt("Cash") > cash1) {
					cash1 = rs.getInt("Cash");
					top1 = rs.getString("Nick");
				} else if (rs.getInt("Cash") > cash2 && rs.getInt("Cash") < cash1) {
					cash2 = rs.getInt("Cash");
					top2 = rs.getString("Nick");
				} else if (rs.getInt("Cash") > cash3 && rs.getInt("Cash") < cash2) {
					cash3 = rs.getInt("Cash");
					top3 = rs.getString("Nick");
				} else if (rs.getInt("Cash") > cash4 && rs.getInt("Cash") < cash3) {
					cash4 = rs.getInt("Cash");
					top4 = rs.getString("Nick");
				} else if (rs.getInt("Cash") > cash5 && rs.getInt("Cash") < cash4) {
					cash5 = rs.getInt("Cash");
					top5 = rs.getString("Nick");
				}
			}
		} catch (SQLException e) {
			System.out.println("[BurkingCash] Erro ao atualizar top cash");
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			con.desconectar();
		}

	}

	public String getTop1() {
		return top1;
	}

	public void setTop1(String top1) {
		this.top1 = top1;
	}

	public String getTop2() {
		return top2;
	}

	public void setTop2(String top2) {
		this.top2 = top2;
	}

	public String getTop3() {
		return top3;
	}

	public void setTop3(String top3) {
		this.top3 = top3;
	}

	public String getTop4() {
		return top4;
	}

	public void setTop4(String top4) {
		this.top4 = top4;
	}

	public String getTop5() {
		return top5;
	}

	public void setTop5(String top5) {
		this.top5 = top5;
	}

	public int getCash1() {
		return cash1;
	}

	public void setCash1(int cash1) {
		this.cash1 = cash1;
	}

	public int getCash2() {
		return cash2;
	}

	public void setCash2(int cash2) {
		this.cash2 = cash2;
	}

	public int getCash3() {
		return cash3;
	}

	public void setCash3(int cash3) {
		this.cash3 = cash3;
	}

	public int getCash4() {
		return cash4;
	}

	public void setCash4(int cash4) {
		this.cash4 = cash4;
	}

	public int getCash5() {
		return cash5;
	}

	public void setCash5(int cash5) {
		this.cash5 = cash5;
	}
}
