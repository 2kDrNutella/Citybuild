package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {
    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_HEAL)) {
            if (args.length > 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    final StringBuilder sudoCommand = new StringBuilder(args[1]);
                    for (int i = 2; i < args.length; i++) {
                        sudoCommand.append(" ").append(args[i]);
                    }
                    target.performCommand(sudoCommand.toString());
                    target.sendMessage(PREFIX + "§fDu wurdest gezwungen einen Befehl auszuführen!");
                    sender.sendMessage(PREFIX + "§fDer Spieler §b" + target.getName() + "§f führt nun den Befehl §7[§e/" + sudoCommand + "§7]§f aus");
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
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/sudo [Spieler] [Command]");
    }
}
