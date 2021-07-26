package xyz.pugly.utils.Command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.pugly.utils.ConfigHandler;
import xyz.pugly.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandHandler implements CommandExecutor, TabCompleter {

    //TODO FINISH MAKING THIS
    public List<Command> commands = new ArrayList<>();

    public CommandHandler(String command, JavaPlugin p) {
        p.getCommand(command).setExecutor(this);
        p.getCommand(command).setTabCompleter(this);
    }

    public CommandHandler registerCommand(Command command) {
        commands.add(command);
        return this;
    }

    public CommandHandler unRegisterCommand(Command command) {
        commands.remove(command);
        return this;
    }

    public CommandHandler registerCommands(Set<Command> commands) {
        this.commands.addAll(commands);
        return this;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull org.bukkit.command.Command cmd, @NotNull String s, String[] args) {
        if (args.length != 0) {
            for (Command command : commands) {
                if (command.aliases.contains(args[0]) && command.enabled) {
                    if (command.player && !(cs instanceof Player)) {
                        // Must be a player

                        cs.sendMessage("\u00a7cYou must be a player to execute this command.");
                        return true;
                    }
                    if ((cs.hasPermission(command.permission) || command.permission
                            .equalsIgnoreCase(""))) {
                        command.execute(cs, args);
                    } else {
                        // No permission

                        cs.sendMessage(ConfigHandler.getPermissionMessage());
                    }
                    return true;
                }
            }
        }
        //Unknown command message
        cs.sendMessage(ConfigHandler.getUnknownCommandMessage());
        //Send help maybe
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender cs,@NotNull org.bukkit.command.@NotNull Command cmd,@NotNull String s, String[] args) {
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (Command command : commands) {
                for (String alias : command.aliases) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (
                            command.enabled && (cs.hasPermission(command.permission)
                                    || command.permission.equalsIgnoreCase("")))) {
                        result.add(alias);
                    }
                }
            }
            return result;
        }
        for (Command command : commands) {
            if (command.aliases.contains(args[0]) && (command.enabled && (
                    cs.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")))) {
                return command.TabComplete(cs, cmd, s, args);
            }
        }
        return null;
    }
}
