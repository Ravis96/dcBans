package pl.dreamcode.dcbans.commands.admin;

import org.bukkit.Bukkit;
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
import pl.dreamcode.dcbans.utils.DateUtil;
import pl.dreamcode.dcbans.utils.TimeUtil;

public class TempbanCMD extends CCommand {
    public TempbanCMD() {
        super("tempban");
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
        if (args.length < 2) {
            sender.sendMessage(ChatUtil.fixColors(config.getUsage())
                    .replace("%USAGE%", "/tempban [nick] [czas] [powod]"));
            return;
        }
        User u = plugin.getUserManager().get(args[0]);
        if (u == null) {
            sender.sendMessage(ChatUtil.fixColors(config.getNoPlayer()));
            return;
        }
        if (!config.isBanSelf()) {
            if (u.getName().equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(ChatUtil.fixColors(config.getPlayerIsMe()));
                return;
            }
        }
        if (config.isCheckBan()) {
            if (BanUtils.isBan(u)) {
                sender.sendMessage(ChatUtil.fixColors(config.getIsBanned()));
                return;
            }
        }
        try {
            String s = String.valueOf(args[1].charAt(args[1].length() - 1));
            if(!s.equals("d") && !s.equals("h") && !s.equals("m") && !s.equals("s")) {
                sender.sendMessage(ChatUtil.fixColors(config.getTimeFormat()));
                return;
            }
            String t = args[1].replace(s, "");
            int i = Integer.parseInt(t);
            int sec = 0;
            if(s.equalsIgnoreCase("d")) {
                sec = i * 24 * 60 * 60;
            }
            if(s.equalsIgnoreCase("h")) {
                sec = i * 60 * 60;
            }
            if(s.equalsIgnoreCase("m")) {
                sec = i * 60;
            }
            if(s.equalsIgnoreCase("s")) {
                sec = i;
            }
            if(config.isBcBans()) {
                ChatUtil.broadcastMessage(ChatUtil.fixColors(config.getBcTempban())
                        .replace("%NICK%", u.getName()).replace("%ADMIN%", sender.getName())
                        .replace("%TIME%", TimeUtil.convertLong(sec)));
            }
            if(args.length == 2) {
                Ban ban = new Ban(BanType.TEMP_BAN, sender.getName(), config.getNoReason(), DateUtil.getDate(config.getDateFormat()), System.currentTimeMillis(), sec);
                Player cst = Bukkit.getPlayerExact(u.getName());
                if(cst != null) {
                    BanUtils.addBanAndKick(cst, ban);
                } else {
                    BanUtils.addBan(u, ban);
                }
            } else {
                StringBuilder msg = new StringBuilder();
                for (int ia = 2; ia < args.length; ia++)
                    msg.append(args[ia]).append(" ");
                if(config.isBcBans()) {
                    ChatUtil.broadcastMessage(ChatUtil.fixColors(config.getBcReason())
                            .replace("%REASON%", ChatUtil.fixColors(msg.toString())));
                }
                Ban ban = new Ban(BanType.TEMP_BAN, sender.getName(), ChatUtil.fixColors(msg.toString()), DateUtil.getDate(config.getDateFormat()), System.currentTimeMillis(), sec);
                Player cst = Bukkit.getPlayerExact(u.getName());
                if (cst != null) {
                    BanUtils.addBanAndKick(cst, ban);
                } else {
                    BanUtils.addBan(u, ban);
                }
            }
            if(config.isBcAdmin()) sender.sendMessage(ChatUtil.fixColors(config.getBan()));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatUtil.fixColors(config.getTimeFormat()));
        }
    }
}
