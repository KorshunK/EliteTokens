package ru.squarecircle.elitetokens.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.squarecircle.elitetokens.EliteTokens;

public class ChatUtil {
    public static void messageTranslated(CommandSender player, String path) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', translate(getMessage("prefix")) + EliteTokens.getMessages().getString(path)));
    }

    public static void message(CommandSender player, String str) {
        player.sendMessage(translate(getMessage("prefix")) + str);
    }

    public static String translate(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String getMessage(String path) {
        return EliteTokens.getMessages().getString(path);
    }
}