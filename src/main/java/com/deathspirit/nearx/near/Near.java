package com.deathspirit.nearx.near;

import com.deathspirit.nearx.message.NearMessage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Near implements ConfigurationSerializable {

    private String id;
    private int radius;
    private int price;
    private String[] enableWorlds;
    private NearType type;
    private boolean ignoreInvisible;
    private boolean ignoreVanish;
    private int coolDown;
    private String permission;
    private boolean ignoreSpectator;


    public void use(Player player) {
        if (!player.hasPermission(getPermission())) {
            player.sendMessage(NearMessage.DONT_HAVE_PERMISSION.toString());
            return;
        }
        List<Player> players = player.getWorld().getPlayers();
        players.remove(player);

        players = players
                .stream()
                .filter(sortPlayer -> sortPlayer.getLocation().distance(player.getLocation()) <= getRadius())
                .sorted((o1, o2) -> (int) (o1.getLocation().distance(player.getLocation()) - o2.getLocation().distance(player.getLocation())))
                .collect(Collectors.toList());

        if (players.size() == 0) {
            player.sendMessage(NearMessage.NEAR_EMPTY.toMessage());
            return;
        }

        ArrayList<String> nearPlayersParts = new ArrayList<>();


        switch (getType()) {
            case DISTANCE:
                for (Player nearPlayer :
                        players) {
                    nearPlayersParts.add(NearMessage.NEAR_PART.toColorMessage()
                            .replace("{player}", ChatColor.stripColor(nearPlayer.getDisplayName()))
                            .replace("{view}", String.valueOf(((int) nearPlayer.getLocation().distance(player.getLocation())))));
                }

                break;
        }
        String nearMessage = NearMessage.NEAR_MESSAGE.toMessage() + "\n" + String.join(", ", nearPlayersParts);
        player.sendMessage(nearMessage);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String[] getEnableWorlds() {
        return enableWorlds;
    }

    public void setEnableWorlds(String[] enableWorlds) {
        this.enableWorlds = enableWorlds;
    }

    public NearType getType() {
        return type;
    }

    public void setType(NearType type) {
        this.type = type;
    }

    public boolean isIgnoreInvisible() {
        return ignoreInvisible;
    }

    public void setIgnoreInvisible(boolean ignoreInvisible) {
        this.ignoreInvisible = ignoreInvisible;
    }

    public boolean isIgnoreVanish() {
        return ignoreVanish;
    }

    public void setIgnoreVanish(boolean ignoreVanish) {
        this.ignoreVanish = ignoreVanish;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isIgnoreSpectator() {
        return ignoreSpectator;
    }

    public void setIgnoreSpectator(boolean ignoreSpectator) {
        this.ignoreSpectator = ignoreSpectator;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Near near) return near.getId() == id;
        else return false;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> params = new HashMap<>();
        params.put("Radius", radius);
        params.put("Price", price);
        if (enableWorlds != null) params.put("EnableWorlds", String.join(", ", enableWorlds));
        params.put("Type", type.name());
        params.put("IgnoreInvisible", ignoreInvisible);
        params.put("IgnoreVanish", ignoreVanish);
        params.put("CoolDown", coolDown);
        params.put("Permission", permission);
        params.put("ID", id);
        params.put("IgnoreSpectator", ignoreSpectator);
        return params;
    }

    public static Near deserialize(Map<String, Object> params) {
        Near near = new Near();
        if (params.containsKey("Radius")) near.setRadius((int) params.get("Radius"));
        if (params.containsKey("Price")) near.setPrice((int) params.get("Price"));
        if (params.containsKey("EnableWorlds")) near.setEnableWorlds(((String) params.get("EnableWorlds")).split(", "));
        if (params.containsKey("Type")) near.setType(NearType.valueOf((String) params.get("Type")));
        if (params.containsKey("HideInvisible")) near.setIgnoreInvisible((boolean) params.get("IgnoreInvisible"));
        if (params.containsKey("HideVanish")) near.setIgnoreVanish((boolean) params.get("IgnoreVanish"));
        if (params.containsKey("CoolDown")) near.setCoolDown((int) params.get("CoolDown"));
        if (params.containsKey("Permission")) near.setPermission((String) params.get("Permission"));
        if (params.containsKey("ID")) near.setId((String) params.get("ID"));
        if (params.containsKey("IgnoreSpectator")) near.setIgnoreSpectator((boolean) params.get("IgnoreSpectator"));
        return near;
    }
}
