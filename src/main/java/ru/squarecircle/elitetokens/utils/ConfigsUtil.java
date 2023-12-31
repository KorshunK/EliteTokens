package ru.squarecircle.elitetokens.utils;

import ru.squarecircle.elitetokens.EliteTokens;

public class ConfigsUtil {
    public static String getString(String path) {
        return EliteTokens.getInstance().getConfig().getString(path);
    }
    public static boolean getBoolean(String path) {
        return EliteTokens.getInstance().getConfig().getBoolean(path);
    }
    public static int getInt(String path) {
        return EliteTokens.getInstance().getConfig().getInt(path);
    }
}
