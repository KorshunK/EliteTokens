package ru.squarecircle.elitetokens.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.squarecircle.elitetokens.utils.ConfigsUtil;

public class Menu {
    private static Inventory menu = Bukkit.createInventory(null, 54, ConfigsUtil.getString("menu-settings.menu-title"));

    public static Inventory getMenu() {
        return menu;
    }

    public static void openFor(Player player) {
        setup();
        player.openInventory(menu);
    }

    public static void setup() {
        ItemStack database = new ItemStack(Material.valueOf(ConfigsUtil.getString("menu-settings.database-icon")));
        ItemMeta databaseIM = database.getItemMeta();
        ItemStack setmintokens = new ItemStack(Material.valueOf(ConfigsUtil.getString("menu-settings.setmintokens-icon")));
        ItemMeta setmintokensIM = setmintokens.getItemMeta();
        ItemStack setmaxtokens = new ItemStack(Material.valueOf(ConfigsUtil.getString("menu-settings.setmaxtokens-icon")));
        ItemMeta setmaxtokensIM = setmaxtokens.getItemMeta();
        if(ConfigsUtil.getString("database.type").equalsIgnoreCase("sqlite")) {
            databaseIM.setDisplayName(ChatColor.GREEN + "SQLite");
            database.setItemMeta(databaseIM);
        }
        else if(ConfigsUtil.getString("database.type").equalsIgnoreCase("mysql")) {
            databaseIM.setDisplayName(ChatColor.RED + "MySQL");
            database.setItemMeta(databaseIM);
        }
        setmintokensIM.setDisplayName(String.valueOf(ConfigsUtil.getInt("min-tokens")));
        setmintokens.setItemMeta(setmintokensIM);
        setmaxtokensIM.setDisplayName(String.valueOf(ConfigsUtil.getInt("max-tokens")));
        setmaxtokens.setItemMeta(setmaxtokensIM);
        menu.setItem(ConfigsUtil.getInt("menu-settings.database-slot"), database);
        menu.setItem(ConfigsUtil.getInt("menu-settings.setmintokens-slot"), setmintokens);
        menu.setItem(ConfigsUtil.getInt("menu-settings.setmaxtokens-slot"), setmaxtokens);
    }
}
