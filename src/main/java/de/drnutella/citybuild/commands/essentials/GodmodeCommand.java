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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.ArrayList;
import java.util.UUID;

public class GodmodeCommand implements CommandExecutor, Listener {

    public static final ArrayList<UUID> GODMODE_PLAYER_LIST = new ArrayList<>();

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if(sender instanceof Player player) {
                if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_GODMODE)) {
                    if (GODMODE_PLAYER_LIST.contains(player.getUniqueId())) {
                        GODMODE_PLAYER_LIST.remove(player.getUniqueId());
                        player.sendMessage(PREFIX + "§fDu bist nun §cnicht §fmehr im godmode");
                    } else {
                        GODMODE_PLAYER_LIST.add(player.getUniqueId());
                        player.sendMessage(PREFIX + "§fDu bist nun im godmode");
                    }
                } else {
                    return false;
                }
            }else {
                sender.sendMessage(PREFIX + "§cDie Console ist immer ein God!");
            }
        } else if (args.length == 1) {
            if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_GODMODE)) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if(target != sender) {
                        if (GODMODE_PLAYER_LIST.contains(target.getUniqueId())) {
                            GODMODE_PLAYER_LIST.remove(target.getUniqueId());
                            sender.sendMessage(PREFIX + "§fDer Spieler §b" + args[0] + "§f ist nun §cnicht §fmehr im godmode");
                            target.sendMessage(PREFIX + "§fDu bist nun §cnicht §fmehr im godmode");
                        } else {
                            GODMODE_PLAYER_LIST.add(target.getUniqueId());
                            target.sendMessage(PREFIX + "§fDu bist nun im godmode");
                            sender.sendMessage(PREFIX + "§fDer Spieler §b" + args[0] + "§f ist nun im godmode");
                        }
                    }else {
                        sendSyntax(sender);
                    }
                } else {
                    sender.sendMessage(PREFIX + "§cDer Spieler §b" + args[0] + "§c ist nicht online!");
                }
            } else {
                return false;
            }
        } else {
            sendSyntax(sender);
        }
        return false;
    }

    static void sendSyntax(final CommandSender sender) {
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/godmode §coder §b/godmode §7[§eSpieler§7]");
    }

    @EventHandler
    private void playerDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player player){
            if(GODMODE_PLAYER_LIST.contains(player.getUniqueId())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void playerFoodLevel(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player player){
            if(GODMODE_PLAYER_LIST.contains(player.getUniqueId())){
                event.setCancelled(true);
            }
        }
    }
}
