package pl.dreamcode.dcbans.user.ban;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Ban implements ConfigurationSerializable {
    private final BanType banType;
    private final String admin;
    @Setter
    private String reason;
    private final String date;
    private final long start;
    private final int end;

    public Ban(Map<String, Object> sb) {
        this.banType = BanType.valueOf((String) sb.get("banType"));
        this.admin = (String) sb.get("admin");
        this.reason = (String) sb.get("reason");
        this.date = (String) sb.get("date");
        this.start = Long.parseLong((String) sb.get("start"));
        this.end = (Integer) sb.get("end");
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("banType", getBanType().toString());
        map.put("admin", getAdmin());
        map.put("reason", getReason());
        map.put("date", getDate());
        map.put("start", String.valueOf(getStart()));
        map.put("end", getEnd());
        return map;
    }
}
