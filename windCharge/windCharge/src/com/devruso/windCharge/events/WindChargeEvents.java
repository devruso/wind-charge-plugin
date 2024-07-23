package com.devruso.windCharge.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WindChargeEvents implements Listener {

    private final JavaPlugin plugin;

    public WindChargeEvents(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack windCharge = new ItemStack(Material.WIND_CHARGE, 99);
        player.getInventory().addItem(windCharge);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Bem vindo ao servidor! Se divirta com o Wind Charge! ");
    }

    @EventHandler
    public void onObjectLaunch(ProjectileLaunchEvent event){

        Projectile projectile = (Projectile) event.getEntity();
        if(projectile.getShooter() instanceof Player){
            Player player = (Player) projectile.getShooter();

            if(player.getInventory().getItemInMainHand().getType() == Material.WIND_CHARGE){
                boolean spawnParticles = plugin.getConfig().getBoolean("windcharge.spawnParticles");
                double projectileSpeed = plugin.getConfig().getDouble("windcharge.projectileSpeed");
                Bukkit.getLogger().info("Projectile speed: " + projectileSpeed);
                projectile.setVelocity(projectile.getVelocity().multiply(projectileSpeed));

                if(spawnParticles){
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            if(projectile.isDead() || !projectile.isValid()){
                                cancel();
                            }
                            projectile.getWorld().spawnParticle(Particle.CLOUD, projectile.getLocation(), 5, 0.5, 0.2, 0.2, 0.02);                        }

                    }.runTaskTimer((Plugin) plugin, 0L, 1L );
                    // adiciona efeito de explosão ao contato
                    projectile.getServer().getPluginManager().registerEvents(new Listener() {
                        @EventHandler
                        public void onProjectileHit(ProjectileHitEvent hitEvent){
                            if(hitEvent.getEntity() == projectile){
                                double explosionStrength = plugin.getConfig().getDouble("windcharge.explosionStrength");
                                projectile.getWorld().createExplosion(projectile.getLocation(), (float) explosionStrength);
                                ProjectileHitEvent.getHandlerList().unregister(this);
                            }
                        }
                    }, plugin);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof  Projectile){
            Projectile projectile = (Projectile) entity;
            if (projectile.getShooter() instanceof Player) {
                Player player = (Player) projectile.getShooter();
                if (player.getInventory().getItemInMainHand().getType() == Material.WIND_CHARGE) {
                    double explosionStrength = plugin.getConfig().getDouble("windcharge.explosionStrength");
                    projectile.getWorld().createExplosion(projectile.getLocation(), (float) explosionStrength);
                }
            }
        }
    }

}
