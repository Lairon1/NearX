package com.deathspirit.nearx;

import com.deathspirit.nearx.command.NearCommandExecutor;
import com.deathspirit.nearx.hook.EssentialsHook;
import com.deathspirit.nearx.loader.NearLoader;
import com.deathspirit.nearx.manager.RegisteredNear;
import com.deathspirit.nearx.message.NearMessage;
import com.deathspirit.nearx.near.Near;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NearX extends JavaPlugin {

    private File configFile = new File(getDataFolder() + File.separator + "config.yml");
    private File nearsFile = new File(getDataFolder() + File.separator + "nears.yml");
    private FileConfiguration nearsYml;

    private RegisteredNear registeredNear;
    private NearLoader nearLoader;

    private EssentialsHook essentialsHook;

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Near.class);
        checkConfigs();

        nearsYml = YamlConfiguration.loadConfiguration(nearsFile);

        nearLoader = new NearLoader(nearsFile, this);

        registeredNear = new RegisteredNear(nearLoader);

        registeredNear.reloadNears();

        Bukkit.getPluginCommand("nearx").setExecutor(new NearCommandExecutor(this));

        NearMessage.load(getConfig());

        essentialsHook = new EssentialsHook();
    }

    private void checkConfigs(){
        if(!configFile.exists()){
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }

    @Override
    public void onDisable() {

    }

    public RegisteredNear getRegisteredNear() {
        return registeredNear;
    }
}
