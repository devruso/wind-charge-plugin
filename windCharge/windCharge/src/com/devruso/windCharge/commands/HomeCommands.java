package com.devruso.windCharge.commands;

import com.devruso.windCharge.WindCharge;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommands implements CommandExecutor {

    private final WindCharge plugin;

    public HomeCommands(WindCharge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem efetuar esse comando");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Uso correto: /sethome <nome>");
                return true;
            }
            String homeName = args[0];
            Location location = player.getLocation();
            plugin.getDatabaseManager().saveHome(player.getUniqueId(), homeName, location);
            player.sendMessage(ChatColor.GREEN + "Teleport para: " + homeName + " configurado!");
        } else if (cmd.getName().equalsIgnoreCase("teleport")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Uso correto: /teleport <nome>");
                return true;
            }
            String homeName = args[0];
            Location home = plugin.getDatabaseManager().getHome(player.getUniqueId(), homeName);
            if (home != null) {
                player.teleport(home);
                player.sendMessage(ChatColor.GREEN + "Teletransportado para: " + homeName + "!");
            } else {
                player.sendMessage(ChatColor.RED + "Home " + homeName + " não encontrada!");
            }
        }

        return true;
    }
}
