package com.devruso.windCharge;

import com.devruso.windCharge.commands.HomeCommands;
import com.devruso.windCharge.commands.HomeConfigCommands;
import com.devruso.windCharge.commands.WindChargeCommands;
import com.devruso.windCharge.database.DatabaseManager;
import com.devruso.windCharge.events.WindChargeEvents;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.Objects;

public class WindCharge extends JavaPlugin implements  Listener{

    private double explosionStrength;
    private boolean spawnParticles;
    private double projectileSpeed;

    private DatabaseManager databaseManager;

    @Override
    public void onEnable(){
        saveDefaultConfig();
        loadConfigValues();
        setupDatabase();

        getServer().getPluginManager().registerEvents((Listener) this, this);
        getServer().getPluginManager().registerEvents(new WindChargeEvents(this),this );

        Objects.requireNonNull(getCommand("getitem")).setExecutor(new WindChargeCommands(this));
        Objects.requireNonNull(getCommand("setwindcharge")).setExecutor(new WindChargeCommands(this));
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new HomeCommands(this));
        Objects.requireNonNull(getCommand("teleport")).setExecutor(new HomeCommands(this));
        Objects.requireNonNull(getCommand("teleportcd")).setExecutor(new HomeConfigCommands(this));
        Objects.requireNonNull(getCommand("teleportparticles")).setExecutor(new HomeConfigCommands(this));

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Wind Charge]: Plugin enabled");

    }

    @Override
    public void onDisable(){
        if (databaseManager != null) {
            databaseManager.closeConnection();
        }
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

    private void setupDatabase() {
        String host = getConfig().getString("mysql.host");
        int port = getConfig().getInt("mysql.port");
        String database = getConfig().getString("mysql.database");
        String username = getConfig().getString("mysql.username");
        String password = getConfig().getString("mysql.password");

        databaseManager = new DatabaseManager(host, database, username, password, port);
        databaseManager.connect();
    }


    public DatabaseManager getDatabaseManager() {
        return databaseManager;
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
