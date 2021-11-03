package me.joao.cash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.joao.cash.conexao.Querys;

public class ShopItem {
	private Material material;
	private String displayName;
	private List<String> lore = new ArrayList<String>();
	private HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
	private List<String> command = new ArrayList<String>();
	private int qtd;
	private int price;

	public ShopItem() {

	}

	public ShopItem(int slot) {
		for (int i = 0; i < 9 * 5; i++) {
			if (Main.getInstance().getConfig().getConfigurationSection("Itens").getKeys(false).contains("" + slot)) {
				this.material = Material
						.getMaterial(Main.getInstance().getConfig().getString("Itens." + slot + ".Item"));
				this.displayName = Main.getInstance().getConfig().getString("Itens." + slot + ".Name").replace("&",
						"§");
				this.lore = Main.getInstance().getConfig().getStringList("Itens." + slot + ".Lore");
				this.qtd = Main.getInstance().getConfig().getInt("Itens." + slot + ".Qtd");
				this.price = Main.getInstance().getConfig().getInt("Itens." + slot + ".Price");
				this.command = Main.getInstance().getConfig().getStringList("Itens." + slot + ".Commands");
				if (Main.getInstance().getConfig().getStringList("Itens." + slot + ".Enchants") != null
						&& Main.getInstance().getConfig().getStringList("Itens." + slot + ".Enchants").size() > 0
						&& Main.getInstance().getConfig().getStringList("Itens." + slot + ".Enchants").get(0) != "") {
					for (String enchants : Main.getInstance().getConfig()
							.getStringList("Itens." + slot + ".Enchants")) {
						Enchantment encantamento = Enchantment.getByName(enchants.split(":")[0]);
						int level = Integer.parseInt(enchants.split(":")[1]);
						this.enchants.put(encantamento, level);
					}
				}
			}
		}
	}

	public void buyItem(Player p, int slot) {
		if (Querys.cashes.get(p) >= this.price) {
			ItemStack item = new ItemStack(this.material, this.qtd);
			ItemMeta imeta = item.getItemMeta();

			imeta.setDisplayName(this.getDisplayName());
			List<String> lore = new ArrayList<String>();
			for (String linhas : this.getLore()) {
				lore.add(linhas.replace("&", "§").replace("@price", price + "").replace("@star", "✪"));
			}
			imeta.setLore(lore);
			if (this.enchants != null && this.enchants.size() > 0) {
				for (Enchantment enc : this.enchants.keySet()) {
					imeta.addEnchant(enc, this.enchants.get(enc), true);
				}
			}
			if (this.command.size() > 0) {
				for (String cmds : this.command) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds);
				}
			}
			item.setItemMeta(imeta);
			Querys qr = new Querys();
			qr.removeCash(p, price);
			p.getInventory().addItem(item);
			p.closeInventory();
			p.sendMessage("§aVoce comprou um item no shop de cash por " + price + " cash");
		} else {
			p.sendMessage("§cVoce nao tem cash suficiente.");
			p.closeInventory();
		}
	}

	@Override
	public String toString() {
		String si = "(Nome: " + this.getDisplayName() + ", Preco: " + this.getPrice() + ", QTD: " + this.getQtd()
				+ ", Enchants: " + this.getEnchants() + ", Lore: " + this.getLore() + ")";

		return si;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public HashMap<Enchantment, Integer> getEnchants() {
		return enchants;
	}

	public void setEnchants(HashMap<Enchantment, Integer> enchants) {
		this.enchants = enchants;
	}

	public List<String> getCommand() {
		return command;
	}

	public void setCommand(List<String> command) {
		this.command = command;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
