package dev.marceloOo.mSignatures.Commands;

import dev.marceloOo.mSignatures.MSignatures;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CheckSignCommand implements CommandExecutor, Listener {
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string).replace(">>", "»");
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (player.hasPermission("ms.checksign")) {
                if (args.length == 1) {
                    String targetPlayerName = args[0];
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                        ItemMeta meta = item.getItemMeta();
                        List<String> lore = meta.getLore();
                        String expectedSignature = color(MSignatures.getInstance().getConfig().getString("settings.signatureName").replace("%player_name%", targetPlayerName));
                        if (lore != null && lore.contains(expectedSignature)) {
                            player.sendMessage(color("&8[&r&lMS&8] &8>> &aThis item is signed by &e" + targetPlayerName + "&a."));
                        } else {
                            player.sendMessage(color("&8[&r&lMS&8] &8>> &aThis item is not signed by &e" + targetPlayerName + "&a."));
                        }
                    } else {
                        player.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.itemHasNoLore")));
                    }
                } else {
                    player.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.checkSignCorrectUsage")));
                }
            } else {
                sender.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.noPermission")));
            }
        } else {
            sender.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.notAPlayer")));
        }
        return true;
    }
}