package org.spigotparty.djg4554.risingskills;

import org.bukkit.entity.Player;
import org.spigotparty.djg4554.risingskills.dataHandlers.SQLiteHandler;

public class AccountManager {
    private final RisingSkills plugin;
    private final SQLiteHandler sqLiteHandler;

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

    // save the player accounts
    public void saveAccount(PlayerAccount playerAccount) {
        sqLiteHandler.saveAccount(playerAccount);
    }


    public void printMiningSKill(PlayerAccount playerAccount) {

        PlayerAccount saved = sqLiteHandler.selectAll(playerAccount.getHolder());
        playerAccount.getHolder().sendMessage("Hey you gay" + saved.getMining());
        playerAccount.getHolder().sendMessage("Hey you not gay" + playerAccount.getMining());
    }
}
