package org.spigotparty.djg4554.risingskills.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotparty.djg4554.risingskills.AccountManager;
import org.spigotparty.djg4554.risingskills.RisingSkills;

public class JoinListener implements Listener {
    private RisingSkills pluign;
    private AccountManager accountManager;

     public JoinListener(RisingSkills pluign) {
         this.pluign = pluign;
         accountManager = pluign.getAccountManager();
     }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        accountManager.loadPlayerAccount(player);

    }


}
