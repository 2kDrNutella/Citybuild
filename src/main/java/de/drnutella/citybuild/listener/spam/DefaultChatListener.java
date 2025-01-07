package de.drnutella.citybuild.listener.spam;

import de.drnutella.citybuild.Citybuild;
import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.handler.MessageSpamHandler;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DefaultChatListener implements Listener {
    final MessageSpamHandler messageSpamHandler = Citybuild.getInstance().getChatMessageSpamHandler();

    @EventHandler
    private void PlayerChat(AsyncPlayerChatEvent event) {
        long currentTime = System.currentTimeMillis() / 1000;

        if (!event.getPlayer().hasPermission(PermissionsConfigAdapter.PERMISSION_SPAM)) {
            if (isKeyboardPrintable(event.getMessage())) {
                if (messageSpamHandler.isChatSpam(event.getPlayer().getName(), currentTime)) {
                    event.getPlayer().sendMessage(ConfigAdapter.PREFIX + "§cBitte spamme nicht!");
                }
            } else {
                event.getPlayer().sendMessage(ConfigAdapter.PREFIX + "§cDu benutzt nicht erlaubte Zeichen!");
            }
        }
        event.setCancelled(true);
    }

    public boolean isKeyboardPrintable(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c) && !isPunctuation(c) && !isSpecialCharacter(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPunctuation(char c) {
        final String punctuation = ".,;:!?";
        return punctuation.indexOf(c) != -1;
    }

    private boolean isSpecialCharacter(char c) {
        final String specialCharacters = "~`@#$%^&*()_-+={}[]|\\<>/\"'";
        return specialCharacters.indexOf(c) != -1;
    }
}
