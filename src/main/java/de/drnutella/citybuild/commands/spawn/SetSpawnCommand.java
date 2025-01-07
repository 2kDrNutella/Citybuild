package de.drnutella.citybuild.commands.spawn;

import de.drnutella.citybuild.Citybuild;
import de.drnutella.citybuild.utils.ConfigBuilder;
import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    final static ConfigBuilder configFile = Citybuild.getInstance().getConfigFile();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,  String[] args) {
        if(command.getName().equalsIgnoreCase("setspawn")){
            if(commandSender instanceof Player player){
                if(player.hasPermission(PermissionsConfigAdapter.PERMISSION_SETSPAWN)){
                    configFile.getConfig().set("spawn.World", player.getWorld().getName());
                    configFile.getConfig().set("spawn.posX", player.getLocation().getX());
                    configFile.getConfig().set("spawn.posY", player.getLocation().getY());
                    configFile.getConfig().set("spawn.posZ", player.getLocation().getZ());
                    configFile.getConfig().set("spawn.Yaw", (float) player.getLocation().getYaw());
                    configFile.getConfig().set("spawn.Pitch", (float) player.getLocation().getPitch());

                    configFile.save();
                    player.sendMessage(ConfigAdapter.PREFIX + "§aDu hast den Spawn erfolgreich gesetzt!");
                }else {
                    player.sendMessage(ConfigAdapter.PREFIX + "§cDu hast keine Rechte dazu!");
                }

            }else{
                commandSender.sendMessage(ConfigAdapter.PREFIX + "§cDu musst ein Spieler sein!");
            }
        }
        return false;
    }
}