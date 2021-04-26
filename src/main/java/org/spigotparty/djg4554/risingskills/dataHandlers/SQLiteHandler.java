package org.spigotparty.djg4554.risingskills.dataHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.spigotparty.djg4554.risingskills.PlayerAccount;
import org.spigotparty.djg4554.risingskills.RisingSkills;

import java.io.File;
import java.sql.*;

public class SQLiteHandler {
    private RisingSkills plugin;
    private Connection connection;
    private String url;
    private String table = "risingskills";
    private String database = "data.db";
    private File dataFolder;

    public SQLiteHandler(RisingSkills plugin) {
        this.plugin = plugin;
        setup();
    }

    private void setup() {
        dataFolder = new File(plugin.getDataFolder().getPath(), "data");
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "data folder created");
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ " datafolder not created");
            }
        }
        connect();
        loadDatabase();

    }

    private void connect() {
        url = "jdbc:sqlite:"+dataFolder.getPath()+"/"+database;

        try {
            connection = DriverManager.getConnection(url);

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connection established to the sql database");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void loadDatabase() {

        //Skills: Mining Skill , Fishing , Farming , Chopping , Digging
        String sql = "CREATE TABLE IF NOT EXISTS "+ table +" (\n"
                + "	uuid text NOT NULL,\n"
                + " mining_exp real,\n"
                + " fishing_exp real,\n"
                + " farming_exp real,\n"
                + " chopping_exp real,\n"
                + " digging_exp real\n "
                + ");";
        try {
            Statement statement = connection.createStatement();

            statement.execute(sql);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "table loaded");


        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "could not create the table");
        }
    }

    public void createAccountData(Player player) {
        String sql = "INSERT INTO " + table + "(uuid,mining_exp,fishing_exp,farming_exp,chopping_exp,digging_exp) VALUES (?,0,0,0,0,0);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Account for player " + player.getName()+ " created");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet select(Player player,String...args) {
        // args[0] = uuid, args[1] = mining_exp, 2 = fishing, 3 = farming, 4 = chopping, 5 = digging
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");
        sqlBuilder.append(player.getUniqueId()).append(", ");
        for (int i = 0; i < args.length; i++) {
            sqlBuilder.append(args[i]);
            if (i == args.length-1) {
                sqlBuilder.append(" FROM ").append(table);
            } else {
                sqlBuilder.append(", ");
            }

        }
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sqlBuilder.toString());

        } catch (SQLException e ) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param player The player whose information are needed
     * @return ResultSet the resut set of the information
     */
    public ResultSet selectAll(Player player) {
        String sql = "SELECT * FROM "+ table + " WHERE uuid=?";
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            resultSet = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return resultSet;

    }

    /**
     * check if a player has an account stored into the database
     *
     * @param player  The player to look for
     * @return true   if the player is found, false if not
     *
     */
    public boolean exists(Player player) {
        String sql = " SELECT * FROM " + table + " WHERE uuid=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve the infos of the player from the database
    public void loadAccount(Player player) {
        ResultSet resultSet = selectAll(player);
        try {
            PlayerAccount playerAccount = new PlayerAccount(player, resultSet.getDouble(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getDouble(5), resultSet.getDouble(6));
            plugin.getOnlinePlayers().add(playerAccount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
