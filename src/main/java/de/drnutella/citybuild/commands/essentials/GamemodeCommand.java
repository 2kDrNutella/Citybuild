package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        GameMode gameMode;
        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_GAMEMODE)) {
            if (args.length == 1) {
                if (sender instanceof Player player) {
                    gameMode = getGameMode(args[0]);
                    if (gameMode != null) {
                        player.setGameMode(gameMode);
                        player.sendMessage(PREFIX + "§fDein Gamemode wurde auf §b" + gameMode.name() + "§f geändert!");
                    } else {
                        sendWrongSyntaxMessage(player);
                    }
                } else {
                    sender.sendMessage(PREFIX + "§cDer Gamemode Befehl ist nur für Spieler! Oder Benutzte /gamemode [Spieler] [Gamemode]");
                }
            } else if (args.length == 2) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(PREFIX + "§cDer Spieler §b" + args[0] + " §cwurde nicht gefunden!");
                } else {
                    gameMode = getGameMode(args[1]);
                    if (gameMode != null) {
                        target.setGameMode(gameMode);
                        sender.sendMessage(PREFIX + "§fDer Gamemode von §b" + target.getName() + " §fwurde auf §b" + gameMode.name() + "§f geändert!");
                        target.sendMessage(PREFIX + "§fDein Gamemode wurde von §b" + sender.getName() + " §fauf §b" + gameMode.name() + "§f geändert!");
                    } else {
                        sendWrongSyntaxMessage(sender);
                    }
                }
            } else {
                sendWrongSyntaxMessage(sender);
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        final List<String> gamemodeCodes = new ArrayList<>(List.of("0","1", "2", "3", "survival", "creative", "adventure", "spectator"));

        if (sender.hasPermission(PermissionsConfigAdapter.PERMISSION_GAMEMODE)) {

            if (args.length == 1) {
                commands.add("survival");
                commands.add("creative");
                commands.add("adventure");
                commands.add("spectator");
                commands.add("[Spielername]");
                StringUtil.copyPartialMatches(args[0], commands, completions);
            } else if (args.length == 2 && !gamemodeCodes.contains(args[0].toLowerCase())) {
                commands.add("survival");
                commands.add("creative");
                commands.add("adventure");
                commands.add("spectator");
                StringUtil.copyPartialMatches(args[1], commands, completions);
            }

            Collections.sort(completions);
        }
        return completions;
    }

    static void sendWrongSyntaxMessage(final CommandSender sender){
        sender.sendMessage(PREFIX + "§cBitte benutzte: §7/§bgamemode §7[§e1§7,§e 2§7,§e 3§7] §coder §b/gamemode §7[§ecreative§7, §esurvival§7, §espectator§7]");
        sender.sendMessage(PREFIX + "§cBitte benutzte §7/§bgamemode §7[§e1§7,§e 2§7,§e 3§7] §coder §b/gamemode §7[§ecreative§7, §esurvival§7, §espectator§7]");
    }

    static GameMode getGameMode(final String input) {
        try {
            final int gamemodeNumber = Integer.parseInt(input);
            return switch (gamemodeNumber) {
                case 0 -> GameMode.SURVIVAL;
                case 1 -> GameMode.CREATIVE;
                case 2 -> GameMode.ADVENTURE;
                case 3 -> GameMode.SPECTATOR;
                default -> null;
            };
        } catch (final NumberFormatException e) {
            return switch (input.toLowerCase()) {
                case "survival" -> GameMode.SURVIVAL;
                case "creative" -> GameMode.CREATIVE;
                case "adventure" -> GameMode.ADVENTURE;
                case "spectator" -> GameMode.SPECTATOR;
                default -> null;
            };
        }
    }
}