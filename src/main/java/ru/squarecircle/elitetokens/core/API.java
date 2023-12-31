package ru.squarecircle.elitetokens.core;

import org.bukkit.OfflinePlayer;
import ru.squarecircle.elitetokens.EliteTokens;
import ru.squarecircle.elitetokens.utils.ConfigsUtil;

import java.sql.*;

public class API {
    public static API instance = new API();

    private Connection connection;
    private Statement stmt;

    public API() {
        try {
            if(EliteTokens.getInstance().getConfig().getString("database.type").equalsIgnoreCase("sqlite")) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + EliteTokens.getInstance().getDataFolder().getAbsolutePath() + "/tokens-db.sqlite");
            }
            else if(EliteTokens.getInstance().getConfig().getString("database.type").equalsIgnoreCase("mysql")) {
                connection = DriverManager.getConnection("jdbc:mysql://" + ConfigsUtil.getString("database.host") + ":" + ConfigsUtil.getString("database.port") + "/" + ConfigsUtil.getString("database.dbName"), ConfigsUtil.getString("database.username"), ConfigsUtil.getString("database.password"));
            }
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS players(player_name TEXT, tokens INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addTokens(OfflinePlayer player, int amount) {
        int currencyTokens = getTokens(player) + amount;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM players WHERE player_name = '" + player.getName() + "'");
            if(rs.next()) {
                if(ConfigsUtil.getString("database.type").equalsIgnoreCase("sqlite")) {
                    stmt.execute("UPDATE players SET tokens='" + currencyTokens + "' WHERE player_name = '" + player.getName() + "'");
                }
                else if(ConfigsUtil.getString("database.type").equalsIgnoreCase("mysql")) {
                    stmt.execute("UPDATE players SET tokens = '" + currencyTokens + "' WHERE player_name = '" + player.getName() + "'");
                }
            }
            else {
                stmt.execute("INSERT INTO players(player_name, tokens) VALUES('" + player.getName() + "', '" + currencyTokens + "');");
            }
        } catch (SQLException ignored) {}
    }
    public Integer getTokens(OfflinePlayer player) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM players WHERE player_name = '" + player.getName() + "'");
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(2);
        } catch (SQLException ignored) {}
        return null;
    }
    public void setTokens(OfflinePlayer player, int amount) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM players WHERE player_name = '" + player.getName() + "'");
            if(rs.next()) {
                if(ConfigsUtil.getString("database.type").equalsIgnoreCase("sqlite")) {
                    stmt.execute("UPDATE players SET tokens='" + amount + "' WHERE player_name = '" + player.getName() + "'");
                    // UPDATE players SET tokens='" + amount + '" + player.getName() + "'
                }
                else if(ConfigsUtil.getString("database.type").equalsIgnoreCase("mysql")) {
                    stmt.execute("UPDATE players SET tokens = '" + amount + "' WHERE player_name = '" + player.getName() + "'");
                }
            }
            else {
                stmt.execute("INSERT INTO players(player_name, tokens) VALUES('" + player.getName() + "', '" + amount + "');");
            }
        } catch (SQLException ignored) {}
    }
    public void removeTokens(OfflinePlayer player, int amount) {
        int currencyTokens = getTokens(player) - amount;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM players WHERE player_name = '" + player.getName() + "'");
            if(rs.next()) {
                if(ConfigsUtil.getString("database.type").equalsIgnoreCase("sqlite")) {
                    stmt.execute("UPDATE players SET tokens='" + currencyTokens + "' WHERE player_name = '" + player.getName() + "'");
                }
                else if(ConfigsUtil.getString("database.type").equalsIgnoreCase("mysql")) {
                    stmt.execute("UPDATE players SET tokens = '" + currencyTokens + "' WHERE player_name = '" + player.getName() + "'");
                }
            }
            else {
                stmt.execute("INSERT INTO players(player_name, tokens) VALUES('" + player.getName() + "', '" + currencyTokens + "');");
            }
        } catch (SQLException ignored) {}
    }
    public void resetTokens(OfflinePlayer player) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM players  WHERE player_name = '" + player.getName() + "'");
            if(rs.next()) {
                if(ConfigsUtil.getString("database.type").equalsIgnoreCase("sqlite")) {
                    stmt.execute("UPDATE players SET tokens='" + 0 + "' WHERE player_name = '" + player.getName() + "'");
                }
                else if(ConfigsUtil.getString("database.type").equalsIgnoreCase("mysql")) {
                    stmt.execute("UPDATE players SET tokens = '" + 0 + "' WHERE player_name = '" + player.getName() + "'");
                }
            }
        } catch (SQLException ignored) {}
    }
    public void payTokens(OfflinePlayer player, OfflinePlayer target, int amount) {
        addTokens(target, amount);
        removeTokens(player, amount);
    }
}