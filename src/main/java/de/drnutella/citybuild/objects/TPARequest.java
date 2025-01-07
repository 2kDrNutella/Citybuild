package de.drnutella.citybuild.objects;

import org.bukkit.entity.Player;

public class TPARequest {


    public Player sender;
    public Long sendTime;
    public int timeOutSeconds;
    public boolean isAccepted = false;
    public boolean isIgnored = false;

    public TPARequest(Player sender, int timeOutSeconds, boolean ignored) {
        this.sender = sender;
        this.sendTime = System.currentTimeMillis();
        this.timeOutSeconds = timeOutSeconds;
        this.isIgnored = ignored;
    }

    public boolean isIgnored() {
        return isIgnored;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isStillValid() {
        return (System.currentTimeMillis() <= (sendTime + (timeOutSeconds * 1000L)));
    }
}
