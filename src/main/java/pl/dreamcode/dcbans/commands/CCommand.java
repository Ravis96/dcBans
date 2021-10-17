package pl.dreamcode.dcbans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import pl.dreamcode.dcbans.Main;

public abstract class CCommand extends Command implements PluginIdentifiableCommand {
    protected CCommand(String name) {
        super(name);
    }

    @Override
    public Plugin getPlugin() {
        return Main.getPlugin();
    }

    public abstract void run(CommandSender sender, String label, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        run(sender, commandLabel, arguments);
        return true;
    }
}
