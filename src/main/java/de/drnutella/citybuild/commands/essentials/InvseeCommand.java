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

public class InvseeCommand implements CommandExecutor, Listener {

    public static String PLAYER_VIEW_PLAYER_INV = "earth-player-view-player-inv";

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    player.setMetadata(PLAYER_VIEW_PLAYER_INV, new FixedMetadataValue(Citybuild.getInstance(), true));
                    player.openInventory(target.getInventory());
                } else {
                    player.sendMessage(PREFIX + "§cDer Spieler ist nicht Online!");
                }
            }else {
                sendSyntax(player);
            }
        }else {
            sender.sendMessage(PREFIX + "§cDu musst ein Spieler sein");
        }
        return false;
    }

    final void sendSyntax(final CommandSender sender) {
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/invsee §7[§eSpieler§7]");
    }

    @EventHandler
    private void inventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().hasMetadata(PLAYER_VIEW_PLAYER_INV)) {
            final boolean isForeign = event.getWhoClicked().getMetadata(PLAYER_VIEW_PLAYER_INV).get(0).asBoolean();

            if (event.getInventory().getType() != InventoryType.PLAYER) {
                return;
            }
            if(!event.getWhoClicked().hasPermission(PermissionsConfigAdapter.PERMISSION_INVSEE_EDIT)){
                if (isForeign) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    private void inventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.PLAYER) {
            if (event.getPlayer().hasMetadata(PLAYER_VIEW_PLAYER_INV)) {
                event.getPlayer().removeMetadata(PLAYER_VIEW_PLAYER_INV, Citybuild.getInstance());
            }
        }
    }

}
