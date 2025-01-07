package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class WorkstationCommand implements CommandExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            if (command.getName().equalsIgnoreCase("workbench")) {
                player.openInventory(Bukkit.createInventory(player, InventoryType.WORKBENCH));
            } else if (command.getName().equalsIgnoreCase("stonecutter")) {
                player.openInventory(Bukkit.createInventory(player, InventoryType.STONECUTTER));
            } else if (command.getName().equalsIgnoreCase("smithingtable")) {
                player.openInventory(Bukkit.createInventory(player, InventoryType.SMITHING));
            } else if (command.getName().equalsIgnoreCase("grindstone")) {
                player.openInventory(Bukkit.createInventory(player, InventoryType.GRINDSTONE));
            } else if (command.getName().equalsIgnoreCase("loom")) {
                player.openInventory(Bukkit.createInventory(player, InventoryType.LOOM));
            } else if (command.getName().equalsIgnoreCase("anvil")) {
                player.openInventory(Bukkit.createInventory(player, InventoryType.ANVIL));
            }
        }else {
            sender.sendMessage(PREFIX + "§cDieser Befehl ist nur für Spieler!");
        }
        return false;
    }

}
