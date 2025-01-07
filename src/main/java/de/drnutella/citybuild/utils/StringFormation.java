package de.drnutella.citybuild.utils;

import net.md_5.bungee.api.ChatColor;

public class StringFormation {
    public static String formatCashString(int cashAmount) {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(cashAmount));

        for (int i = stringBuilder.length() - 3; i > 0; i -= 3) {
            stringBuilder.insert(i, '.');
        }

        return stringBuilder.toString();
    }
    public static String convertToHEX(String input, String[] color) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String colorCode = color[i % color.length];
            sb.append(ChatColor.of(colorCode) + input.split("")[i]);
        }
        return sb.toString();
    }

    public static String convertToHEXWithExtra(String input, String[] color, String[] extra) {
        StringBuilder sb = new StringBuilder();
        for (int a = 0; a < input.length(); a++) {
            String colorCode = color[a % color.length];

            StringBuilder extraBuilder = new StringBuilder();
            for (String s : extra) {
                extraBuilder.append(s);
            }

            sb.append(ChatColor.of(colorCode) + extraBuilder.toString() + input.split("")[a]);
        }
        return sb.toString();
    }
}
