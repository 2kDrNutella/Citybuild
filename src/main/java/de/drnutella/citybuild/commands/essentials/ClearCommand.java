package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearCommand implements CommandExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_CLEAR)) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.getInventory().clear();
                    player.sendMessage(PREFIX + "§fDein Inventar wurde gelöscht");
                }
            } else if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.getInventory().clear();
                    target.sendMessage(PREFIX + "§fDein Inventar wurde geleert");
                    sender.sendMessage(PREFIX + "§fDu hast das Inventar von §b" + target.getName() + "§f gelöscht");
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
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/clear §coder §b/clear §7[§eSpieler§7]");
    }
}
