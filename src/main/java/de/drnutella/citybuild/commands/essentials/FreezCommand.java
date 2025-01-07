package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.UUID;

public class FreezCommand implements CommandExecutor, Listener {

    public static final ArrayList<UUID> FREEZED_PLAYER_LIST = new ArrayList<>();

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            if(sender.hasPermission(PermissionsConfigAdapter.PERMISSION_FREEZ)){
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null){
                    if(FREEZED_PLAYER_LIST.contains(target.getUniqueId())){
                        FREEZED_PLAYER_LIST.remove(target.getUniqueId());
                        sender.sendMessage(PREFIX + "§fDer Spieler §b" + args[0] + "§f ist nun §cnicht §fmehr gefreezed");
                    }else {
                        FREEZED_PLAYER_LIST.add(target.getUniqueId());
                        sender.sendMessage(PREFIX + "§fDer Spieler §b" + args[0] + "§f ist nun gefreezed");
                    }
                }else {
                    sender.sendMessage(PREFIX + "§cDer Spieler §b" + args[0] + "§c ist nicht online!");
                }
            }else {
                return false;
            }
        }else {
            sendSyntax(sender);
        }
        return false;
    }

    static void sendSyntax(final CommandSender sender) {
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/freez §7[§eSpieler§7]");
    }

    @EventHandler
    private void playerMove(PlayerMoveEvent event){
        if(FREEZED_PLAYER_LIST.contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }
}
