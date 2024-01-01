package ru.squarecircle.elitetokens.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.squarecircle.elitetokens.EliteTokens;
import ru.squarecircle.elitetokens.utils.ChatUtil;
import ru.squarecircle.elitetokens.utils.ConfigsUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Events1 extends BukkitRunnable implements Listener {

    private Map<UUID, Long> players = new HashMap<UUID, Long>();

    public Events1(JavaPlugin plugin) {
        this.runTaskTimer(plugin, 0, 20);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem().getType() == Material.valueOf(ConfigsUtil.getString("menu-settings.setmaxtokens-icon")) && e.getView().getTitle().equalsIgnoreCase(ConfigsUtil.getString("menu-settings.menu-title"))) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if(players.containsKey(player.getUniqueId())) {
                player.closeInventory();
                return;
            }
            players.put(player.getUniqueId(), System.currentTimeMillis() + 60000);
            player.closeInventory();
            ChatUtil.messageTranslated(player, "settokens-message");
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();

        if(!players.containsKey(player.getUniqueId())) return;
        e.setCancelled(true);
            EliteTokens.getInstance().getConfig().set("max-tokens", Integer.parseInt(message));
            try {
                EliteTokens.getInstance().getConfig().save(new File(EliteTokens.getInstance().getDataFolder().getAbsolutePath() + "/config.yml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            EliteTokens.getInstance().reloadConfig();
        for(UUID uuid : players.keySet()) {
            players.remove(uuid);
        }
    }

    @Override
    public void run() {
        for(UUID uuid : players.keySet()) {
            if(System.currentTimeMillis() >= players.get(uuid)) {
                players.remove(uuid);
            }
        }
    }
}
