package de.drnutella.citybuild.listener;

import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandPreProcessListener implements Listener {

    @EventHandler
    private void playerCommandProcess(PlayerCommandPreprocessEvent event){
        final Player player = event.getPlayer();
        String usedCommand = event.getMessage().toLowerCase();
        usedCommand = usedCommand.replace("/", "");

        final List<String> usedCommandParts = List.of(usedCommand.split(" "));


        String blockCommandString = ConfigAdapter.ALLOWED_COMMANDS;
        blockCommandString = blockCommandString.replace(" ", "");
        final List<String> blockedCommands = List.of(blockCommandString.split(","));

        if (!player.isOp()) {
            if (blockedCommands.contains(usedCommandParts.get(0))) {
                player.sendMessage(ConfigAdapter.PREFIX + "§cDas darfst du nicht!");
                event.setCancelled(true);
            }
        }
    }
}
