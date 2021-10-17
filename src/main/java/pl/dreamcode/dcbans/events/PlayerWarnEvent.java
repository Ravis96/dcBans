package pl.dreamcode.dcbans.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.user.ban.Ban;

@Getter
@RequiredArgsConstructor
public class PlayerWarnEvent extends Event implements Cancellable {
    private final HandlerList handlerList = new HandlerList();
    private final CommandSender admin;
    private final User user;
    private final String reason;
    private String cancelReason = defaultMessageCancelled();
    private boolean cancelled;

    public User getUser() {
        return this.user;
    }

    public CommandSender getAdmin() {
        return this.admin;
    }

    public String getReason() {
        return this.reason;
    }

    public void setCancelledReason(String text) {
        this.cancelReason = text;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private String defaultMessageCancelled() {
        return "&cEvent zostal anulowany!";
    }
}
