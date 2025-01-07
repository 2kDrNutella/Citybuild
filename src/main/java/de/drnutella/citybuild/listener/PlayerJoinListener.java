package de.drnutella.citybuild.listener;

import de.drnutella.citybuild.Citybuild;
import de.drnutella.citybuild.handler.TablistHandler;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    final TablistHandler tablistHandler = Citybuild.getInstance().getTablistHandler();

    @EventHandler
    private void playerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        event.setJoinMessage("");

        for (final Player all : Bukkit.getOnlinePlayers()) {
            if (!all.equals(player)) {
                all.sendMessage("§7[§a+§7] " + player.getName());
            }
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.teleport(ConfigAdapter.getSpawnLocationFromConfig());

        Citybuild.getInstance().getScoreboardHandler().createPlayerScoreboard(player);

        tablistHandler.sendTablistDetails(player);
    }
}
