package de.drnutella.citybuild.commands.essentials;

import de.drnutella.citybuild.utils.PermissionsConfigAdapter;
import de.drnutella.citybuild.utils.ConfigAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.UUID;

public class SuicideCommand implements CommandExecutor, Listener {

    public static final ArrayList<UUID> SUCIDE_PLAYER_LIST = new ArrayList<>();
    final static String PREFIX = ConfigAdapter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission(PermissionsConfigAdapter.PERMISSION_SUICIDE)){
            if(args.length == 0){
                if(sender instanceof Player player){
                    SUCIDE_PLAYER_LIST.add(player.getUniqueId());
                    player.setHealth(0D);
                    player.sendMessage(PREFIX + "§fDu hast dich umgebracht");
                }
            }else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    SUCIDE_PLAYER_LIST.add(target.getUniqueId());
                    target.sendMessage(PREFIX + "§fDu wurdest umgebracht");
                    target.setHealth(0D);
                    sender.sendMessage(PREFIX + "§fDu hast §b" + target.getName() + "§f umgebracht");
                } else {
                    sender.sendMessage(PREFIX + "§cDer Spieler ist nicht online");
                }
            } else {
                sendSyntax(sender);
            }
        }
        return false;
    }

    @EventHandler
    private void playerDamage(PlayerDeathEvent event){
        if(SUCIDE_PLAYER_LIST.contains(event.getEntity().getUniqueId())){
            final Player player = event.getEntity();
            event.setDeathMessage("§7[§c☠§7] §b" + player.getName() + "§f brachte sich um");
            SUCIDE_PLAYER_LIST.remove(player.getUniqueId());
        }
    }

    static void sendSyntax(final CommandSender sender) {
        sender.sendMessage(PREFIX + "§cBitte benutzte §b/suicide §coder §b/suicide §7[§eSpieler§7]");
    }
}
