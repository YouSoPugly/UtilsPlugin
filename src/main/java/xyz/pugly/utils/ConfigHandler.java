package xyz.pugly.utils;

public class ConfigHandler {

    public static String getPermissionMessage() {
        return TextUtils.colorize(Utils.get().getConfig().getString("no-permission"));
    }

    public static String getUnknownCommandMessage() {
        return TextUtils.colorize(Utils.get().getConfig().getString("unknown-command"));
    }

}
