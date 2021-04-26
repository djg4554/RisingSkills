package org.spigotparty.djg4554.risingskills.dataHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.spigotparty.djg4554.risingskills.PlayerAccount;
import org.spigotparty.djg4554.risingskills.RisingSkills;
import org.spigotparty.djg4554.risingskills.Skill;

import java.io.File;
import java.sql.*;

public class SQLiteHandler {
    private final RisingSkills plugin;
    private Connection connection;
    private String url;
    private final String table = "risingskills";

    public SQLiteHandler(RisingSkills plugin) {
        this.plugin = plugin;
        setup();
    }

    private void setup() {
        File dataFolder = new File(plugin.getDataFolder().getPath(), "data");
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "data folder created");
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+ " datafolder not created");
            }
        }
        String database = "data.db";
        url = "jdbc:sqlite:"+ dataFolder.getPath()+"/"+ database;
        connect();
        setupDatabase();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url);

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "A connection has been enstablished");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupDatabase() {
            //Skills: Mining Skill , Fishing , Farming , Chopping , Digging
            String sql = "CREATE TABLE IF NOT EXISTS " + table + " (\n"
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
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Account for player " + player.getName() + " created");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

    }



    /**
     *
     * @param player The player whose information are needed
     * @return ResultSet the resut set of the information
     */
    public PlayerAccount selectAll(Player player) {
        String sql = "SELECT * FROM "+ table + " WHERE uuid=?";
        ResultSet resultSet;
        PlayerAccount playerAccount = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                playerAccount = new PlayerAccount(player,
                        resultSet.getDouble(2),
                        resultSet.getDouble(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6)
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return playerAccount;

    }

    /**
     * check if a player has an account stored into the database
     *
     * @param player  The player to look for
     * @return true   if the player is found, false if not
     *
     */
    public boolean exists(Player player) {
        String sql = " SELECT "+Skill.MINING.getDataLabel()+" FROM " + table + " WHERE uuid='" + player.getUniqueId() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve the infos of the player from the database
    public void loadAccount(Player player) {
        PlayerAccount playerAccount = selectAll(player);
        plugin.getOnlinePlayers().add(playerAccount);
        plugin.getServer().getConsoleSender().sendMessage("Player " + player.getName() + " loaded");

    }

    //update the data of a player
    public void saveAccount(PlayerAccount playerAccount) {

        String sql = "UPDATE " + table + " SET "
                + Skill.MINING.getDataLabel() + " = ? , "
                + Skill.FISHING.getDataLabel() + " = ? , "
                + Skill.FARMING.getDataLabel() + " = ? , "
                + Skill.CHOPPING.getDataLabel() + " = ? , "
                + Skill.DIGGING.getDataLabel() + " = ? WHERE uuid =?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, playerAccount.getMining());
            preparedStatement.setDouble(2, playerAccount.getFishing());
            preparedStatement.setDouble(3, playerAccount.getFarming());
            preparedStatement.setDouble(4, playerAccount.getChopping());
            preparedStatement.setDouble(5, playerAccount.getDigging());
            preparedStatement.setString(6, playerAccount.getHolder().getUniqueId().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
