package de.drnutella.citybuild.handler;

import java.util.HashMap;
import java.util.Map;

public class MessageSpamHandler {


    private final int MESSAGE_COOLDOWN_SECONDS = 7;
    private final int MAX_MESSAGES = 3;
    Map<String, Long> lastMessageTimes = new HashMap<>();
    Map<String, Integer> lastMessageCount = new HashMap<>();

    public boolean isChatSpam(String playerName, long currentTime) {
        if (!lastMessageTimes.containsKey(playerName)) {
            lastMessageTimes.put(playerName, currentTime);
            lastMessageCount.put(playerName, 0);
            return false;
        }

        final long lastCommandTime = lastMessageTimes.get(playerName);

        if (currentTime - lastCommandTime < MESSAGE_COOLDOWN_SECONDS) {
            final int commandCount = lastMessageCount.get(playerName);
            if (commandCount >= MAX_MESSAGES) {
                return true;
            }
            lastMessageCount.put(playerName, commandCount + 1);
        } else {
            lastMessageTimes.put(playerName, currentTime);
            lastMessageCount.put(playerName, 1);
        }
        return false;
    }
}
