package com.devruso.windCharge.commands;

import com.devruso.windCharge.WindCharge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WindChargeCommands implements CommandExecutor {

    private final WindCharge plugin;

    public WindChargeCommands(WindCharge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem efetuar esse comando");
            return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("getitem")){
            ItemStack windcharge = new ItemStack(Material.WIND_CHARGE, 90);
            player.getInventory().addItem(windcharge);
            player.sendMessage(ChatColor.GREEN + "90 Wind Charges foram adicionados ao seu inventario");
        }

        else if (cmd.getName().equalsIgnoreCase("setwindcharge")){
            if(args.length < 2){
                player.sendMessage(ChatColor.RED + "Uso correto /setwindcharge <propriedade> <valor>");
                return true;
            }

            String property = args[0].toLowerCase();
            String value = args[1];

            switch (property) {
                case "explosionstrength":
                    try {
                        double explosionStrength = Double.parseDouble(value);
                        plugin.getConfig().set("windcharge.explosionStrength",explosionStrength);
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "Força da explosão configurada para: " + explosionStrength);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Valor inválido para força da explosão.");
                    }
                    break;
                case "spawnparticles":
                    try{
                        boolean spawnParticles = Boolean.parseBoolean(value);
                        plugin.getConfig().set("windcharge.spawnParticles",spawnParticles);
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "Partículas ativadas para Wind Charge: " + spawnParticles);
                        break;
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Valor inválido para spawn particles. Use true ou false.");
                    }
                case "projectilespeed":
                    try {
                        double projectileSpeed = Double.parseDouble(value);
                        plugin.getConfig().set("windcharge.projectileSpeed",projectileSpeed);
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "Velocidade do projétil configurada para: " + projectileSpeed);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Valor inválido para velocidade do projétil.");
                    }
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Propriedade desconhecida: " + property);
                    break;
            }
        }

        return true;
    }


}
