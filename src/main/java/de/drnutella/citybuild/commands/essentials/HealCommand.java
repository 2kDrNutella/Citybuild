package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_HEAL)) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.setHealth(20);
                    player.sendMessage(PREFIX + "§fDu hast dich geheilt");
                }
            } else if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setHealth(20);
                    target.sendMessage(PREFIX + "§fDu wurdest geheilt");
                    sender.sendMessage(PREFIX + "§fDu hast §b" + target.getName() + "§f geheilt");
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
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/heal §coder §b/heal §7[§eSpieler§7]");
    }
}
