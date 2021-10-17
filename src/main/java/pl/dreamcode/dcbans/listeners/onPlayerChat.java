package pl.dreamcode.dcbans.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.dreamcode.dcbans.Main;
import pl.dreamcode.dcbans.config.Config;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.user.ban.Ban;
import pl.dreamcode.dcbans.user.ban.BanType;
import pl.dreamcode.dcbans.user.ban.BanUtils;
import pl.dreamcode.dcbans.utils.ChatUtil;
import pl.dreamcode.dcbans.utils.TimeUtil;

public class onPlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Main plugin = Main.getPlugin();
        Config cfg = plugin.getConfigManager().getConfig();
        if(plugin.getUserManager().get(p) == null) {
            plugin.getUserManager().addUser(User.defaultUser(p));
            return;
        }
        User u = plugin.getUserManager().get(p);
        for(Ban ban : u.getBans()) {
            if(ban.getBanType().equals(BanType.MUTE)) {
                e.setCancelled(true);
                p.sendMessage(ChatUtil.fixColors(cfg.getKmute())
                        .replace("%ADMIN%", ban.getAdmin())
                        .replace("%DATE%", ban.getDate())
                        .replace("%REASON%", ban.getReason()));
                return;
            }
            if(ban.getBanType().equals(BanType.TEMP_MUTE)) {
                if(BanUtils.getTimeBan(u, BanType.TEMP_MUTE) > 0) {
                    e.setCancelled(true);
                    p.sendMessage(ChatUtil.fixColors(cfg.getKtempmute())
                            .replace("%ADMIN%", ban.getAdmin())
                            .replace("%DATE%", ban.getDate())
                            .replace("%REASON%", ban.getReason())
                            .replace("%MUTE_TIME%", TimeUtil.convertLong(BanUtils.getTimeBan(u, BanType.TEMP_MUTE))));
                    return;
                }
            }
        }
    }
}
