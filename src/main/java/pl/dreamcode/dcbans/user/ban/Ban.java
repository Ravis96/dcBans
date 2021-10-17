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
@Setter
@AllArgsConstructor
public class Ban implements ConfigurationSerializable {
    private BanType banType;
    private String admin;
    private String reason;
    private String date;
    private long start;
    private int end;

    public Ban(Map<String, Object> sb) {
        setBanType(BanType.valueOf((String) sb.get("banType")));
        setAdmin((String) sb.get("admin"));
        setReason((String) sb.get("reason"));
        setDate((String) sb.get("date"));
        setStart(Long.parseLong((String) sb.get("start")));
        setEnd((Integer) sb.get("end"));
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
