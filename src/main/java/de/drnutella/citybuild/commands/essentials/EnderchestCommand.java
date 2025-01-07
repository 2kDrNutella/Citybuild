package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.Citybuild;
import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.metadata.FixedMetadataValue;

public class EnderchestCommand implements CommandExecutor, Listener {

    public static String PLAYER_VIEW_NOT_OWNED_EC_KEY = "earth-view-not-owned-ec";
    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.openInventory(player.getEnderChest());
            } else if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    player.setMetadata(PLAYER_VIEW_NOT_OWNED_EC_KEY, new FixedMetadataValue(Citybuild.getInstance(), true));
                    player.openInventory(target.getEnderChest());
                } else {
                    player.sendMessage(PREFIX + "§cDer Spieler ist nicht Online!");
                }
            }
        } else {
            sender.sendMessage(PREFIX + "§cDu kannst nur als Spieler Enderchests aufmachen!");
        }
        return false;
    }

    @EventHandler
    private void inventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().hasMetadata(PLAYER_VIEW_NOT_OWNED_EC_KEY)) {
            final boolean isForeign = event.getWhoClicked().getMetadata(PLAYER_VIEW_NOT_OWNED_EC_KEY).get(0).asBoolean();

            if (event.getInventory().getType() != InventoryType.ENDER_CHEST) {
                return;
            }
            if(!event.getWhoClicked().hasPermission(PermissionsConfigAdapter.PERMISSION_ENDERCHEST_EDIT)){
                if (isForeign) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    private void inventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
            if (event.getPlayer().hasMetadata(PLAYER_VIEW_NOT_OWNED_EC_KEY)) {
                event.getPlayer().removeMetadata(PLAYER_VIEW_NOT_OWNED_EC_KEY, Citybuild.getInstance());
            }
        }
    }
}
