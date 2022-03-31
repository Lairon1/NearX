package com.deathspirit.nearx.manager;

import com.deathspirit.nearx.loader.NearLoader;
import com.deathspirit.nearx.near.Near;

import java.util.HashSet;
import java.util.Optional;

public class RegisteredNear {

    private HashSet<Near> registeredNears = new HashSet<>();

    private NearLoader nearLoader;

    public RegisteredNear(NearLoader nearLoader) {
        this.nearLoader = nearLoader;
    }

    public void registerNear(Near near){
        if(near == null) throw new NullPointerException("Near cannot be null");
        if(registeredNears.contains(near)) throw new IllegalArgumentException("This near already register");
        if(near.getId().equalsIgnoreCase("reload")) throw new IllegalArgumentException("This Near cannot be registered because its id is equal to one of the plugin commands.");
        registeredNears.add(near);
    }

    public Near findNearByID(String id){
        Optional<Near> first = registeredNears
                .stream()
                .filter(near -> near.getId().equals(id))
                .findFirst();
        if(first.isEmpty()) return null;
        return first
                .get();
    }

    public void unregisterNear(Near near){
        if(near == null) throw new NullPointerException("Near cannot be null");
        if(!registeredNears.contains(near)) throw new IllegalArgumentException("This near already unregister");
        registeredNears.remove(near);
    }

    public int reloadNears(){
        int i = 0;
        registeredNears.clear();
        for (Near near : nearLoader.load()){
            registerNear(near);
            i++;
        }
        return i;
    }
}
