package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission(PermissionsConfigAdapter.PERMISSION_TELEPORT)){
                if(args.length == 1){
                    final Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        if(player != target) {
                            player.teleport(target);
                            player.sendMessage(PREFIX + "§fDu wurdest zu §b" + target.getName() + "§f teleportiert");
                        }else {
                            player.sendMessage(PREFIX + "§cDu kannst dich nicht zu dir selber teleportieren");
                        }
                    }else {
                        player.sendMessage(PREFIX + "§cDer Spieler §b" + args[0] + "§c ist nicht online!");
                    }
                }else {
                    sendSyntax(player);
                }
            }else {
                return false;
            }
        }
        return false;
    }

    static void sendSyntax(final CommandSender sender) {
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/tp §7[§eSpieler§7]");
    }
}
