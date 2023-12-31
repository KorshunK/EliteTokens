package ru.squarecircle.elitetokens;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.squarecircle.elitetokens.commands.Commands;
import ru.squarecircle.elitetokens.commands.EliteTokensCommand;
import ru.squarecircle.elitetokens.core.API;
import ru.squarecircle.elitetokens.core.UpdateChecker;
import ru.squarecircle.elitetokens.placeholders.Placeholder;
import ru.squarecircle.elitetokens.utils.ConfigsUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class EliteTokens extends JavaPlugin {
    private static EliteTokens instance;
    private static YamlConfiguration messages;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("tokens").setExecutor(new Commands());
        getCommand("tokens").setTabCompleter(new Commands());
        getCommand("elitetokens").setExecutor(new EliteTokensCommand());
        getCommand("elitetokens").setTabCompleter(new EliteTokensCommand());
        File file = new File(getFile().getAbsolutePath() + "/tokens-db.sqlite");
        saveDefaultConfig();
        loadMessages();
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholder().register();
        }
        if(ConfigsUtil.getBoolean("update-check"))
        new UpdateChecker(this, 114079).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info(ChatColor.RED + "Обновлений не найдено!");
            } else {
                getLogger().info(ChatColor.GREEN + "Найдено обновление! Скачать: https://spigotmc.org/resources/elitetokens.114079");
            }
        });
        this.getLogger().info(ChatColor.GREEN + "Плагин включен!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info(ChatColor.RED + "Плагин выключен!");
    }

    public static YamlConfiguration getMessages() {
        return messages;
    }

    private void loadMessages() {
        saveResource("messages.yml", false);
        File file = new File(getDataFolder().getAbsolutePath() + "/messages.yml");
        messages = YamlConfiguration.loadConfiguration(file);
    }

    public static EliteTokens getInstance() {
        return instance;
    }
}