package org.spigotparty.djg4554.risingskills;

import org.bukkit.entity.Player;
import org.spigotparty.djg4554.risingskills.dataHandlers.SQLiteHandler;

public class AccountManager {
    private RisingSkills plugin;
    private SQLiteHandler sqLiteHandler;

    public AccountManager(RisingSkills plugin, SQLiteHandler handler) {
        this.plugin = plugin;
        this.sqLiteHandler = handler;

    }


    /** check if an Account in the database exists
        if not:
            create an account
        else:
            if the list contain the player:
                remove the player
            loadTheAccount


     */
    public void loadPlayerAccount(Player player) {
        if (!sqLiteHandler.exists(player)) {
            sqLiteHandler.createAccountData(player);
        }
        PlayerAccount toRemove = null;
        for (PlayerAccount playerAccount : plugin.getOnlinePlayers()) {
            if (playerAccount.getHolder().getUniqueId().equals(player.getUniqueId())) {
                toRemove = playerAccount;
            }
        }
        plugin.getOnlinePlayers().remove(toRemove);
        sqLiteHandler.loadAccount(player);

    }

    public void loadAllAccounts() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            loadPlayerAccount(player);
        }


    }


}
