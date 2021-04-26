package org.spigotparty.djg4554.risingskills;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotparty.djg4554.risingskills.dataHandlers.SQLiteHandler;
import org.spigotparty.djg4554.risingskills.listeners.JoinListener;
import org.spigotparty.djg4554.risingskills.listeners.LeaveListener;
import org.spigotparty.djg4554.risingskills.listeners.TestListener;

import java.util.ArrayList;
import java.util.Iterator;

public final class RisingSkills extends JavaPlugin {

    private SQLiteHandler sqLiteHandler;

    private boolean useMySQL;

    private AccountManager accountManager;
    private ArrayList<PlayerAccount> onlinePlayers;



    @Override
    public void onEnable() {

        onlinePlayers = new ArrayList<>();

        saveDefaultConfig();

        useMySQL = getConfig().getBoolean("config.database.mysql.use-mysql");
        if (useMySQL) {
            // Mysql logic
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + " Skipping mysql");
        } else {
            sqLiteHandler = new SQLiteHandler(this);
            accountManager = new AccountManager(this, sqLiteHandler);
        }

        if (!getServer().getOnlinePlayers().isEmpty()) {
            accountManager.loadAllAccounts();
        }


        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new TestListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //clear the list
        //disconnect form sql
        sqLiteHandler.disconnect();
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
    public ArrayList<PlayerAccount> getOnlinePlayers() {
        return onlinePlayers;
    }

    public PlayerAccount getPlayerAccountFromOnline(Player player) {
        Iterator<PlayerAccount> iterator = getOnlinePlayers().iterator();
        PlayerAccount playerAccount = null;
        while (iterator.hasNext()) {
            PlayerAccount current = iterator.next();
            if (current.getHolder().equals(player)) {
                playerAccount = current;
                break;
            }
        }
        return playerAccount;
    }
}


