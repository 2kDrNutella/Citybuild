package de.drnutella.citybuild.utils;

import de.drnutella.citybuild.Citybuild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class ConfigAdapter {

    private static final FileConfiguration config = Citybuild.getInstance().getConfigFile().getConfig();

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&',
            Objects.requireNonNull(config.getString("prefix")));

    public static final String EMPTY_SPACE_PREFIX = ChatColor.translateAlternateColorCodes('&',
            Objects.requireNonNull(config.getString("emptyspace")));

    public static final String ALLOWED_COMMANDS = config.getString("blockedCommands");
    public static Location getSpawnLocationFromConfig(){
        return new Location(
                Bukkit.getWorld(config.getString("spawn.World")),
                config.getDouble("spawn.posX"),
                config.getDouble("spawn.posY"),
                config.getDouble("spawn.posZ"),
                (float) config.getDouble("spawn.Yaw"),
                (float) config.getDouble("spawn.Pitch"));
    }

}
