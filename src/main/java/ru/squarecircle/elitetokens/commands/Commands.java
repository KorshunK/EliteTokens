package ru.squarecircle.elitetokens.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import ru.squarecircle.elitetokens.EliteTokens;
import ru.squarecircle.elitetokens.core.API;
import ru.squarecircle.elitetokens.core.Menu;
import ru.squarecircle.elitetokens.utils.ChatUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Commands implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 3) {
            if(args[0].equalsIgnoreCase("add")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if(!target.hasPlayedBefore()) {
                    ChatUtil.messageTranslated(sender, "player_not_found");
                    return false;
                }
                if(Integer.parseInt(args[2]) <= EliteTokens.getInstance().getConfig().getInt("min-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-error");
                    return false;
                }
                if(API.instance.getTokens(target) + Integer.parseInt(args[2]) > EliteTokens.getInstance().getConfig().getInt("max-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-higher-max");
                    return false;
                }
                if (sender.hasPermission("elitetokens.add")) {
                    try {
                        API.instance.addTokens(target, Integer.parseInt(args[2]));
                    } catch (NumberFormatException e) {
                        ChatUtil.messageTranslated(sender, "tokens-not-number");
                    }
                    String msg = EliteTokens.getMessages().getString("you_add_player_tokens").replace("{player_name}", target.getName()).replace("{tokens}", args[2]);
                    ChatUtil.message(sender, ChatUtil.translate(msg));
                } else {
                    ChatUtil.message(sender, "dont_have_permission");
                }
            }
            else if(args[0].equalsIgnoreCase("set")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if(!target.hasPlayedBefore()) {
                    ChatUtil.messageTranslated(sender, "player_not_found");
                    return false;
                }
                if(Integer.parseInt(args[2]) <= EliteTokens.getInstance().getConfig().getInt("min-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-error");
                    return false;
                }
                if(Integer.parseInt(args[2]) > EliteTokens.getInstance().getConfig().getLong("max-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-higher-max");
                    return false;
                }
                if(sender.hasPermission("elitetokens.set")) {
                    try {
                        API.instance.setTokens(target, Integer.parseInt(args[2]));
                    }
                    catch (NumberFormatException e) {
                        ChatUtil.messageTranslated(sender, "tokens-not-number");
                    }
                    ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("you_set_player_tokens").replace("{player_name}", target.getName()).replace("{tokens}", args[2])));
                }
                else {
                    ChatUtil.message(sender, "dont_have_permission");
                }
            }
            else if(args[0].equalsIgnoreCase("remove")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if(!target.hasPlayedBefore()) {
                    ChatUtil.messageTranslated(sender, "player_not_found");
                    return false;
                }
                if(Integer.parseInt(args[2]) <= EliteTokens.getInstance().getConfig().getInt("min-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-error");
                    return false;
                }
                if(API.instance.getTokens(target) - Integer.parseInt(args[2]) < EliteTokens.getInstance().getConfig().getInt("min-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-bellow-min");
                    return false;
                }
                if(sender.hasPermission("elitetokens.remove")) {
                    try {
                        API.instance.removeTokens(target, Integer.parseInt(args[2]));
                    } catch (NumberFormatException e) {
                        ChatUtil.messageTranslated(sender, "tokens-not-number");
                    }
                    ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("you_remove_player_tokens").replace("{player_name}", target.getName()).replace("{tokens}", args[2])));
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else if(args[0].equalsIgnoreCase("pay")) {
                if(!(sender instanceof Player)) {
                    ChatUtil.messageTranslated(sender, "sender_not_player");
                    return false;
                }
                Player player = (Player) sender;
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if(!target.hasPlayedBefore()) {
                    ChatUtil.messageTranslated(sender, "player_not_found");
                    return false;
                }
                if(Integer.parseInt(args[2]) <= EliteTokens.getInstance().getConfig().getInt("min-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-error");
                    return false;
                }
                if(Integer.parseInt(args[2]) > API.instance.getTokens(player)) {
                    ChatUtil.messageTranslated(sender, "dont-have-tokens");
                    return false;
                }
                if(API.instance.getTokens(target) + Integer.parseInt(args[2]) < EliteTokens.getInstance().getConfig().getInt("min-tokens")) {
                    ChatUtil.messageTranslated(sender, "number-higher-max");
                    return false;
                }
                if(player.hasPermission("elitetokens.pay")) {
                    try {
                        API.instance.payTokens(player, target, Integer.parseInt(args[2]));
                    } catch (NumberFormatException e) {
                        ChatUtil.messageTranslated(sender, "tokens-not-number");
                    }
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else {
                ChatUtil.messageTranslated(sender, "command-error");
            }
        }
        else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("reset")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if(sender.hasPermission("elitetokens.reset")) {
                    API.instance.resetTokens(target);
                    String msg = EliteTokens.getMessages().getString("you_reset_player_tokens").replace("{player_name}", target.getName());
                    ChatUtil.message(sender, ChatUtil.translate(msg));
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else if(args[0].equalsIgnoreCase("balance")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if(!target.hasPlayedBefore()) {
                    ChatUtil.messageTranslated(sender, "player_not_found");
                    return false;
                }
                else {
                    if (target == sender) {
                        ChatUtil.messageTranslated(sender, "player-is-target");
                        return false;
                    }
                    if (sender.hasPermission("elitetokens.balance.other")) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("balance-player").replace("{tokens}", String.valueOf(API.instance.getTokens(target))).replace("{player_name}", target.getName())));
                    } else {
                        ChatUtil.messageTranslated(sender, "dont_have_permission");
                    }
                }
            }
            else if(args[0].equalsIgnoreCase("addall")) {
                if (sender.hasPermission("elitetokens.addall")) {
                    for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                        API.instance.addTokens(player, Integer.parseInt(args[1]));
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        API.instance.addTokens(player, Integer.parseInt(args[1]));
                    }
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else {
                ChatUtil.messageTranslated(sender, "command-error");
            }
        }
        else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("balance")) {
                Player player = (Player) sender;
                if(sender.hasPermission("elitetokens.balance")) {
                    ChatUtil.message(player, ChatUtil.translate(ChatUtil.getMessage("balance").replace("{tokens}", String.valueOf(API.instance.getTokens(player)))));
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else if(args[0].equalsIgnoreCase("resetAll") || args[0].equalsIgnoreCase("vaip")) {
                if(sender.hasPermission("elitetokens.resetall")) {
                    for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                        API.instance.resetTokens(player);
                    }
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        API.instance.resetTokens(player);
                    }
                    ChatUtil.messageTranslated(sender, "reset-all");
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else if(args[0].equalsIgnoreCase("settings")) {
                if(sender.hasPermission("elitetokens.settings")) {
                    if(!(sender instanceof Player)) {
                        ChatUtil.messageTranslated(sender, "sender_not_player");
                        return false;
                    }
                    Player player = (Player) sender;
                    Menu.openFor(player);
                }
                else {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                }
            }
            else {
                ChatUtil.messageTranslated(sender, "command-error");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("add");
            list.add("addall");
            list.add("balance");
            list.add("remove");
            list.add("reset");
            list.add("resetAll");
            list.add("set");
            list.add("vaip");
            return list;
        }
        return null;
    }
}