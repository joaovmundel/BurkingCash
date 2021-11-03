package me.joao.cash;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.joao.cash.conexao.Querys;

public class Comandos implements CommandExecutor {
	public static boolean debug = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

		if (cmd.getName().equalsIgnoreCase("devtestecash")) {
			if (sender.hasPermission("cash.admin")) {
				TopCash tc = new TopCash();
				tc.sortTop();
				sender.sendMessage("TOP CASH:");
				sender.sendMessage("1. " + tc.getTop1() + " - " + tc.getCash1());
				sender.sendMessage("2. " + tc.getTop2() + " - " + tc.getCash2());
				sender.sendMessage("3. " + tc.getTop3() + " - " + tc.getCash3());
				sender.sendMessage("4. " + tc.getTop4() + " - " + tc.getCash4());
				sender.sendMessage("5. " + tc.getTop5() + " - " + tc.getCash5());
			}
		}
		if (cmd.getName().equalsIgnoreCase("cash")) {
			Menu menus = new Menu();
			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (args.length == 0) {
					menus.openPerfil(p);
				}
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("enviar") || args[0].equalsIgnoreCase("pay")) {
						if (args.length == 3) {
							Querys qr = new Querys();
							Player target = Bukkit.getPlayer(args[1]);
							int qtd = Integer.parseInt(args[2]);

							if (qtd > 0) {
								if (Querys.cashes.get(p) >= qtd) {
									qr.removeCash(p, qtd);
									qr.addCash(target, qtd);
									p.sendMessage("§aVoce enviou " + qtd + " cash para " + target.getName() + ".");
									target.sendMessage("§aVoce recebeu " + qtd + " de cash de " + p.getName());
								} else {
									p.sendMessage("§cVoce nao tem cash o suficiente para enviar.");
									return true;
								}
							} else {
								p.sendMessage("§cVoce nao pode enviar valores negativos.");
								return true;
							}
						} else {
							p.sendMessage("§cUso correto /cash enviar [player] [qtd]");
							return true;
						}
					}
				}
				if (args.length == 1) {

					if (args[0].equalsIgnoreCase("debug")) {
						if (p.hasPermission("cash.debug")) {
							if (debug) {
								debug = false;
								p.sendMessage("§6§lCASH §cDebug desativado.");
							} else {
								debug = true;
								p.sendMessage("§6§lCASH §aDebug ativado.");
							}
						} else {
							p.sendMessage("§cVoce nao tem acesso a esse comando.");
							return true;
						}
					}
				}

			}
			if (args.length > 0) {
				ArrayList<String> argumentos = new ArrayList<String>();
				argumentos.add("help");
				argumentos.add("set");
				argumentos.add("add");
				argumentos.add("remove");
				argumentos.add("debug");
				if (argumentos.contains(args[0])) {
					if (args[0].equalsIgnoreCase("help")) {
						if (sender.hasPermission("cash.admin")) {
							sender.sendMessage("");
							sender.sendMessage("§aComandos cash: ");
							sender.sendMessage("§a/cash §2- §aAbre o menu principal.");
							sender.sendMessage("§a/cash help §2- §aMostra os comandos.");
							sender.sendMessage("§a/cash enviar §2- §aEnvia cash a outro player.");
							sender.sendMessage("§c/cash set §4- §cSeta cash de um jogador.");
							sender.sendMessage("§c/cash add §4- §cAdiciona cash a um jogador");
							sender.sendMessage("§c/cash remove §4- §cRemove cash de um jogador.");
							sender.sendMessage("§c/cash debug §4- §cAtiva as mensagens de depuracao (Para Devs).");
							sender.sendMessage("");
						} else {
							sender.sendMessage("");
							sender.sendMessage("§aComandos cash: ");
							sender.sendMessage("§a/cash §2- §aAbre o menu principal.");
							sender.sendMessage("§a/cash help §2- §aMostra os comandos.");
							sender.sendMessage("§a/cash enviar §2- §aEnvia cash a outro player.");
							sender.sendMessage("");
						}
					}
					if (sender.hasPermission("cash.admin")) {
						if (args[0].equalsIgnoreCase("set")) {
							if (args.length == 3) {
								Querys qr = new Querys();
								Player target = Bukkit.getPlayer(args[1]);
								int qtd = Integer.parseInt(args[2]);

								if (qtd > 0) {
									qr.setCash(target, qtd, "nosql");
									sender.sendMessage("§aVoce setou o cash de " + target.getName() + " para " + qtd);
								} else {
									sender.sendMessage("§cVoce nao pode usar numeros negativos");
									return true;
								}
							} else {
								sender.sendMessage("§cUso correto: /cash set [player] [qtd]");
								return true;
							}
						}
						if (args[0].equalsIgnoreCase("add")) {
							if (args.length == 3) {
								Querys qr = new Querys();
								Player target = Bukkit.getPlayer(args[1]);
								int qtd = Integer.parseInt(args[2]);
								if (qtd > 0) {
									qr.addCash(target, qtd);
									sender.sendMessage("§aVoce adicionou " + qtd + " cash para " + target.getName());
								} else {
									sender.sendMessage("§cVoce nao pode usar numeros negativos");
									return true;
								}
							} else {
								sender.sendMessage("§cUso correto: /cash add [player] [qtd]");
								return true;
							}
						}
						if (args[0].equalsIgnoreCase("remove")) {
							if (args.length == 3) {
								Querys qr = new Querys();
								Player target = Bukkit.getPlayer(args[1]);
								int qtd = Integer.parseInt(args[2]);

								if (qtd > 0) {
									if (Querys.cashes.get(target) >= qtd) {
										qr.removeCash(target, qtd);
										sender.sendMessage("§aVoce removeu " + qtd + " cash de " + target.getName());
									} else {
										qr.setCash(target, 0, "nosql");
										sender.sendMessage("§aVoce removeu " + qtd + " cash de " + target.getName());
									}
								} else {
									sender.sendMessage("§cVoce nao pode usar numeros negativos");
									return true;
								}
							} else {
								sender.sendMessage("§cUso correto: /cash remove [player] [qtd]");
								return true;
							}
						}
					} else {
						sender.sendMessage("§cVoce nao tem acesso a esse comando.");
					}
				} else if (!(args[0].equalsIgnoreCase("enviar"))) {
					if (sender.hasPermission("cash.admin")) {
						sender.sendMessage("");
						sender.sendMessage("§aComandos cash: ");
						sender.sendMessage("§a/cash §2- §aAbre o menu principal.");
						sender.sendMessage("§a/cash help §2- §aMostra os comandos.");
						sender.sendMessage("§a/cash enviar §2- §aEnvia cash a outro player.");
						sender.sendMessage("§c/cash set §4- §cSeta cash de um jogador.");
						sender.sendMessage("§c/cash add §4- §cAdiciona cash a um jogador");
						sender.sendMessage("§c/cash remove §4- §cRemove cash de um jogador.");
						sender.sendMessage("");
					} else {
						sender.sendMessage("");
						sender.sendMessage("§aComandos cash: ");
						sender.sendMessage("§a/cash §2- §aAbre o menu principal.");
						sender.sendMessage("§a/cash help §2- §aMostra os comandos.");
						sender.sendMessage("§a/cash enviar §2- §aEnvia cash a outro player.");
						sender.sendMessage("");
					}
				}
			}
		}

		return false;
	}

}
