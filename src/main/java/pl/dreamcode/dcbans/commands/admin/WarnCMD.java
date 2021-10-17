package pl.dreamcode.dcbans.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dreamcode.dcbans.Main;
import pl.dreamcode.dcbans.commands.CCommand;
import pl.dreamcode.dcbans.config.Config;
import pl.dreamcode.dcbans.events.PlayerBanEvent;
import pl.dreamcode.dcbans.events.PlayerWarnEvent;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.user.ban.Ban;
import pl.dreamcode.dcbans.user.ban.BanType;
import pl.dreamcode.dcbans.user.ban.BanUtils;
import pl.dreamcode.dcbans.utils.ChatUtil;
import pl.dreamcode.dcbans.utils.DateUtil;

public class WarnCMD extends CCommand {
    public WarnCMD() {
        super("warn");
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
        if(args.length == 0) {
            sender.sendMessage(ChatUtil.fixColors(config.getUsage())
                    .replace("%USAGE%", "/warn [nick] [powod]"));
            return;
        }
        User u = plugin.getUserManager().get(args[0]);
        if(u == null) {
            sender.sendMessage(ChatUtil.fixColors(config.getNoPlayer()));
            return;
        }
        if(!config.isBanSelf()) {
            if (u.getName().equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(ChatUtil.fixColors(config.getPlayerIsMe()));
                return;
            }
        }
        Ban ban = new Ban(BanType.WARN, sender.getName(), config.getNoReason(), DateUtil.getDate(config.getDateFormat()), 0, 0);
        if(args.length != 1) {
            StringBuilder msg = new StringBuilder();
            for (int ia = 1; ia < args.length; ia++)
                msg.append(args[ia]).append(" ");
            String reason = msg.toString();
            ban.setReason(reason);
        }
        PlayerWarnEvent event = new PlayerWarnEvent(u, ban);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            sender.sendMessage(ChatUtil.fixColors(event.getReason()));
            return;
        }
        BanUtils.addWarn(u, ban);
        if(config.isBcBans()) {
            ChatUtil.broadcastMessage(ChatUtil.fixColors(config.getBcWarn())
                    .replace("%NICK%", u.getName()).replace("%ADMIN%", sender.getName()));
            ChatUtil.broadcastMessage(ChatUtil.fixColors(config.getBcReason())
                    .replace("%REASON%", ChatUtil.fixColors(ban.getReason())));
        }
        if(config.isBcAdmin()) sender.sendMessage(ChatUtil.fixColors(config.getWarn()));
    }
}
