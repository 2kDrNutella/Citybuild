package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlyCommand implements CommandExecutor, TabExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_FLY)) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    if (player.getAllowFlight()) {
                        player.setFlying(false);
                        player.setAllowFlight(false);
                        player.sendMessage(PREFIX + "§fDu kannst nun §cnicht mehr §ffliegen");
                    } else {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.sendMessage(PREFIX + "§fDu kannst §anun §ffliegen");
                    }
                }
            } else if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(PREFIX + "§cDer Spieler §b" + args[0] + " §cwurde nicht gefunden!");
                    return false;
                }
                if (target.getAllowFlight()) {
                    target.setFlying(false);
                    target.setAllowFlight(false);
                    target.sendMessage(PREFIX + "§fDu kannst nun §cnicht mehr §ffliegen");
                    sender.sendMessage(PREFIX + "§fDer Spieler §b" + target.getName() + "§f kann nun §cnicht mehr §ffliegen!");
                } else {
                    target.setAllowFlight(true);
                    target.setFlying(true);
                    target.sendMessage(PREFIX + "§fDu kannst §anun §ffliegen");
                    sender.sendMessage(PREFIX + "§fDer Spieler §b" + target.getName() + "§f kann §anun §ffliegen!");
                }
            } else {
                sendSyntax(sender);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();

        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_GAMEMODE)) {

            if (args.length == 1) {
                if(sender instanceof Player){
                    for (Player player : Bukkit.getOnlinePlayers()){
                        if(((Player) sender).canSee(player)){
                            commands.add(player.getName());
                        }
                    }
                }else {
                    for (Player player : Bukkit.getOnlinePlayers()){
                        commands.add(player.getName());
                    }
                }
                StringUtil.copyPartialMatches(args[0], commands, completions);
            }

            Collections.sort(completions);
        }
        return completions;
    }

    static void sendSyntax(final CommandSender sender) {
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/fly §coder §b/fly §7[§eSpieler§7]");
    }

}
