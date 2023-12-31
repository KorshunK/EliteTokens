package ru.squarecircle.elitetokens.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.squarecircle.elitetokens.core.API;

public class Placeholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "elitetokens";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Korshun";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(player == null) {
            return "";
        }
        if(params.equalsIgnoreCase("tokens")) {
            return String.valueOf(API.instance.getTokens(player));
        }
        return null;
    }
}