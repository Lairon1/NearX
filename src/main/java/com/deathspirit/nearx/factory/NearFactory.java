package com.deathspirit.nearx.factory;


import com.deathspirit.nearx.near.Near;
import org.bukkit.configuration.file.FileConfiguration;

public class NearFactory {

    public Near createNear(FileConfiguration config, String path){
        return (Near) config.get(path);
    }

}


