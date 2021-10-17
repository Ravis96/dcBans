package pl.dreamcode.dcbans.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.dreamcode.dcbans.Main;
import pl.dreamcode.dcbans.commands.CCommand;
import pl.dreamcode.dcbans.config.Config;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.user.ban.Ban;
import pl.dreamcode.dcbans.user.ban.BanType;
import pl.dreamcode.dcbans.user.ban.BanUtils;
import pl.dreamcode.dcbans.utils.ChatUtil;
import pl.dreamcode.dcbans.utils.TimeUtil;

public class CheckbanCMD extends CCommand {
    public CheckbanCMD() {
        super("checkban");
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
        if(args.length == 1) {
            User u = plugin.getUserManager().get(args[0]);
            if(u == null) {
                sender.sendMessage(ChatUtil.fixColors(config.getNoPlayer()));
                return;
            }
            sender.sendMessage(ChatUtil.fixColors(config.getCheckban())
                    .replace("%NICK%", u.getName()));
            boolean b = false;
            for(Ban ban : u.getBans()) {
                if(ban.getBanType().equals(BanType.BAN)) {
                    b = true;
                    sender.sendMessage(ChatUtil.fixColors(config.getCbExample())
                            .replace("%BAN_TYPE%", ban.getBanType().toString())
                            .replace("%DATE%", ban.getDate())
                            .replace("%ADMIN%", ban.getAdmin())
                            .replace("%REASON%", ban.getReason()));
                }
                if(ban.getBanType().equals(BanType.TEMP_BAN)) {
                    b = true;
                    sender.sendMessage(ChatUtil.fixColors(config.getCbTempexample())
                            .replace("%BAN_TYPE%", ban.getBanType().toString())
                            .replace("%DATE%", ban.getDate())
                            .replace("%ADMIN%", ban.getAdmin())
                            .replace("%REASON%", ban.getReason())
                            .replace("%TIME%", TimeUtil.convertLong(ban.getEnd()))
                            .replace("%END_TIME%", BanUtils.getTimeBan(u, ban.getBanType()) < 0 ?
                                    ChatUtil.fixColors("&cWygaslo.") : TimeUtil.convertLong(BanUtils.getTimeBan(u, ban.getBanType()))));
                }
                if(ban.getBanType().equals(BanType.MUTE)) {
                    b = true;
                    sender.sendMessage(ChatUtil.fixColors(config.getCbExample())
                            .replace("%BAN_TYPE%", ban.getBanType().toString())
                            .replace("%DATE%", ban.getDate())
                            .replace("%ADMIN%", ban.getAdmin())
                            .replace("%REASON%", ban.getReason()));
                }
                if(ban.getBanType().equals(BanType.TEMP_MUTE)) {
                    b = true;
                    sender.sendMessage(ChatUtil.fixColors(config.getCbTempexample())
                            .replace("%BAN_TYPE%", ban.getBanType().toString())
                            .replace("%DATE%", ban.getDate())
                            .replace("%ADMIN%", ban.getAdmin())
                            .replace("%REASON%", ban.getReason())
                            .replace("%TIME%", TimeUtil.convertLong(ban.getEnd()))
                            .replace("%END_TIME%", BanUtils.getTimeBan(u, ban.getBanType()) < 0 ?
                                    ChatUtil.fixColors("&cWygaslo.") : TimeUtil.convertLong(BanUtils.getTimeBan(u, ban.getBanType()))));
                }
            }
            if(!b) {
                sender.sendMessage(ChatUtil.fixColors(config.getCbNoBans()));
            }
            return;
        }
        sender.sendMessage(ChatUtil.fixColors(config.getUsage())
                .replace("%USAGE%", "/checkban [nick]"));
    }
}
