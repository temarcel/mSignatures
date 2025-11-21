package dev.marceloOo.mSignatures.Commands;

import dev.marceloOo.mSignatures.MSignatures;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SignCommand implements CommandExecutor {
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string).replace(">>", "»");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                player.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.signCorrectUsage")));
                return true;
            }

            if (player.hasPermission("ms.sign")) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getItemMeta() != null) {
                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = meta.getLore();
                    String signature = color(MSignatures.getInstance().getConfig().getString("settings.signatureName").replace("%player_name%", player.getName()));

                    // Initialize lore if it's null
                    if (lore == null) {
                        lore = new ArrayList<>();
                    }

                    boolean alreadySigned = lore.stream().anyMatch(line ->
                            line.contains(color(MSignatures.getInstance().getConfig().getString("settings.signatureNamePrefix")))
                    );

                    // Prevent signing if already signed and no oversigning permission
                    if (alreadySigned && !player.hasPermission("msignatures.oversigning")) {
                        player.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.cannotSignAlreadySigned")));
                        return true;
                    }

                    // Handle oversigning
                    if (alreadySigned) {
                        boolean keepOldSignatures = MSignatures.getInstance().getConfig().getBoolean("settings.keepOldSignatures");

                        if (!keepOldSignatures) {
                            // Remove all previous signatures
                            lore.removeIf(line ->
                                    line.contains(color(MSignatures.getInstance().getConfig().getString("settings.signatureNamePrefix")))
                            );
                        }
                    }

                    // Add the new signature
                    lore.add(signature);
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    player.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.itemSigned")));
                } else {
                    player.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.noItemInHand")));
                }
            } else {
                player.sendMessage(color("&8[&r&lMS&8] &8>> " + MSignatures.getInstance().getConfig().getString("messages.noPermission")));
            }
        } else {
            sender.sendMessage("[MS] >> " + MSignatures.getInstance().getConfig().getString("messages.notAPlayer"));
        }
        return true;
    }
}
