package org.spigotparty.djg4554.risingskills;

import org.bukkit.plugin.java.JavaPlugin;
import org.spigotparty.djg4554.risingskills.dataHandlers.SQLiteHandler;
import org.spigotparty.djg4554.risingskills.listeners.JoinListener;

import java.util.ArrayList;

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
        } else {
            sqLiteHandler = new SQLiteHandler(this);
            accountManager = new AccountManager(this, sqLiteHandler);

        }



        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //clear the list
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
    public ArrayList<PlayerAccount> getOnlinePlayers() {
        return onlinePlayers;
    }
}
