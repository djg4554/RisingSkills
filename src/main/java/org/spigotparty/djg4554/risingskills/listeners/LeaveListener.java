package org.spigotparty.djg4554.risingskills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotparty.djg4554.risingskills.AccountManager;
import org.spigotparty.djg4554.risingskills.PlayerAccount;
import org.spigotparty.djg4554.risingskills.RisingSkills;

public class LeaveListener implements Listener {

    private RisingSkills plugin;
    private AccountManager accountManager;


    public LeaveListener(RisingSkills risingSkills) {
        plugin = risingSkills;
        accountManager = plugin.getAccountManager();
    }

    /*
        Retrieve all the information from the player.
        save it into the database
        remove the player form the list
     */
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        PlayerAccount playerAccount = plugin.getPlayerAccountFromOnline(event.getPlayer());

        if (playerAccount == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: the player who left seems to not have an account loaded, pls report this error to the developer of this plugin");
            return;
        }

        accountManager.saveAccount(playerAccount);
        plugin.getOnlinePlayers().remove(playerAccount);



    }



}

