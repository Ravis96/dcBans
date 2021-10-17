package pl.dreamcode.dcbans.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Config {
    // messages
    private final String reloaded;
    private final String error;
    private final String usage;
    private final String permission;
    private final String noPlayer;
    private final String timeFormat;
    private final String playerIsMe;
    private final String isBanned;
    private final String noBanned;
    private final String unban;
    private final String unmute;
    private final String ban;
    private final String mute;
    private final String kick;
    private final String warn;

    // values
    private final String noReason;
    private final String dateFormat;

    // booleans
    private final boolean checkBan;
    private final boolean bcBans;
    private final boolean banSelf;
    private final boolean bcAdmin;

    // checkban
    private final String checkban;
    private final String cbExample;
    private final String cbTempexample;
    private final String cbNoBans;

    // broadcast
    private final String bcBan;
    private final String bcTempban;
    private final String bcKick;
    private final String bcMute;
    private final String bcTempmute;
    private final String bcWarn;
    private final String bcReason;
    private final String bcUnban;
    private final String bcUnmute;

    // kick's
    private final String kban;
    private final String ktempban;
    private final String kkick;

    //mute
    private final String kmute;
    private final String ktempmute;

    //warn
    private final String kwarn;
}
