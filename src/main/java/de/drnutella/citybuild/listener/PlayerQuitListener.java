package de.drnutella.citybuild.listener;

import de.drnutella.citybuild.commands.essentials.GodmodeCommand;
import de.drnutella.citybuild.commands.essentials.TpaCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    private void playerQuit(PlayerQuitEvent event){
        event.setQuitMessage("");

        final Player player = event.getPlayer();

        TpaCommand.REQUEST_LIST.remove(player.getUniqueId()); // Clear all opened TPAs to the Player
        GodmodeCommand.GODMODE_PLAYER_LIST.remove(player.getUniqueId()); // Reset Godmode

        for (final Player all : Bukkit.getOnlinePlayers()){
            if(!all.equals(player)){
                all.sendMessage("§7[§c-§7] " + player.getName());
            }
        }
    }

}
