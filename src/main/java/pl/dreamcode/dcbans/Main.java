package pl.dreamcode.dcbans;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dreamcode.dcbans.commands.CommandMap;
import pl.dreamcode.dcbans.config.ConfigManager;
import pl.dreamcode.dcbans.listeners.onPlayerChat;
import pl.dreamcode.dcbans.listeners.onPlayerLogin;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.user.UserManager;
import pl.dreamcode.dcbans.user.ban.Ban;

@Getter
@Setter
public final class Main extends JavaPlugin {
    @Getter
    private static Main plugin;
    private ConfigManager configManager;
    private UserManager userManager;

    @Override
    public void onEnable() {
        System.out.printf("[%s] Aktywna wersja: v%s - Autor: %s%n", getDescription().getName(), getDescription().getVersion(), getDescription().getAuthors());
        System.out.printf("[%s] Uruchamiam wstepne aplikacje plugin'u...%n", getDescription().getName());
        plugin = this;
        ConfigurationSerialization.registerClass(User.class);
        ConfigurationSerialization.registerClass(Ban.class);
        try {
            setConfigManager(new ConfigManager());
            setUserManager(new UserManager());
            registerListeners();
            new CommandMap();
        } catch (Exception e) {
            shutdown("Wykryto problem z zaladowaniem danych.");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if(getUserManager() != null) {
            getUserManager().saveUsers();
        }
    }

    public void shutdown(String reason) {
        System.out.printf("[%s] Plugin zostal wylaczony przez problem...%n", getDescription().getName());
        System.out.printf("[%s] Powod: %s%n", getDescription().getName(), reason);
        this.getServer().getPluginManager().disablePlugin(this);
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new onPlayerLogin(), this);
        pm.registerEvents(new onPlayerChat(), this);
    }
}
