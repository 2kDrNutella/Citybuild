package de.drnutella.citybuild.listener.spam;

import de.drnutella.citybuild.Citybuild;
import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.handler.MessageSpamHandler;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


public class CommandSpamListener implements Listener {

    final MessageSpamHandler messageSpamHandler = Citybuild.getInstance().getCommandMessageSpamHandler();

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        final String playerName = event.getPlayer().getName();
        final long currentTime = System.currentTimeMillis() / 1000;

        if(!event.getPlayer().hasPermission(PermissionsConfigAdapter.PERMISSION_SPAM)) {
            if (messageSpamHandler.isChatSpam(playerName, currentTime)) {
                event.setCancelled(true);
                event.getPlayer().kickPlayer(ConfigAdapter.PREFIX + "§c§lBitte spamme keine Befehle!");
            }
        }
    }
}
