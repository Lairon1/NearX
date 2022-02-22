package com.deathspirit.nearx.near;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Near implements ConfigurationSerializable {

    private int radius;
    private double price;
    private ArrayList<String> enableWorlds;
    private NearType type;
    private boolean hideInvisible;
    private boolean hideVanish;


    public int getRadius() {
        return radius;
    }

    
    public void setRadius(int radius) {
        this.radius = radius;
    }

    
    public double getPrice() {
        return price;
    }

    
    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<String> getEnableWorlds() {
        return enableWorlds;
    }

    public void setEnableWorlds(ArrayList<String> enableWorlds) {
        this.enableWorlds = enableWorlds;
    }

    public NearType getType() {
        return type;
    }

    
    public void setType(NearType type) {
        this.type = type;
    }

    
    public boolean isHideInvisible() {
        return hideInvisible;
    }

    
    public void setHideInvisible(boolean hideInvisible) {
        this.hideInvisible = hideInvisible;
    }

    
    public boolean isHideVanish() {
        return hideVanish;
    }

    
    public void setHideVanish(boolean hideVanish) {
        this.hideVanish = hideVanish;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> params = new HashMap<>();
        params.put("Radius", radius);
        params.put("Price", price);
        params.put("EnableWorlds", String.join(" ", enableWorlds));
        params.put("Type", type);
        params.put("HideInvisible", hideInvisible);
        params.put("HideVanish", hideVanish);
        return params;
    }

    public static Near deserialize(Map<String, Object> params){
        Near near = new Near();
        if(params.containsKey("Radius")) near.setRadius((int) params.get("Radius"));
        if(params.containsKey("Price")) near.setPrice((double) params.get("Price"));
        if(params.containsKey("EnableWorlds")){
            ArrayList<String> worlds = new ArrayList<>(Arrays.asList(((String) params.get("EnableWorlds")).split(" ")));
            near.setEnableWorlds(worlds);
        }
        if(params.containsKey("Type")) near.setType((NearType) params.get("Type"));
        if(params.containsKey("HideInvisible")) near.setHideInvisible((boolean) params.get("HideInvisible"));
        if(params.containsKey("HideVanish")) near.setHideInvisible((boolean) params.get("HideVanish"));
        return near;
    }
}
