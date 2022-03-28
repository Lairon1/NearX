package com.deathspirit.nearx.manager;

import com.deathspirit.nearx.near.Near;

import java.util.HashSet;

public class NearManager {

    private HashSet<Near> registeredNears = new HashSet<>();

    public void registerNear(Near near){
        if(near == null) throw new NullPointerException("Near cannot be null");
        if(registeredNears.contains(near)) throw new IllegalArgumentException("This near already register");
        registeredNears.add(near);
    }

    public Near findNearByID(String id){
        return registeredNears.stream().filter(near -> near.getId().equals(id)).findFirst().get();
    }

}
