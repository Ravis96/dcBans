package pl.dreamcode.dcbans.commands;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.dreamcode.dcbans.Main;
import pl.dreamcode.dcbans.commands.admin.*;

import java.lang.reflect.Field;

public class CommandMap {
    private SimpleCommandMap scm;
    private SimplePluginManager spm;

    public CommandMap() {
        setupSimpleCommandMap();
        registerCommands(new BanreloadCMD());
        registerCommands(new CheckbanCMD());
        registerCommands(new CheckwarnCMD());
        registerCommands(new BanCMD());
        registerCommands(new TempbanCMD());
        registerCommands(new MuteCMD());
        registerCommands(new TempmuteCMD());
        registerCommands(new KickCMD());
        registerCommands(new WarnCMD());
        registerCommands(new UnbanCMD());
        registerCommands(new UnmuteCMD());
    }

    private void registerCommands(CCommand command) {
        scm.register(Main.getPlugin().getDescription().getName(), command);
    }

    private void setupSimpleCommandMap() {
        spm = (SimplePluginManager) Main.getPlugin().getServer().getPluginManager();
        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        try {
            scm = (SimpleCommandMap) f.get(spm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
