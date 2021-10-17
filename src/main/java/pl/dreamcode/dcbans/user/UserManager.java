package pl.dreamcode.dcbans.user;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.dreamcode.dcbans.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.*;

public class UserManager {
    @Setter
    @Getter
    private ArrayList<User> users = new ArrayList<>();

    public UserManager() {
        loadUsers();
    }

    public User get(String name) {
        for(int i = 0; i < getUsers().size(); i++) {
            User u = getUsers().get(i);
            if(u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    public User get(Player p) {
        for(int i = 0; i < getUsers().size(); i++) {
            User u = getUsers().get(i);
            if(u.getName().equals(p.getName())) {
                return u;
            }
        }
        return null;
    }

    public void addUser(User user) {
        getUsers().add(user);
    }

    public void loadUsers() {
        try {
            File folder = new File(Main.getPlugin().getDataFolder(), "users/");
            File[] listOfFiles = folder.listFiles();
            if(listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
                        if(!data.contains("user")) {
                            System.out.printf("[%s] Gracz " + file.getName() + " posiada blad w danych.%n", Main.getPlugin().getDescription().getName());
                            return;
                        }
                        User user = (User) data.get("user");
                        addUser(user);
                    }
                }
            }
            System.out.printf("[%s] Zaladowano %s graczy!%n", Main.getPlugin().getDescription().getName(), getUsers().size());
        } catch (ReadOnlyFileSystemException | IllegalArgumentException e) {
            System.out.printf("[%s] Nie mozna zaladowac danych.%n", Main.getPlugin().getDescription().getName());
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        for(int i = 0; i < getUsers().size(); i++) {
            User u = getUsers().get(i);
            try {
                File f = new File(Main.getPlugin().getDataFolder(), "users/" + u.getName() + ".yml");
                YamlConfiguration data = YamlConfiguration.loadConfiguration(f);
                try {
                    data.set("user", u);
                    data.save(f);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                System.out.printf("[%s] Gracza %s nie mozna zapisac poprawnie.%n", Main.getPlugin().getDescription().getName(), u.getName());
                e.printStackTrace();
            }
        }
        System.out.printf("[%s] Zapisano poprawnie graczy.%n", Main.getPlugin().getDescription().getName());
    }
}
