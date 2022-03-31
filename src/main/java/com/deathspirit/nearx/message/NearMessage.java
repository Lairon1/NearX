package com.deathspirit.nearx.message;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum NearMessage {

    DONT_HAVE_PERMISSION, RELOAD_CONFIG, ONLY_PLAYER, NEAR_NOT_FOUND, NEAR_MESSAGE, NEAR_PART, NEAR_EMPTY;

    private static FileConfiguration config;

    public static void load(FileConfiguration conf){
        config = conf;
    }

    public String toMessage(){
        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.PREFIX"));
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + toRawMessage());
    }

    public String toRawMessage(){
        return config.getString("Messages." + name());
    }

    public String toColorMessage(){
        return ChatColor.translateAlternateColorCodes('&', toRawMessage());
    }

}
