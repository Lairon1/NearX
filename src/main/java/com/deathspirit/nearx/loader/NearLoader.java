package com.deathspirit.nearx.loader;

import com.deathspirit.nearx.NearX;
import com.deathspirit.nearx.factory.NearFactory;
import com.deathspirit.nearx.near.Near;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public class NearLoader {

    private File nearConfigFile;
    private NearX nearX;

    private final String path = "Nears";
    private final NearFactory nearFactory = new NearFactory();

    public NearLoader(File nearConfigFile, NearX nearX) {
        this.nearConfigFile = nearConfigFile;
        this.nearX = nearX;

        if(!nearConfigFile.exists()) nearX.saveResource("nears.yml", false);
    }

    public Near[] load(){
        FileConfiguration nearConfig = YamlConfiguration.loadConfiguration(nearConfigFile);

        ConfigurationSection configurationSection = nearConfig.getConfigurationSection(path);

        if(configurationSection == null) return new Near[]{};

        Set<String> keys = configurationSection.getKeys(false);

        if(keys == null) return new Near[]{};

        String[] nearPaths = keys.toArray(new String[]{});

        Near[] nears = new Near[nearPaths.length];
        for (int i = 0; i < nearPaths.length; i++) nears[i] = nearFactory.createNear(nearConfig, path + "." + nearPaths[i]);
        return nears;
    }

}
