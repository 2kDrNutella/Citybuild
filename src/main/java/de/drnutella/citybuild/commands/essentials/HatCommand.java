package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class HatCommand implements CommandExecutor {

    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission(PermissionsConfigAdapter.PERMISSION_HAT)){
            if(sender instanceof Player player) {
                if (args.length == 0) {
                    final ItemStack playerItem = player.getInventory().getItemInMainHand();
                    if(playerItem.getType() != Material.AIR){
                        if(player.getInventory().getHelmet() == null){
                            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                            player.getInventory().setHelmet(playerItem);
                        }else {
                            final ItemStack currentPlayerHead = player.getInventory().getHelmet();
                            player.getInventory().setHelmet(new ItemStack(Material.AIR));
                            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

                            player.getInventory().setHelmet(playerItem);
                            player.getInventory().setItemInMainHand(currentPlayerHead);
                        }
                        player.sendMessage(PREFIX + "§fDu hast einen neuen Hut");
                    }
                }
            }else {
                sender.sendMessage(PREFIX + "§cDie Console hat keinen Kopf :P");
            }
        }
        return false;
    }
}
