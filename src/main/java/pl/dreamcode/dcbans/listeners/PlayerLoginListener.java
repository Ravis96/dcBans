package pl.dreamcode.dcbans.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.dreamcode.dcbans.Main;
import pl.dreamcode.dcbans.config.Config;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.user.ban.Ban;
import pl.dreamcode.dcbans.user.ban.BanType;
import pl.dreamcode.dcbans.user.ban.BanUtils;
import pl.dreamcode.dcbans.utils.ChatUtil;
import pl.dreamcode.dcbans.utils.TimeUtil;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        Main plugin = Main.getPlugin();
        Config cfg = plugin.getConfigManager().getConfig();
        if(plugin.getUserManager().get(p) == null) {
            plugin.getUserManager().addUser(User.defaultUser(p));
            return;
        }
        User u = plugin.getUserManager().get(p);
        for(Ban ban : u.getBans()) {
            if(ban.getBanType().equals(BanType.BAN)) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatUtil.fixColors(cfg.getKban())
                        .replace("%ADMIN%", ban.getAdmin())
                        .replace("%DATE%", ban.getDate())
                        .replace("%REASON%", ban.getReason()));
                return;
            }
            if(ban.getBanType().equals(BanType.TEMP_BAN)) {
                if(BanUtils.getTimeBan(u, BanType.TEMP_BAN) > 0) {
                    e.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatUtil.fixColors(cfg.getKtempban())
                            .replace("%ADMIN%", ban.getAdmin())
                            .replace("%DATE%", ban.getDate())
                            .replace("%REASON%", ban.getReason())
                            .replace("%BAN_TIME%", TimeUtil.convertLong(BanUtils.getTimeBan(u, BanType.TEMP_BAN))));
                    return;
                }
            }
        }
    }
}
