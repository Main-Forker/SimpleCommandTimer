package net.vertrauterdavid.timer.utils;

import net.vertrauterdavid.timer.SimpleCommandTimer;
import net.vertrauterdavid.timer.hook.PlaceholderAPIHook;
import org.bukkit.Bukkit;

public class MessageUtils {

    public static void NoneExsistingDepend(String message) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook(SimpleCommandTimer.getInstance()).register();
        } else {
            Bukkit.getConsoleSender().sendRichMessage(message);
            Bukkit.getPluginManager().disablePlugin(SimpleCommandTimer.getInstance());
        }

    }

}
