package pl.dreamcode.dcbans.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.dreamcode.dcbans.Main;

import java.io.File;
import java.io.IOException;

@Getter
public class ConfigManager {
    private Config config;

    public ConfigManager() throws IOException {
        loadConfig();
    }

    public void loadConfig() throws IOException {
        File f = new File(Main.getPlugin().getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.addDefault("message.reloaded", "&aPrzeloadowano config ban'ow.");
        cfg.addDefault("message.error", "&cWystapil problem, sprawdz config czy wszystko jest poprawne.");
        cfg.addDefault("message.usage", "&cPoprawne uzycie: &c%USAGE%");
        cfg.addDefault("message.permission", "&cNie posiadasz permisji. &8(&7%PERMISSION%&8)");
        cfg.addDefault("message.noPlayer", "&cPodanego gracza nie znaleziono.");
        cfg.addDefault("message.timeFormat", "&cPodana ilosc musi byc liczba z jednostka. (np.: 1s)");
        cfg.addDefault("message.playerIsMe", "&cNie uzywaj tego na sobie.");
        cfg.addDefault("message.checkban.head", "&8-----[ &cCheckBan &8]-----\n" +
                "&8* &7Nick: &f%NICK%");
        cfg.addDefault("message.checkban.example", "&8* &7%BAN_TYPE%:\n" +
                "&8- &7Data: &f%DATE%\n" +
                "&8- &7Admin: &f%ADMIN%\n" +
                "&8- &7Powod: &f%REASON%");
        cfg.addDefault("message.checkban.temp_example", "&8* &7%BAN_TYPE%:\n" +
                "&8- &7Data: &f%DATE%\n" +
                "&8- &7Admin: &f%ADMIN%\n" +
                "&8- &7Powod: &f%REASON%\n" +
                "&8- &7Okres: &f%TIME%\n" +
                "&8- &7Koniec za: &f%END_TIME%");
        cfg.addDefault("message.checkban.noBans", "&8* &cBrak nalozonych kar.");
        cfg.addDefault("boolean.checkBan", true);
        cfg.addDefault("boolean.bcBans", true);
        cfg.addDefault("boolean.bcAdmin", true);
        cfg.addDefault("boolean.banSelf", false);
        cfg.addDefault("message.hasBan", "&cPodany gracz posiada juz bana.");
        cfg.addDefault("message.noBan", "&cPodany gracz nie posiada kary.");
        cfg.addDefault("message.unban", "&aOdbanowales gracza!");
        cfg.addDefault("message.unmute", "&aOdciszyles gracza!");
        cfg.addDefault("message.ban", "&aZbanowales gracza!");
        cfg.addDefault("message.mute", "&aWyciszyles gracza!");
        cfg.addDefault("message.kick", "&aWyrzuciles gracza!");
        cfg.addDefault("message.warn", "&aUkarales gracza!");
        cfg.addDefault("broadcast.ban", "&8[&4BAN&8] &7Gracz %NICK% zostal &czbanowany &7przez &9%ADMIN%.");
        cfg.addDefault("broadcast.tempban", "&8[&4BAN&8] &7Gracz %NICK% zostal &czbanowany &7przez &9%ADMIN%&7, na okres: &f%TIME%.");
        cfg.addDefault("broadcast.kick", "&8[&4BAN&8] &7Gracz %NICK% zostal &cwyrzucony &7przez &9%ADMIN%.");
        cfg.addDefault("broadcast.mute", "&8[&4BAN&8] &7Gracz %NICK% zostal &cwyciszony &7przez &9%ADMIN%.");
        cfg.addDefault("broadcast.tempmute", "&8[&4BAN&8] &7Gracz %NICK% zostal &cwyciszony &7przez &9%ADMIN%&7, na okres: &f%TIME%.");
        cfg.addDefault("broadcast.warn", "&8[&4BAN&8] &7Gracz %NICK% zostal &costrzezony &7przez &9%ADMIN%.");
        cfg.addDefault("broadcast.unban", "&8[&4BAN&8] &7Gracz %NICK% zostal &codbanowany &7przez &9%ADMIN%.");
        cfg.addDefault("broadcast.unmute", "&8[&4BAN&8] &7Gracz %NICK% zostal &codciszony &7przez &9%ADMIN%.");
        cfg.addDefault("broadcast.reason", "&4Powod: &7%REASON%");
        cfg.addDefault("value.noReason", "Brak podanego powodu.");
        cfg.addDefault("value.dateFormat", "yyyy-MM-dd_HH:mm:ss");
        cfg.addDefault("kick.ban", "&4Zostales zbanowany!\n" +
                "&cZbanowany przez: %ADMIN%\n" +
                "&7Data: %DATE%\n" +
                "&cPowod: %REASON%\n\n" +
                "&7Zakup unbana w naszym itemshop! :)");
        cfg.addDefault("kick.tempban", "&4Zostales zbanowany!\n" +
                "&cZbanowany przez: %ADMIN%\n" +
                "&7Data: %DATE%\n" +
                "&7Koniec za: &c%BAN_TIME%\n" +
                "&cPowod: &7%REASON%\n\n" +
                "&7Zakup unbana w naszym itemshop! :)");
        cfg.addDefault("kick.kick", "&4Zostales wyrzucony!\n" +
                "&cWyrzucony przez: %ADMIN%\n" +
                "&cData: &7%DATE%\n" +
                "&cPowod: &7%REASON%");
        cfg.addDefault("warn.mute", "&4Zostales wyciszony!\n" +
                "&cMute zostal nadany przez: &7%ADMIN%\n" +
                "&cW dniu: &7%DATE%\n" +
                "&cPowod: &7%REASON%");
        cfg.addDefault("warn.tempmute", "&4Zostales wyciszony!\n" +
                "&cMute zostal nadany przez: &7%ADMIN%\n" +
                "&cW dniu: &7%DATE%\n" +
                "&4Do konca: &7%MUTE_TIME%\n" +
                "&cPowod: &7%REASON%");
        cfg.addDefault("warn.warn", "&4Zostales ostrzezony!\n" +
                "&cNadal: &7%ADMIN%\n" +
                "&cW dniu: &7%DATE%\n" +
                "&cPowod: &7%REASON%");
        cfg.options().copyDefaults(true);
        cfg.save(f);
        this.config = new Config(cfg.getString("message.reloaded"), cfg.getString("message.error"),
                cfg.getString("message.usage"), cfg.getString("message.permission"),
                cfg.getString("message.noPlayer"), cfg.getString("message.timeFormat"), cfg.getString("message.playerIsMe"), cfg.getString("message.hasBan"), cfg.getString("message.noBan"),
                cfg.getString("message.unban"), cfg.getString("message.unmute"), cfg.getString("message.ban"),
                cfg.getString("message.mute"), cfg.getString("message.kick"), cfg.getString("message.warn"),
                cfg.getString("value.noReason"), cfg.getString("value.dateFormat"),
                cfg.getBoolean("boolean.checkBan"), cfg.getBoolean("boolean.bcBans"), cfg.getBoolean("boolean.banSelf"),
                cfg.getBoolean("boolean.bcAdmin"), cfg.getString("message.checkban.head"), cfg.getString("message.checkban.example"),
                cfg.getString("message.checkban.temp_example"), cfg.getString("message.checkban.noBans"),
                cfg.getString("broadcast.ban"), cfg.getString("broadcast.tempban"), cfg.getString("broadcast.kick"),
                cfg.getString("broadcast.mute"), cfg.getString("broadcast.tempmute"), cfg.getString("broadcast.warn"),
                cfg.getString("broadcast.reason"), cfg.getString("broadcast.unban"), cfg.getString("broadcast.unmute"),
                cfg.getString("kick.ban"), cfg.getString("kick.tempban"), cfg.getString("kick.kick"),
                cfg.getString("warn.mute"), cfg.getString("warn.tempmute"), cfg.getString("warn.warn"));
    }
}
