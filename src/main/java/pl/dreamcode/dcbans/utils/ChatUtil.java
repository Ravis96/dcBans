package pl.dreamcode.dcbans.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {
    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    // blad na wyzszych wersjach, lubi wyrzucic null
    public static void broadcastMessage(String text) {
        for(Player po : Bukkit.getOnlinePlayers()) {
            if(po != null) {
                po.sendMessage(text);
            }
        }
    }
}
