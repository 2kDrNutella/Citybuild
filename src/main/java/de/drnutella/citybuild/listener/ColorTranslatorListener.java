package de.drnutella.citybuild.listener;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ColorTranslatorListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(event.getPlayer().hasPermission(PermissionsConfigAdapter.PERMISSION_COLOR_CHAT)) {
            final String message = event.getMessage();
            event.setMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    @EventHandler
    public void onSignUpdate(SignChangeEvent event) {
        if(event.getPlayer().hasPermission(PermissionsConfigAdapter.PERMISSION_COLOR_SIGN)) {
            for (int i = 0; i < event.getLines().length; i++) {
                final String line = event.getLine(i);
                if (line != null) {
                    event.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
                }
            }
        }
    }

}
