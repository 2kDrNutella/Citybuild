package de.drnutella.citybuild;

import de.drnutella.citybuild.commands.essentials.*;
import de.drnutella.citybuild.commands.spawn.SetSpawnCommand;
import de.drnutella.citybuild.commands.spawn.SpawnCommand;
import de.drnutella.citybuild.handler.MessageSpamHandler;
import de.drnutella.citybuild.handler.ScoreboardHandler;
import de.drnutella.citybuild.handler.TablistHandler;
import de.drnutella.citybuild.listener.ColorTranslatorListener;
import de.drnutella.citybuild.listener.CommandPreProcessListener;
import de.drnutella.citybuild.listener.PlayerQuitListener;
import de.drnutella.citybuild.listener.spam.CommandSpamListener;
import de.drnutella.citybuild.listener.spam.DefaultChatListener;
import de.drnutella.citybuild.listener.PlayerJoinListener;
import de.drnutella.citybuild.utils.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Citybuild extends JavaPlugin {

    static Citybuild instance;

    static ScoreboardHandler scoreboardHandler;
    static TablistHandler tablistHandler;

    static ConfigBuilder config;
    static ConfigBuilder permissionConfig;

    static MessageSpamHandler chatMessageSpamHandler;
    static MessageSpamHandler commandMessageSpamHandler;

    final PluginManager pluginManager = Bukkit.getPluginManager();

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Earth V" + getDescription().getVersion() + "successfully launched!");

        scoreboardHandler = new ScoreboardHandler();
        tablistHandler = new TablistHandler();
        chatMessageSpamHandler = new MessageSpamHandler();
        commandMessageSpamHandler = new MessageSpamHandler();

        loadConfigFiles();
        loadCommands();
        loadListener();
    }

    void loadConfigFiles(){
        config = new ConfigBuilder(this, "plugins/Earth", "config.yml", "", "config");
        permissionConfig = new ConfigBuilder(this,"plugins/Earth", "permissions.yml", "", "permissions");

    }

    void loadCommands(){
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        getCommand("walkspeed").setExecutor(new WalkSpeedCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpahere").setExecutor(new TpahereCommand());
        getCommand("freez").setExecutor(new FreezCommand());
        getCommand("tp").setExecutor(new TeleportCommand());
        getCommand("tphere").setExecutor(new TeleportHereCommand());
        getCommand("godmode").setExecutor(new GodmodeCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("clear").setExecutor(new ClearCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("near").setExecutor(new NearCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("sudo").setExecutor(new SudoCommand());

        for (final String stationCommand : List.of("workbench", "stonecutter", "smithingtable", "grindstone", "loom", "anvil")){
            getCommand(stationCommand).setExecutor(new WorkstationCommand());
        }
    }

    void loadListener(){
        pluginManager.registerEvents(new ColorTranslatorListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new DefaultChatListener(), this);
        pluginManager.registerEvents(new CommandPreProcessListener(), this);
        pluginManager.registerEvents(new CommandSpamListener(), this);

        pluginManager.registerEvents(new FreezCommand(), this); //Move Event canceld if freezed
        pluginManager.registerEvents(new GodmodeCommand(), this); //Playerdamage & foodlevel change canceld
        pluginManager.registerEvents(new EnderchestCommand(), this);
        pluginManager.registerEvents(new SuicideCommand(), this);
        pluginManager.registerEvents(new InvseeCommand(), this);
    }

    public static Citybuild getInstance() {
        return instance;
    }

    public static ScoreboardHandler getScoreboardHandler() {
        return scoreboardHandler;
    }

    public static TablistHandler getTablistHandler() {
        return tablistHandler;
    }

    public static ConfigBuilder getPermissionConfig() {
        return permissionConfig;
    }

    public static ConfigBuilder getConfigFile() {
        return config;
    }

    public static MessageSpamHandler getCommandMessageSpamHandler() {
        return commandMessageSpamHandler;
    }

    public static MessageSpamHandler getChatMessageSpamHandler() {
        return chatMessageSpamHandler;
    }

}
