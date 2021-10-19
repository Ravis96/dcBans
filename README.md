# dcBans🛡️
Plugin na bany Spigot 1.13+, napisany dla [DreamCode](https://discord.gg/G8aFUSyfFh), z full-config.

## Download: [1.0](https://github.com/Ravis96/dcBans/releases/tag/1.0)

### Informacje:
- Permisje wg. schematu: (rpl.(komenda))
- Każda wiadomosc oraz ustawienia można zmienic w config.yml
- Parametry do wiadomosci sa opisane w config.yml (przyklady)
- Pomoc znajdziesz na naszym discord.
### Komendy: (powód jest opcjonalny)
- /banreload - Przeladowywuje config.
- /checkban [nick] - Wyświetla status banów.
- /checkwarn [nick] - Wyświetla liste ostrzezen.
- /ban [nick] [powod] - Banuje gracza.
- /tempban [nick] [czas(np. 1m)] [powod] - Banuje tymczasowo gracza.
- /kick [nick] [powod] - Wyrzuca gracza.
- /mute [nick] [powod] - Wycisza gracza.
- /tempmute [nick] [czas(np. 1m)] [powod] - Wycisza tymczasowo gracza.
- /warn [nick] [powod] - Ostrzega gracza.
- /unban [nick] [powod] - Usuwa nadanego bana graczowi.
- /unmute [nick] [powod] - usuwa nadane wyciszenie graczowi.

### Uzycie przykładowe event'ow:
```java
public class PlayerBanListener implements Listener {

    @EventHandler
    public void onBan(PlayerBanEvent e) {
        User u = e.getUser();
        if(u.getName().equalsIgnoreCase("Ravis96")) {
            e.setCancelled(true);
            e.setCancelledReason(ChatUtil.fixColors("&cNie mozesz tego zrobic, bo bedziesz zalowal!"));
            return;
        }
        CommandSender admin = e.getAdmin();
        if(admin instanceof Player) {
            Player p = (Player) admin;
            if (p.getHealth() != 20.0D) {
                e.setCancelled(true);
                e.setCancelledReason(ChatUtil.fixColors("&cAby to zrobic, musisz miec pelne zycie!"));
                return;
            }
        }
        e.setCancelled(true);
        e.setCancelledReason(ChatUtil.fixColors("&cAby to zrobic, musisz byc graczem!"));
    }
}
```
### Wywolanie bana z innego miejsca niż komenda:
```java
if(BanUtils.isBan(p)) {
    return "Gracz posiada bana.";
}
if(BanUtils.isMute(p)) {
    return "Gracz jest wyciszony.";
}
# Jezeli chcesz uzyc perm-bana, start i end ustaw na 0.
# W przypadku temp-ban, start ustaw na System.currentTimeMillis(), a end na sekundy bana. (60s - 1min)
# Formatowanie daty znajdziesz w DateUtil.getDate(twoj format), przyklad masz w configu.
BanUtils.addBanAndKick(p, new Ban(BanType.BAN, sender.getName(), "Bez powodu", DateUtil.getDate(config.getDateFormat()), 0, 0);
```

## Plugin nie jest doskonały, lecz może komuś sie przyda. 🤭

ps. Jest lekki lag, ze wzgledu na lagującą funkcje kickPlayer.
Poprawić to można w silniku.
