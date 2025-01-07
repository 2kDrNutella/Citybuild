package de.drnutella.citybuild.utils;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentUtils {

    private TextComponent base;

    public TextComponentUtils(String texte) {
        this.base = new TextComponent(texte);
    }

    public TextComponentUtils setColor(ChatColor color) {
        base.setColor(color);
        return this;
    }

    public TextComponentUtils setUrl(String url) {
        base.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }

    public TextComponentUtils setSuggestCommand(String cmd) {
        base.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd));
        return this;
    }

    public TextComponentUtils setRunCommand(String cmd) {
        base.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
        return this;
    }

    public TextComponentUtils setTexteHover(String texte) {
        base.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(texte).create()));
        return this;
    }

    public TextComponentUtils setBold(Boolean bol) {
        base.setBold(bol);
        return this;
    }


    public TextComponent toTexte() {
        return base;
    }


}
