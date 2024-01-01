package ru.squarecircle.elitetokens.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import ru.squarecircle.elitetokens.EliteTokens;
import ru.squarecircle.elitetokens.core.Menu;
import ru.squarecircle.elitetokens.utils.ChatUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EliteTokensCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("setmaxtokens")) {
                EliteTokens.getInstance().getConfig().set("max-tokens", Integer.parseInt(args[1]));
                try {
                    EliteTokens.getInstance().getConfig().save(new File(EliteTokens.getInstance().getDataFolder().getAbsolutePath() + "/config.yml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                EliteTokens.getInstance().reloadConfig();
                ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("max-number-changed").replace("{changed_number}", args[1])));
            }
            else if(args[0].equalsIgnoreCase("setmintokens")) {
                EliteTokens.getInstance().getConfig().set("min-tokens", Integer.parseInt(args[1]));
                try {
                    EliteTokens.getInstance().getConfig().save(new File(EliteTokens.getInstance().getDataFolder().getAbsolutePath() + "/config.yml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                EliteTokens.getInstance().reloadConfig();
                ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("min-number-changed").replace("{changed_number}", args[1])));
            }
            else if(args[0].equalsIgnoreCase("settings")) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) {
                    ChatUtil.messageTranslated(sender, "player_not_found");
                    return false;
                }
                Menu.openFor(target);
            }
        }
        else if(args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("elitetokens.help")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/t add <Игрок> <Кол-во> - добавить валюту игроку"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/t remove <Игрок> <Кол-во> - удалить валюту у игрока"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/t set <Игрок> <Кол-во> - установить валюту игроку"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/t reset <Игрок> <Кол-во> - сбросить валюту игроку"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/t balance <Игрок> - посмотреть баланс игрока"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/t balance - посмотреть свой баланс"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/t pay <Игрок> <Кол-во> - перевести деньги игроку"));
                }
                else if(args[0].equalsIgnoreCase("settings")) {
                    if(!(sender instanceof Player)) {
                        ChatUtil.messageTranslated(sender, "sender_not_player");
                        return false;
                    }
                    Player player = (Player) sender;
                    if(player.hasPermission("elitetokens.settings")) {
                       Menu.openFor(player);
                    }
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else if(args[0].equalsIgnoreCase("reload")) {
                if(sender.hasPermission("elitetokens.reload")) {
                    EliteTokens.getInstance().reloadConfig();
                    ChatUtil.messageTranslated(sender, "config-reloaded");
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else {
                ChatUtil.messageTranslated(sender, "command-error");
            }
        }
        else if(args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "EliteTokens v" + EliteTokens.getInstance().getDescription().getVersion());
            sender.sendMessage(ChatColor.RED + "Для помощи: /et help");
        }
        else {
            ChatUtil.messageTranslated(sender, "command-error");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        list.add("help");
        list.add("reload");
        list.add("setmaxtokens");
        list.add("setmintokens");
        return list;
    }
}