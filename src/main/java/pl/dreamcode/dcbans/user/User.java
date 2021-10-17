package pl.dreamcode.dcbans.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import pl.dreamcode.dcbans.user.ban.Ban;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class User implements ConfigurationSerializable {
    private String name;
    private List<Ban> bans;

    public User(Map<String, Object> su) {
        setName((String) su.get("name"));
        ArrayList<Map<String, Object>> mapBans = (ArrayList<Map<String, Object>>) su.get("bans");
        ArrayList<Ban> dsBans = new ArrayList<>();
        for(Map<String, Object> sBans : mapBans)
            dsBans.add(new Ban(sBans));
        setBans(dsBans);
    }

    public static User defaultUser(Player p) {
        return new User(p.getName(), new ArrayList<>());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        ArrayList<Map<String, Object>> tempBans = new ArrayList<>();
        for(Ban ban : getBans()) {
            tempBans.add(ban.serialize());
        }
        map.put("bans", tempBans);
        return map;
    }
}
