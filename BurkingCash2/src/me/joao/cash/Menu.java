package me.joao.cash;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import me.joao.cash.conexao.Querys;

public class Menu {
	private long lastUpdate = 0;
	private long cooldownTime = 20 * 60000; // 10min

	public void openRank(Player p) {
		TopCash tc = new TopCash();
		long time = System.currentTimeMillis();
		if (time > lastUpdate + cooldownTime) {
			tc.sortTop();
			lastUpdate = time;
		}
		Inventory inv = Bukkit.createInventory(null, 9 * 3, "Cash_TOP");

		ItemStack t1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemStack t2 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemStack t3 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemStack t4 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemStack t5 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

		ItemStack vidros = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);

		ItemMeta vmeta = vidros.getItemMeta();
		SkullMeta m1 = (SkullMeta) t1.getItemMeta();
		SkullMeta m2 = (SkullMeta) t2.getItemMeta();
		SkullMeta m3 = (SkullMeta) t3.getItemMeta();
		SkullMeta m4 = (SkullMeta) t4.getItemMeta();
		SkullMeta m5 = (SkullMeta) t5.getItemMeta();

		m1.setOwner(tc.getTop1());
		m2.setOwner(tc.getTop2());
		m3.setOwner(tc.getTop3());
		m4.setOwner(tc.getTop4());
		m5.setOwner(tc.getTop5());

		vmeta.setDisplayName("§f");
		m1.setDisplayName("§61. §r" + tc.getTop1());
		m2.setDisplayName("§62. §r" + tc.getTop2());
		m3.setDisplayName("§63. §r" + tc.getTop3());
		m4.setDisplayName("§64. §r" + tc.getTop4());
		m5.setDisplayName("§65. §r" + tc.getTop5());

		ArrayList<String> l1 = new ArrayList<String>();
		ArrayList<String> l2 = new ArrayList<String>();
		ArrayList<String> l3 = new ArrayList<String>();
		ArrayList<String> l4 = new ArrayList<String>();
		ArrayList<String> l5 = new ArrayList<String>();

		l1.add("§6Cash: §e" + tc.getCash1() + " §6✪");
		l2.add("§6Cash: §e" + tc.getCash2() + " §6✪");
		l3.add("§6Cash: §e" + tc.getCash3() + " §6✪");
		l4.add("§6Cash: §e" + tc.getCash4() + " §6✪");
		l5.add("§6Cash: §e" + tc.getCash5() + " §6✪");

		m1.setLore(l1);
		m2.setLore(l2);
		m3.setLore(l3);
		m4.setLore(l4);
		m5.setLore(l5);

		vidros.setItemMeta(vmeta);
		t1.setItemMeta(m1);
		t2.setItemMeta(m2);
		t3.setItemMeta(m3);
		t4.setItemMeta(m4);
		t5.setItemMeta(m5);

		// Rank
		inv.setItem(11, t1);
		inv.setItem(12, t2);
		inv.setItem(13, t3);
		inv.setItem(14, t4);
		inv.setItem(15, t5);
		// Vidros
		inv.setItem(16, vidros);
		inv.setItem(10, vidros);
		inv.setItem(0, vidros);
		inv.setItem(1, vidros);
		inv.setItem(2, vidros);
		inv.setItem(3, vidros);
		inv.setItem(4, vidros);
		inv.setItem(5, vidros);
		inv.setItem(6, vidros);
		inv.setItem(7, vidros);
		inv.setItem(8, vidros);
		inv.setItem(9, vidros);
		inv.setItem(17, vidros);
		inv.setItem(26, vidros);
		inv.setItem(25, vidros);
		inv.setItem(24, vidros);
		inv.setItem(23, vidros);
		inv.setItem(22, vidros);
		inv.setItem(21, vidros);
		inv.setItem(20, vidros);
		inv.setItem(19, vidros);
		inv.setItem(18, vidros);
		inv.setItem(9, vidros);
		p.openInventory(inv);
	}

	public void openPerfil(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9 * 3, "CASH_" + p.getName());

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemStack vidros = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemStack star = new ItemStack(Material.NETHER_STAR);
		ItemStack gold = new ItemStack(Material.GOLD_INGOT);

		SkullMeta smeta = (SkullMeta) skull.getItemMeta();
		ItemMeta vmeta = vidros.getItemMeta();
		ItemMeta starmeta = star.getItemMeta();
		ItemMeta gmeta = gold.getItemMeta();

		smeta.setOwner(p.getName());
		smeta.setDisplayName("§r" + p.getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§6Cash: §e" + Querys.cashes.get(p) + " §6✪");
		smeta.setLore(lore);
		vmeta.setDisplayName("§f");
		starmeta.setDisplayName("§6TOP CASH");
		gmeta.setDisplayName("§6LOJA");

		vidros.setItemMeta(vmeta);
		skull.setItemMeta(smeta);
		star.setItemMeta(starmeta);
		gold.setItemMeta(gmeta);

		inv.setItem(11, skull);
		inv.setItem(13, star);
		inv.setItem(15, gold);
		// Vidros
		inv.setItem(16, vidros);
		inv.setItem(10, vidros);
		inv.setItem(14, vidros);
		inv.setItem(12, vidros);
		inv.setItem(0, vidros);
		inv.setItem(1, vidros);
		inv.setItem(2, vidros);
		inv.setItem(3, vidros);
		inv.setItem(4, vidros);
		inv.setItem(5, vidros);
		inv.setItem(6, vidros);
		inv.setItem(7, vidros);
		inv.setItem(8, vidros);
		inv.setItem(9, vidros);
		inv.setItem(17, vidros);
		inv.setItem(26, vidros);
		inv.setItem(25, vidros);
		inv.setItem(24, vidros);
		inv.setItem(23, vidros);
		inv.setItem(22, vidros);
		inv.setItem(21, vidros);
		inv.setItem(20, vidros);
		inv.setItem(19, vidros);
		inv.setItem(18, vidros);
		inv.setItem(9, vidros);

		p.openInventory(inv);
	}

	public void openLoja(Player p) {

		Inventory inv = Bukkit.createInventory(null, 9 * 5, "CASH_SHOP");

		for (int i = 0; i < 9 * 5; i++) {
			if (Main.getInstance().getConfig().getConfigurationSection("Itens").getKeys(false).contains("" + i)) {
				try {
					String materialName = Main.getInstance().getConfig().getString("Itens." + i + ".Item");
					String displayName = Main.getInstance().getConfig().getString("Itens." + i + ".Name").replace("&",
							"§");
					int price = Main.getInstance().getConfig().getInt("Itens." + i + ".Price");
					ArrayList<String> lore = new ArrayList<String>();

					for (String linha : Main.getInstance().getConfig().getStringList("Itens." + i + ".Lore")) {
						lore.add(linha.replace("&", "§").replace("@price", price + "").replace("@star", "✪"));
					}

					ItemStack mat = new ItemStack(Material.getMaterial(materialName));

					ItemMeta meta = mat.getItemMeta();

					meta.setDisplayName(displayName);
					meta.setLore(lore);
					mat.setItemMeta(meta);

					inv.setItem(i, mat);

				} catch (NullPointerException e) {
					if (p.hasPermission("cash.debug")) {
						if (Comandos.debug) {
							p.sendMessage("Valor de i: " + i);
							p.sendMessage("Material encontrado: "
									+ Main.getInstance().getConfig().getString("Itens." + i + ".Item"));
							p.sendMessage("Size da Section: " + Main.getInstance().getConfig()
									.getConfigurationSection("Itens").getKeys(false).size());
							p.sendMessage("Teste getMaterial: " + Material.getMaterial("DIAMOND").toString());
							p.getInventory().addItem(new ItemStack(Material.getMaterial("DIAMOND")));
						}
						System.out.println("[BurkingCash] Erro ao carregar shop.");
						System.out.println("[BurkingCash] " + e.getMessage());
					}
					ItemStack stone = new ItemStack(Material.STONE);
					ItemMeta sm = stone.getItemMeta();
					sm.setDisplayName("§6§lErro de config");
					stone.setItemMeta(sm);
					inv.setItem(i, stone);
				}
			}
		}

		p.openInventory(inv);
	}

}