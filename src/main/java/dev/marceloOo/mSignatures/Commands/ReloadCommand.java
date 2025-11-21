package dev.marceloOo.mSignatures.Commands;

import dev.marceloOo.mSignatures.MSignatures;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string).replace(">>", "»");
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender.hasPermission("ms.reload")) {
            MSignatures.getInstance().reloadConfig();
            sender.sendMessage(color("&8[&r&lMS&8] &8>>" + MSignatures.getInstance().getConfig().getString("messages.configReloaded")));
        } else {
            sender.sendMessage(color("&8[&r&lMS&8] &8>>" + MSignatures.getInstance().getConfig().getString("messages.noPermission")));
        }
        return true;
    }
}
