package com.deathspirit.nearx.hook;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EssentialsHook {

    private IEssentials ess;

    public EssentialsHook() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
        if(plugin == null) throw new RuntimeException("Plugin \"Essentials\" not found.");
        if(!plugin.isEnabled()) throw new RuntimeException("Plugin \"Essentials\" is disabled.");
        ess = (IEssentials) plugin;
    }

    public User getUser(Player player){
        return ess.getUser(player);
    }

}
