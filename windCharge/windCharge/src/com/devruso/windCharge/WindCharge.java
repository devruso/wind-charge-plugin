package com.devruso.windCharge;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class WindCharge extends JavaPlugin {

    @Override
    public void onEnable(){
        getServer()
        .getConsoleSender()
        .sendMessage(ChatColor.GREEN + "[Wind Charge]: Plugin enabled");
    }

    @Override
    public void onDisable(){
        getServer()
                .getConsoleSender()
                .sendMessage(ChatColor.RED + "[Wind Charge]: Plugin disabled");
    }

}
