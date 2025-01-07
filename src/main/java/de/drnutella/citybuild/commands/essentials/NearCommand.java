package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NearCommand implements CommandExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            final int radius = 50;

            final List<Player> nearbyPlayers = getNearbyPlayers(player, radius);

            if (nearbyPlayers.isEmpty()) {
                sender.sendMessage(PREFIX + "§fKeine Spieler in der Nähe gefunden!");
                return true;
            }

            final StringBuilder message = new StringBuilder(PREFIX + "§fSpieler in der Nähe §7(§e50 Blöcke§7)§f:\n");
            for (final Player nearbyPlayer : nearbyPlayers) {
                double distance = player.getLocation().distance(nearbyPlayer.getLocation());
                distance = Math.floor(distance);
                message.append("- §b" + nearbyPlayer.getName() + " §7(§b" + distance + " §fBlöcke entfernt§7)");
            }

            sender.sendMessage(message.toString());
            return true;
        }

        sender.sendMessage(PREFIX + "Dieser Befehl kann nur von Spielern ausgeführt werden!");
        return false;
    }

    static List<Player> getNearbyPlayers(final Player player, final int radius) {
        final List<Player> nearbyPlayers = new ArrayList<>();
        for (final Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if(player.canSee(onlinePlayer)) {
                if (onlinePlayer != player && onlinePlayer.getLocation().distance(player.getLocation()) <= radius) {
                    nearbyPlayers.add(onlinePlayer);
                }
            }
        }
        return nearbyPlayers;
    }
}
