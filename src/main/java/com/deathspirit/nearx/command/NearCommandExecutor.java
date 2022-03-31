package com.deathspirit.nearx.command;

import com.deathspirit.nearx.NearX;
import com.deathspirit.nearx.manager.RegisteredNear;
import com.deathspirit.nearx.message.NearMessage;
import com.deathspirit.nearx.near.Near;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NearCommandExecutor implements CommandExecutor {

    private NearX main;
    private RegisteredNear registeredNear;


    public NearCommandExecutor(NearX main) {
        this.main = main;
        registeredNear = main.getRegisteredNear();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("reload")){
                Bukkit.getScheduler().runTaskAsynchronously(main, ()-> {
                    main.reloadConfig();
                    NearMessage.load(main.getConfig());
                    int loadedNears = registeredNear.reloadNears();
                    sender.sendMessage(NearMessage.RELOAD_CONFIG.toMessage().replace("{nears}", String.valueOf(loadedNears)));
                });
                return false;
            }

            if(sender instanceof Player player){
                Near near = registeredNear.findNearByID(args[0]);
                if(near == null){
                    player.sendMessage(NearMessage.NEAR_NOT_FOUND.toMessage().replace("{nearname}", args[0]));
                    return false;
                }
                Bukkit.getScheduler().runTaskAsynchronously(main, ()-> near.use(player));

                await(near.use(player));

            }else{
                sender.sendMessage(NearMessage.ONLY_PLAYER.toMessage());
                return false;
            }
        }
        return false;
    }
}
