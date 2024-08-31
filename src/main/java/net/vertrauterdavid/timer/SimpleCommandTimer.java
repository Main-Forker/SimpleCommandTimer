package net.vertrauterdavid.timer;

import lombok.Getter;
import net.vertrauterdavid.timer.timer.Timer;
import net.vertrauterdavid.timer.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

@Getter
public class SimpleCommandTimer extends JavaPlugin {
    private final HashMap<String, Timer> timers = new HashMap<>();
    @Getter
    private static SimpleCommandTimer instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            saveDefaultConfig();
            loadTimers();
            loadStartup();
        });
        MessageUtils.NoneExsistingDepend("<red>SimpleCommandTimer has been disabled because PlaceholderAPI is not installed.");
    }

    private void loadTimers() {
        Objects.requireNonNull(getConfig().getConfigurationSection("Timers")).getKeys(false).forEach(identifier -> {
            String s = getConfig().getString("Timers." + identifier);
            try {
                assert s != null;
                long seconds = Long.parseLong(s.split(";")[0]);
                timers.put(identifier, new Timer(this, seconds, s.replaceAll(seconds + ";", "")));
            } catch (NumberFormatException ignored) { }
        });
    }

    private void loadStartup() {
        Objects.requireNonNull(getConfig().getConfigurationSection("Startup")).getKeys(false).forEach(identifier -> {
            String s = getConfig().getString("Startup." + identifier);
            try {
                assert s != null;
                long seconds = Long.parseLong(s.split(";")[0]);
                Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll(seconds + ";", "")), 20 * seconds);
            } catch (NumberFormatException ignored) { }
        });
    }

}
