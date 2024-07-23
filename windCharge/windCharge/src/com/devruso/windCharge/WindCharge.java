package com.devruso.windCharge;

import com.devruso.windCharge.commands.WindChargeCommands;
import com.devruso.windCharge.events.WindChargeEvents;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class WindCharge extends JavaPlugin implements  Listener{

    private double explosionStrength;
    private boolean spawnParticles;
    private double projectileSpeed;

    @Override
    public void onEnable(){
        saveDefaultConfig();
        loadConfigValues();
        getServer().getPluginManager().registerEvents((Listener) this, this);
        getServer().getPluginManager().registerEvents(new WindChargeEvents(),this );
        Objects.requireNonNull(getCommand("getitem")).setExecutor(new WindChargeCommands(this));
        Objects.requireNonNull(getCommand("setwindcharge")).setExecutor(new WindChargeCommands(this));
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Wind Charge]: Plugin enabled");

    }

    @Override
    public void onDisable(){
        getServer()
                .getConsoleSender()
                .sendMessage(ChatColor.RED + "[Wind Charge]: Plugin disabled");
    }

    private void loadConfigValues(){
        ConfigurationSection config = getConfig().getConfigurationSection("windcharge");
        explosionStrength = getConfig().getDouble("windcharge.explosionStrength");
        spawnParticles = getConfig().getBoolean("windcharge.spawnParticles");
        projectileSpeed = getConfig().getDouble("windcharge.projectileSpeed");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Explosion Strength: " + explosionStrength);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Projectile Velocity: " + projectileSpeed);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Spawn Particles: " + spawnParticles);

    }

    public double getExplosionStrength() {
        return getConfig().getDouble("windcharge.explosionStrength");
    }

    public boolean isSpawnParticles() {
        return getConfig().getBoolean("windcharge.spawnParticles");
    }

    public double getProjectileSpeed() {
        return getConfig().getDouble("windcharge.projectileSpeed");
    }

    public void setExplosionStrength(double explosionStrength){
        this.explosionStrength = explosionStrength;
        getConfig().set("windchange.explosionStrength",explosionStrength);
        saveConfig();
    }

    public void setSpawnParticles(boolean spawnParticles){
        this.spawnParticles = spawnParticles;
        getConfig().set("windchange.spawnParticles",spawnParticles);
        saveConfig();
    }

    public void setProjectileSpeed(double projectileSpeed){
        this.projectileSpeed = projectileSpeed;
        getConfig().set("windchange.projectileSpeed",projectileSpeed);
        saveConfig();
    }




}
