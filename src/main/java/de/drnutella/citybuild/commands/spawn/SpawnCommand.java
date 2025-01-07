package de.drnutella.citybuild.commands.spawn;

import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            return false;
        }

        player.teleport(ConfigAdapter.getSpawnLocationFromConfig());
        player.sendMessage(ConfigAdapter.PREFIX + "§6Du bist nun beim Spawn");
        return false;
    }
}
