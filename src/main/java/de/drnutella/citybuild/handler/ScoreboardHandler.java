package de.drnutella.citybuild.handler;

import de.drnutella.citybuild.utils.StringFormation;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardHandler {
    public final Map<UUID, FastBoard> boardMap = new HashMap<>();

    public static final String coloredDomain = StringFormation.convertToHEXWithExtra("???.net", new String[]
            {
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFE259",
                    "#FFCE56",
                    "#FFBB54",
                    "#FFA751",
                    "#FFA751",
            }, new String[]{"§l"});

    public void createPlayerScoreboard(final Player player) {
        final FastBoard board = new FastBoard(player);
        board.updateTitle("  " + coloredDomain + "  ");
        board.updateLines(
                "",
                "§e§lServer: ",
                "§f➡ Citybuild",
                ""

        );
        boardMap.put(player.getUniqueId(), board);
    }

    public void removeScoreboard(final Player player) {
        final FastBoard board = this.boardMap.get(player.getUniqueId());
        this.boardMap.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    //Update given Board and set it to the player
    public void updateBoard(final FastBoard board, final Player player) {
        board.updateLines(
                "",
                "§e§lServer: ",
                "§f➡ Citybuild",
                ""

        );
    }
}
