package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WalkSpeedCommand implements CommandExecutor, TabExecutor {

    static final String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission(PermissionsConfigAdapter.PERMISSION_WALKSPEED)){
            if(sender instanceof Player player) {
                if (args.length == 0) {
                    player.sendMessage(PREFIX + "§fDein Walkspeed ist §b" + (player.getWalkSpeed()*10));
                } else if (args.length == 1) {
                    try {
                        final float initValue = Float.parseFloat(args[0]);
                        if (initValue > 10 || initValue < 0) {
                            player.sendMessage(PREFIX + "§cDein angegebener Walkspeed §7(§b" + initValue + "§7)§c muss zwischen §e0 §cund §e10 §csein!");
                            return false;
                        }else {
                            player.setWalkSpeed((initValue/10));
                            player.sendMessage(PREFIX + "§fDein Walkspeed beträgt nun §b" + initValue);
                        }
                    }catch (final NumberFormatException ignored){
                        player.sendMessage(PREFIX + "§cDein angegebener Walkspeed muss zwischen §e0 §cund §e10 §csein!");
                        return false;
                    }
                } else {
                    sendSyntax(sender);
                }
            }else {
                sender.sendMessage(PREFIX + "§cDie Console hat keinen Walkspeed :P");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();

        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_WALKSPEED)) {

            if (args.length == 1) {
                commands.add("[0 - 10]");
                StringUtil.copyPartialMatches(args[0], commands, completions);
            }

            Collections.sort(completions);
        }
        return completions;
    }

    static void sendSyntax(final CommandSender sender){
        sender.sendMessage(PREFIX + "§cBitte benuzte §b/walkspeed §7[§e0 §7- §e10§7] §czum setzten deines §bWalkspeeds");
    }
}
