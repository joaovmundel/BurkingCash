package me.joao.cash;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.nickuc.login.api.nLoginAPI;
import com.nickuc.login.api.events.RegisterEvent;
import com.nickuc.login.core.nLogin;

import me.joao.cash.conexao.Conexao;
import me.joao.cash.conexao.Querys;

public class Eventos implements Listener {

	nLoginAPI api = new nLoginAPI();
	nLogin core = new nLogin();

	@EventHandler
	public void onRegister(RegisterEvent e) {
		Player p = e.getPlayer();
		Querys nqr = new Querys(new Conexao());
		if (nqr.exists(p)) {
			Querys.cashes.put(p, nqr.getCash(p));
		} else {
			CashUser novato = new CashUser();
			novato.setCash(0);
			novato.setNick(p.getName());
			novato.setUuid(p.getUniqueId().toString());
			Querys.cashes.put(p, 0);
			nqr.inserir(novato);
		}
	}

	@EventHandler
	public void onJoin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		Querys nqr = new Querys(new Conexao());
		if (nqr.exists(p)) {
			Querys.cashes.put(p, nqr.getCash(p));
		} else {
			CashUser novato = new CashUser();
			novato.setCash(0);
			novato.setNick(p.getName());
			novato.setUuid(p.getUniqueId().toString());
			Querys.cashes.put(p, 0);
			nqr.inserir(novato);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (Querys.cashes.containsKey(p)) {
			Querys qr = new Querys(new Conexao());
			qr.setCash(p, Querys.cashes.get(p));
			Querys.cashes.remove(p);
		} else {
			Querys qr = new Querys(new Conexao());
			qr.setCash(p, 0);
		}
	}

	@EventHandler
	public void interact(InventoryInteractEvent e) {
		if (e.getInventory().getTitle().toUpperCase().contains("CASH")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void menuInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equalsIgnoreCase("CASH_" + p.getName())) {
			net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(e.getCurrentItem());
			if (e.getCurrentItem().getType() == Material.GOLD_INGOT) {
				if (nmsItem.hasTag()) {
					if (nmsItem.getTag().toString().toLowerCase().contains("§6loja")) {
						Menu menus = new Menu();
						p.closeInventory();
						menus.openLoja(p);
					}
				}
			}
			if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
				if (nmsItem.hasTag()) {
					if (nmsItem.getTag().toString().toLowerCase().contains("§6top cash")) {
						Menu menus = new Menu();
						p.closeInventory();
						menus.openRank(p);
					}

				}
			}
			e.setCancelled(true);
		}
		if (e.getInventory().getTitle().equalsIgnoreCase("Cash_SHOP")) {
			if (e.getClickedInventory().getTitle().equalsIgnoreCase("Cash_SHOP")) {
				int slot = e.getSlot();
				ShopItem si = new ShopItem(slot);
				if (e.getCurrentItem().getType() == si.getMaterial()) {
					si.buyItem(p, slot);
				}
			}
			e.setCancelled(true);
		}
		if (e.getInventory().getTitle().equalsIgnoreCase("Cash_TOP")) {

			e.setCancelled(true);
		}
	}
}
