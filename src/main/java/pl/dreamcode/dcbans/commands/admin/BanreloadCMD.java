package pl.dreamcode.dcbans.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dreamcode.dcbans.Main;
import pl.dreamcode.dcbans.commands.CCommand;
import pl.dreamcode.dcbans.config.Config;
import pl.dreamcode.dcbans.utils.ChatUtil;

import java.io.IOException;

public class BanreloadCMD extends CCommand {
    public BanreloadCMD() {
        super("banreload");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Main plugin = Main.getPlugin();
        Config config = plugin.getConfigManager().getConfig();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("rpl." + getName())) {
                p.sendMessage(ChatUtil.fixColors(config.getPermission())
                    .replace("%PERMISSION%", "rpl." + getName()));
                return;
            }
        }
        try {
            plugin.getConfigManager().loadConfig();
            sender.sendMessage(ChatUtil.fixColors(config.getReloaded()));
        } catch (IOException e) {
            sender.sendMessage(ChatUtil.fixColors(config.getError()));
            e.printStackTrace();
        }
    }
}
