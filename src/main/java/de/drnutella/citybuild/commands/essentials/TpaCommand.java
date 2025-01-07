package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.Citybuild;
import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.objects.TPARequest;
import de.drnutella.citybuild.utils.ConfigAdapter;
import de.drnutella.citybuild.utils.TextComponentUtils;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

//T0Do: Merge class with TpahereCommand

public class TpaCommand implements CommandExecutor {

    public static final HashMap<UUID, ArrayList<TPARequest>> REQUEST_LIST = new HashMap<>();

    final static ArrayList<UUID> PLAYER_IGNORE_ALL_TPA_LIST = new ArrayList<>();
    final static HashMap<UUID, ArrayList<UUID>> IGNORED_PLAYER_LIST = new HashMap<>();
    final static ArrayList<Player> TPA_RUNNING_LIST = new ArrayList<>();

    final static String PREFIX = ConfigAdapter.PREFIX;
    final static String EMPTY_SPACE = ConfigAdapter.EMPTY_SPACE_PREFIX;
    final static int TIMEOUT_SECONDS = 120;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            final UUID playerUUID = player.getUniqueId();
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("accept")) {
                    if (!TPA_RUNNING_LIST.contains(player)) {
                        final TPARequest latestTPA = getLastestTPA(playerUUID);

                        if (latestTPA != null) {
                            if (!latestTPA.isAccepted) {
                                runTPA(player, latestTPA);
                            } else {
                                player.sendMessage(PREFIX + "§cDu hast die Anfrage bereits angenommen!");
                            }
                        } else {
                            player.sendMessage(PREFIX + "§cDu hast keine offene TPAs!");
                        }
                    } else {
                        player.sendMessage(PREFIX + "§cDu darfst momentan keine §bTPAs §cannehmen!");
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    final TPARequest latestTPA = getLastestTPA(playerUUID);
                    if (latestTPA != null) {
                        final Player targetPlayer = latestTPA.sender;
                        if (targetPlayer.isOnline()) {
                            removeRequestFromList(playerUUID, latestTPA);
                            targetPlayer.sendMessage(PREFIX + "Der Spieler §b" + player.getName() + "§f hat deine §bTPA §cabgelehnt!");
                            player.sendMessage(PREFIX + "Du hast die §bTPA§f von §b" + targetPlayer.getName() + "§c abgelehnt");
                        }
                    } else {
                        player.sendMessage(PREFIX + "§cDu hast keine offene TPA von diesem Spieler!");
                    }
                } else {

                    final Player target = Bukkit.getPlayer(args[0]);
                    if (!TPA_RUNNING_LIST.contains(player)) {
                        if (target != null && target.canSee(player)) {
                            if (player != target) {
                                if (!hasSendTPA(player, target.getUniqueId())) {
                                    if(isPlayerIgnored(target.getUniqueId(), playerUUID)){
                                        if(player.hasPermission(PermissionsConfigAdapter.PERMISSION_ADMIN_TPA)){
                                            target.sendMessage("");
                                            target.sendMessage(PREFIX + "§c§l" + player.getName() + " §fmöchte sich zu dir teleportieren!");
                                            target.spigot().sendMessage(getTPAButtonsComponent(player.getName()));
                                            target.sendMessage("");
                                            addRequestToList(target.getUniqueId(), new TPARequest(player, TIMEOUT_SECONDS, false));
                                        }else {
                                            player.sendMessage(PREFIX + "§cDu wirst von §b" + target.getName() + "§c ignoriert!");
                                            addRequestToList(target.getUniqueId(), new TPARequest(player, TIMEOUT_SECONDS, true));
                                        }
                                    }else {
                                        player.sendMessage("");
                                        player.sendMessage(PREFIX + "§fDu hast die §bTPA §ferfolgreich §fgesendet!");
                                        player.sendMessage("");

                                        target.sendMessage("");
                                        target.sendMessage(PREFIX + "§fDer Spieler §b" + player.getName() + " §fmöchte sich zu dir teleportieren!");
                                        target.spigot().sendMessage(getTPAButtonsComponent(player.getName()));
                                        target.sendMessage("");

                                        addRequestToList(target.getUniqueId(), new TPARequest(player, TIMEOUT_SECONDS, false));
                                    }
                                }else {
                                    player.sendMessage(PREFIX + "§cDu hast bereits eine TPA an §b" + target.getName() + "§c gesendet!");
                                }
                            } else {
                                player.sendMessage(PREFIX + "§cDu kannst dir nicht selber eine TPA schicken!");
                            }
                        } else {
                            player.sendMessage(PREFIX + "§cDer Spieler §b" + args[0] + " §cist nicht online!");
                        }
                    } else {
                        player.sendMessage(PREFIX + "§cDu darfst momentan keine §bTPA §cschicken!");
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("accept")) {
                    final Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer == null) {
                        player.sendMessage(PREFIX + "§cDer Spieler §b" + args[1] + " §cist nicht online!");
                        return false;
                    }
                    if (hasSendTPA(targetPlayer, playerUUID)) {
                        final TPARequest latestTPA = getTPARequestFromPlayer(player, targetPlayer);
                        if (latestTPA != null) {
                            if (!latestTPA.isAccepted) {
                                runTPA(player, latestTPA);
                            } else {
                                player.sendMessage(PREFIX + "§cDu hast die Anfrage bereits angenommen!");
                            }
                        } else {
                            player.sendMessage(PREFIX + "§cDu hast keine offene TPA von diesem Spieler!");
                        }
                    } else {
                        player.sendMessage(PREFIX + "§cDu hast keine offene TPA von diesem Spieler!");
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    final Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer == null) {
                        player.sendMessage(PREFIX + "§cDer Spieler §b" + args[1] + " §cist nicht online!");
                        return false;
                    }
                    if (hasSendTPA(targetPlayer, playerUUID)) {
                        final TPARequest latestTPA = getTPARequestFromPlayer(player, targetPlayer);
                        if (latestTPA != null) {
                            removeRequestFromList(playerUUID, latestTPA);
                            targetPlayer.sendMessage(PREFIX + "§fDer Spieler §b" + player.getName() + "§f hat deine §bTPA §cabgelehnt!");
                            player.sendMessage(PREFIX + "§fDu hast die §bTPA§f von §b" + targetPlayer.getName() + "§c abgelehnt");

                        } else {
                            player.sendMessage(PREFIX + "§cDu hast keine offene TPA von diesem Spieler!");
                        }
                    } else {
                        player.sendMessage(PREFIX + "§cDu hast keine offene TPA von diesem Spieler!");
                    }
                }else if (args[0].equalsIgnoreCase("ignore")) {
                    if(args[1].equalsIgnoreCase("*")){
                        if(!isPlayerIgnoreAll(playerUUID)){
                            PLAYER_IGNORE_ALL_TPA_LIST.add(playerUUID);
                            player.sendMessage(PREFIX + "§fDu ignorierst nun §bALLE §fSpieler");
                        }else {
                            PLAYER_IGNORE_ALL_TPA_LIST.remove(playerUUID);
                            player.sendMessage(PREFIX + "§fDu ignorierst nun §cnicht §fmehr §bALLE §fSpieler");
                        }
                        return true;
                    }

                    final OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);
                    final UUID targetPlayerUUID = targetPlayer.getUniqueId();

                    if(!isPlayerIgnored(playerUUID, targetPlayerUUID)){
                        togglePlayerIgnorePlayer(playerUUID, targetPlayerUUID);
                        player.sendMessage(PREFIX + "§fDu ignorierst den Spieler §b" + args[1] + "§f nun temporär");
                    }else {
                        togglePlayerIgnorePlayer(playerUUID, targetPlayerUUID);
                        player.sendMessage(PREFIX + "§fDu ignorierst den Spieler §b" + args[1] + "§f nun §cnicht §fmehr");
                    }
                }
            } else {
                sendSyntax(sender);
            }
        } else {
            sender.sendMessage(PREFIX + "Die Console darf keine TPAs schicken! :P");
        }
        return false;
    }

    static void runTPA(Player reciver, TPARequest tpaRequest) {
        final Player sender = tpaRequest.sender;
        if (sender == null) {
            reciver.sendMessage(PREFIX + "§cEtwas ist schiefgelaufen! Die TPA wurde abgebrochen!");
            return;
        }

        if (sender.isOnline() && tpaRequest.isStillValid() && reciver.isOnline()) {
            sender.sendMessage(PREFIX + "§fDu wirst in 5 Sekunden zu §b" + reciver.getName() + "§f teleportiert");
            reciver.sendMessage(PREFIX + "§fDer Spieler §b" + sender.getName() + "§f wird in 5 Sekunden zu dir teleportiert");

            tpaRequest.setAccepted(true);
            TPA_RUNNING_LIST.add(sender);
            TPA_RUNNING_LIST.add(reciver);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (sender.isOnline() && reciver.isOnline()) {
                        sender.teleport(reciver);
                        sender.sendMessage(PREFIX + "§fDu wurdest zu §b" + reciver.getName() + "§f teleportiert");
                        reciver.sendMessage(PREFIX + "§fDer Spieler §b" + sender.getName() + "§f wurde zu dir teleportiert");
                    } else {
                        reciver.sendMessage(PREFIX + "§cDie TPA wurde von §b" + sender.getName() + "§c abgebrochen");
                        sender.sendMessage(PREFIX + "§cDie TPA wurde von §b" + reciver.getName() + "§c abgebrochen");
                    }

                    TPA_RUNNING_LIST.remove(sender);
                    TPA_RUNNING_LIST.remove(reciver);
                    removeRequestFromList(reciver.getUniqueId(), tpaRequest);
                    cancel();
                }
            }.runTaskLater(Citybuild.getInstance(), 20L * 5);
        } else {
            reciver.sendMessage(PREFIX + "§cDu hast keine offene TPAs!");
        }
    }

    static Boolean isPlayerIgnored(UUID sender, UUID reciver){
        if(isPlayerIgnoreAll(sender)){
            return true;
        }else {
            return IGNORED_PLAYER_LIST.getOrDefault(sender, new ArrayList<>()).contains(reciver);
        }
    }

    static Boolean isPlayerIgnoreAll(UUID sender){
        return PLAYER_IGNORE_ALL_TPA_LIST.contains(sender);
    }

    static void togglePlayerIgnorePlayer(UUID sender, UUID ignoredUUID){
        final ArrayList<UUID> ignoredPlayers = IGNORED_PLAYER_LIST.getOrDefault(sender, new ArrayList<>());
        if(ignoredPlayers.contains(ignoredUUID)){
            ignoredPlayers.remove(ignoredUUID);
        }else {
            ignoredPlayers.add(ignoredUUID);
        }
        IGNORED_PLAYER_LIST.put(sender, ignoredPlayers);
    }

    static Boolean hasSendTPA(Player sender, UUID reciver) {
        final ArrayList<TPARequest> currentRequests = REQUEST_LIST.getOrDefault(reciver, new ArrayList<>());
        for (final TPARequest request : currentRequests) {
            if (request.isStillValid()) {
                if (request.sender.equals(sender)) {
                    return true;
                }
            } else {
                removeRequestFromList(reciver, request);
                return false;
            }
        }
        return false;
    }

    static TPARequest getTPARequestFromPlayer(Player reciver, Player sender) {
        final ArrayList<TPARequest> currentRequests = REQUEST_LIST.getOrDefault(reciver.getUniqueId(), new ArrayList<>());
        for (final TPARequest request : currentRequests) {
            if (request.isStillValid()) {
                if(!request.isIgnored) {
                    if (request.sender.equals(sender)) {
                        return request;
                    }
                }
            } else {
                removeRequestFromList(reciver.getUniqueId(), request);
            }
        }
        return null;
    }

    static TPARequest getLastestTPA(UUID reciverUUID) {
        final ArrayList<TPARequest> currentRequests = REQUEST_LIST.getOrDefault(reciverUUID, new ArrayList<>());
        if (!currentRequests.isEmpty()) {
            for (final TPARequest request : currentRequests) {
                if (!request.isStillValid()) {
                    removeRequestFromList(reciverUUID, request);
                }
            }

            TPARequest tpaRequest;
            for (int i = 1; i < currentRequests.size() + 1; i++) {
                tpaRequest = currentRequests.get(currentRequests.size() - i);
                if (!tpaRequest.isIgnored) {
                    return tpaRequest;
                }
            }
        }
        return null;
    }

    static void addRequestToList(UUID targetUUID, TPARequest request) {
        final ArrayList<TPARequest> currentRequests = REQUEST_LIST.getOrDefault(targetUUID, new ArrayList<>());
        currentRequests.add(request);
        REQUEST_LIST.put(targetUUID, currentRequests);
    }

    static void removeRequestFromList(UUID reciverUUID, TPARequest request) {
        final ArrayList<TPARequest> currentRequests = REQUEST_LIST.getOrDefault(reciverUUID, new ArrayList<>());
        currentRequests.remove(request);
        REQUEST_LIST.put(reciverUUID, currentRequests);
    }

    static TextComponent getTPAButtonsComponent(String tpaSenderName){
        return new TextComponent(
                new TextComponent(EMPTY_SPACE),
                new TextComponentUtils("§7[§a§l/tpa accept§7]").setRunCommand("/tpa accept " + tpaSenderName)
                        .setTexteHover("§aAkzeptiere die TPA").toTexte(),
                new TextComponent("§f oder "),
                new TextComponentUtils("§7[§c§l/tpa deny§7]").setRunCommand("/tpa deny " + tpaSenderName)
                        .setTexteHover("§cLehne die TPA ab").toTexte()
        );
    }

    static void sendSyntax(final CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(PREFIX + "§cBitte benuzte:");
        sender.sendMessage("§b/tpa §7[§eSpielername§7] §fum eine TPA zu senden");
        sender.sendMessage("§b/tpa accept §7[§eSpielername§7] §fum die TPA vom Spieler anzunehmen");
        sender.sendMessage("§b/tpa accept §fum die letzte TPA anzunehmen");
        sender.sendMessage("§b/tpa deny §7[§eSpielername§7] §fum die TPA vom Spieler abzulehnen");
        sender.sendMessage("§b/tpa deny §fum die letzte TPA abzulehnen");
        sender.sendMessage("§b/tpa ignore [Name] §fum einen Spieler zu ignorieren");
        sender.sendMessage("§b/tpa ignore * §fum alle Spieler zu ignorieren");
        sender.sendMessage("");
    }
}

