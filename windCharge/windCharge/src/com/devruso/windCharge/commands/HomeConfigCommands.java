package com.devruso.windCharge.commands;

import com.devruso.windCharge.WindCharge;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeConfigCommands implements CommandExecutor {

    private WindCharge plugin;

    public HomeConfigCommands(WindCharge plugin){
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem efetuar esse comando");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("teleportcd")){
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Uso correto: /teleportcd <tempo_em_segundos>");
                return true;
            }
            try{
                int cooldown = Integer.parseInt(args[0]);
                plugin.getConfig().set("home.cooldown", cooldown);
                plugin.saveConfig();
                player.sendMessage(ChatColor.GREEN + "Cooldown do teleporte configurado para: " + cooldown + " segundos.");
            }catch (NumberFormatException e){
                player.sendMessage(ChatColor.RED + "Insira um número válido.");
            }
        }
        else if(cmd.getName().equalsIgnoreCase("teleportparticles")){
            boolean currentSetting = plugin.getConfig().getBoolean("home.teleportParticles");
            plugin.getConfig().set("home.teleportParticles", !currentSetting);
            plugin.saveConfig();
            player.sendMessage(ChatColor.GREEN + "Exibição de partículas para teleporte: "
                    + (!currentSetting ? "ativada" : "desativada") + ".");

        }

        return true;
    }



}
