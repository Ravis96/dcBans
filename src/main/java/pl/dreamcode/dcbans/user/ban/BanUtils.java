package pl.dreamcode.dcbans.user.ban;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.dreamcode.dcbans.Main;
import pl.dreamcode.dcbans.config.Config;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.utils.ChatUtil;
import pl.dreamcode.dcbans.utils.TimeUtil;

@NoArgsConstructor
public final class BanUtils {
    public static boolean isBan(Player p) {
        User u = Main.getPlugin().getUserManager().get(p);
        if(u != null) {
            for(Ban ban : u.getBans()) {
                if(ban.getBanType().equals(BanType.BAN)) {
                    return true;
                }
                if(ban.getBanType().equals(BanType.TEMP_BAN)) {
                    long secondsLeft = (ban.getStart() / 1000) + ban.getEnd() - (System.currentTimeMillis() / 1000);
                    return secondsLeft > 0;
                }
            }
        }
        return false;
    }

    public static boolean isBan(User u) {
        for(Ban ban : u.getBans()) {
            if(ban.getBanType().equals(BanType.BAN)) {
                return true;
            }
            if(ban.getBanType().equals(BanType.TEMP_BAN)) {
                long secondsLeft = (ban.getStart() / 1000) + ban.getEnd() - (System.currentTimeMillis() / 1000);
                return secondsLeft > 0;
            }
        }
        return false;
    }

    public static boolean isMute(Player p) {
        User u = Main.getPlugin().getUserManager().get(p);
        if(u != null) {
            for(Ban ban : u.getBans()) {
                if(ban.getBanType().equals(BanType.MUTE)) {
                    return true;
                }
                if(ban.getBanType().equals(BanType.TEMP_MUTE)) {
                    long secondsLeft = (ban.getStart() / 1000) + ban.getEnd() - (System.currentTimeMillis() / 1000);
                    return secondsLeft > 0;
                }
            }
        }
        return false;
    }

    public static boolean isMute(User u) {
        for(Ban ban : u.getBans()) {
            if(ban.getBanType().equals(BanType.MUTE)) {
                return true;
            }
            if(ban.getBanType().equals(BanType.TEMP_MUTE)) {
                long secondsLeft = (ban.getStart() / 1000) + ban.getEnd() - (System.currentTimeMillis() / 1000);
                return secondsLeft > 0;
            }
        }
        return false;
    }

    public static long getTimeBan(User u, BanType type) {
        for(Ban ban : u.getBans()) {
            if(ban.getBanType().equals(type)) {
                return (ban.getStart() / 1000) + ban.getEnd() - (System.currentTimeMillis() / 1000);
            }
        }
        return 0;
    }

    public static void addWarn(Player p, Ban ban) {
        User u = Main.getPlugin().getUserManager().get(p);
        if(u != null) {
            u.getBans().add(ban);
        }
    }

    public static void addWarn(User u, Ban ban) {
        u.getBans().add(ban);
    }

    public static void addBan(Player p, Ban ban) {
        User u = Main.getPlugin().getUserManager().get(p);
        if(u != null) {
            u.getBans().add(ban);
        }
    }

    public static void addBan(User u, Ban ban) {
        removeBan(u, ban.getBanType());
        u.getBans().add(ban);
    }

    public static void addBanAndKick(Player p, Ban ban) {
        User u = Main.getPlugin().getUserManager().get(p);
        if(u != null) {
            removeBan(u, ban.getBanType());
            u.getBans().add(ban);
            Config cfg = Main.getPlugin().getConfigManager().getConfig();
            if(ban.getBanType().equals(BanType.BAN)) {
                p.kickPlayer(ChatUtil.fixColors(cfg.getKban())
                        .replace("%ADMIN%", ban.getAdmin())
                        .replace("%DATE%", ban.getDate())
                        .replace("%REASON%", ban.getReason()));
            }
            if(ban.getBanType().equals(BanType.TEMP_BAN)) {
                p.kickPlayer(ChatUtil.fixColors(cfg.getKtempban())
                        .replace("%ADMIN%", ban.getAdmin())
                        .replace("%DATE%", ban.getDate())
                        .replace("%REASON%", ban.getReason())
                        .replace("%BAN_TIME%", TimeUtil.convertLong(BanUtils.getTimeBan(u, BanType.TEMP_BAN))));
            }
            if(ban.getBanType().equals(BanType.KICK)) {
                p.kickPlayer(ChatUtil.fixColors(cfg.getKkick())
                        .replace("%ADMIN%", ban.getAdmin())
                        .replace("%DATE%", ban.getDate())
                        .replace("%REASON%", ban.getReason()));
            }
        }
    }

    public static void removeBan(Player p, BanType type) {
        User u = Main.getPlugin().getUserManager().get(p);
        if(u != null) {
            u.getBans().removeIf(ban -> ban.getBanType().equals(type));
        }
    }

    public static void removeBan(User u, BanType type) {
        u.getBans().removeIf(ban -> ban.getBanType().equals(type));
    }
}
