package de.drnutella.citybuild.utils;

import de.drnutella.citybuild.Citybuild;

public class PermissionsConfigAdapter {

    private static final ConfigBuilder permissionConfig = Citybuild.getInstance().getPermissionConfig();

    public static final String PERMISSION_SPAM = permissionConfig.getConfig().getString("earth-chatSpam");

    //Commands

    public static final String PERMISSION_SETSPAWN = permissionConfig.getConfig().getString("earth-setspawn"),
            PERMISSION_GAMEMODE = permissionConfig.getConfig().getString("earth-gamemode"),
            PERMISSION_FLY = permissionConfig.getConfig().getString("earth-fly"),
            PERMISSION_WALKSPEED = permissionConfig.getConfig().getString("earth-walkspeed"),
            PERMISSION_ADMIN_TPA = permissionConfig.getConfig().getString("earth-admin-tpa"),
            PERMISSION_ADMIN_TPAHERE = permissionConfig.getConfig().getString("earth-admin-tpahere"),
            PERMISSION_FREEZ = permissionConfig.getConfig().getString("earth-freez"),
            PERMISSION_TELEPORT = permissionConfig.getConfig().getString("earth-teleport"),
            PERMISSION_TELEPORT_HERE = permissionConfig.getConfig().getString("earth-teleport-here"),
            PERMISSION_GODMODE = permissionConfig.getConfig().getString("earth-godmode"),
            PERMISSION_CLEAR = permissionConfig.getConfig().getString("earth-clear"),
            PERMISSION_ENDERCHEST_EDIT = permissionConfig.getConfig().getString("earth-enderchest-edit"),
            PERMISSION_SUICIDE = permissionConfig.getConfig().getString("earth-suicide"),
            PERMISSION_INVSEE_EDIT = permissionConfig.getConfig().getString("earth-invsee-edit"),
            PERMISSION_HAT = permissionConfig.getConfig().getString("earth-hat"),
            PERMISSION_FEED = permissionConfig.getConfig().getString("earth-feed"),
            PERMISSION_HEAL = permissionConfig.getConfig().getString("earth-heal");

    //Features

    public static final String PERMISSION_COLOR_CHAT = permissionConfig.getConfig().getString("earth-color-chat"),
            PERMISSION_COLOR_SIGN = permissionConfig.getConfig().getString("earth-color-sign");

}
