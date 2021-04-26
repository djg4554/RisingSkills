package org.spigotparty.djg4554.risingskills;

import org.bukkit.entity.Player;

public class PlayerAccount {
    private final Player holder;
    private double mining, fishing, farming, chopping, digging;
    public PlayerAccount(Player holder, double mining, double fishing, double farming, double chopping, double digging) {
        this.holder = holder;
        this.mining = mining;
        this.fishing = fishing;
        this.farming = farming;
        this.chopping = chopping;
        this.digging = digging;
    }


    //Getters
    public Player getHolder() {
        return holder;
    }
    public double getMining() {
        return mining;
    }
    public double getFishing() {
        return fishing;
    }
    public double getFarming() {
        return farming;
    }
    public double getChopping() {
        return chopping;
    }
    public double getDigging() {
        return digging;
    }

    //Setters
    public void setMining(double mining) {
        this.mining = mining;
    }
    public void setFishing(double fishing) {
        this.fishing = fishing;
    }
    public void setFarming(double farming) {
        this.farming = farming;
    }
    public void setChopping(double chopping) {
        this.chopping = chopping;
    }
    public void setDigging(double digging) {
        this.digging = digging;
    }





}
