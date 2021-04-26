package org.spigotparty.djg4554.risingskills.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotparty.djg4554.risingskills.AccountManager;
import org.spigotparty.djg4554.risingskills.PlayerAccount;
import org.spigotparty.djg4554.risingskills.RisingSkills;

import java.util.Iterator;

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
        Iterator<PlayerAccount> iterator = plugin.getOnlinePlayers().iterator();
        Player player = event.getPlayer();
        PlayerAccount playerAccount = null;
        while (iterator.hasNext()) {
            PlayerAccount current = iterator.next();
            if (current.getHolder().equals(event.getPlayer())) {
                playerAccount = current;
                break;
            }
        }
        

    }


}

