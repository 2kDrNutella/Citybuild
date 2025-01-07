package de.drnutella.citybuild.handler;

import org.bukkit.entity.Player;

public class TablistHandler {

    public void sendTablistDetails(final Player player){
        player.setPlayerListHeader("\n  §6§lWillkommen in Citybuild!  \n\n");
        player.setPlayerListFooter("\n  §c???.net  \n");
    }
}
