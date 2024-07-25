package com.devruso.windCharge.commands;

import com.devruso.windCharge.WindCharge;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeCommands implements CommandExecutor {

    private final WindCharge plugin;
    private final Map<UUID, Long> lastTeleportTimes;
    public HomeCommands(WindCharge plugin) {
        this.plugin = plugin;
        this.lastTeleportTimes = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem efetuar esse comando");
            return true;
        }
        Player player = (Player) sender;
        // Segundo para milissegundos
        long cooldown = plugin.getConfig().getLong("home.cooldown") * 1000;
        boolean teleportParticles = plugin.getConfig().getBoolean("home.teleportParticles");
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
            if(isCooldownActive(player, cooldown)){
                player.sendMessage(ChatColor.RED + "Você deve esperar antes de usar o comando novamente!");
                return true;
            }
            String homeName = args[0];
            Location home = plugin.getDatabaseManager().getHome(player.getUniqueId(), homeName);
            if (home != null) {
                player.teleport(home);
                if(teleportParticles){
                    player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, home, 200, 2, 2, 2, 0.1);
                }
                player.sendMessage(ChatColor.GREEN + "Teletransportado para: " + homeName + "!");
                lastTeleportTimes.put(player.getUniqueId(), System.currentTimeMillis());
            } else {
                player.sendMessage(ChatColor.RED + "Home " + homeName + " não encontrada!");
            }
        }

        return true;
    }

    private boolean isCooldownActive(Player player, long cooldown) {
        Long lastTeleportTime = lastTeleportTimes.get(player.getUniqueId());
        return lastTeleportTime != null && (System.currentTimeMillis() - lastTeleportTime) < cooldown;
    }
}
