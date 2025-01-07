package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_FEED)) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.setFoodLevel(20);
                    player.sendMessage(PREFIX + "§fDu hast dich gefüttert");
                }
            } else if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setFoodLevel(20);
                    target.sendMessage(PREFIX + "§fDu wurdest gefüttert");
                    sender.sendMessage(PREFIX + "§fDu hast §b" + target.getName() + "§f gefüttert");
                } else {
                    sender.sendMessage(PREFIX + "§cDer Spieler ist nicht online");
                }
            } else {
                sendSyntax(sender);
            }
        }
        return false;
    }

    static void sendSyntax(final CommandSender sender) {
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/feed §coder §b/feed §7[§eSpieler§7]");
    }

}
