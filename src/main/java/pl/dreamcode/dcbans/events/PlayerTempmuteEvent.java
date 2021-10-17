package pl.dreamcode.dcbans.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.dreamcode.dcbans.user.User;
import pl.dreamcode.dcbans.user.ban.Ban;

@Getter
@RequiredArgsConstructor
public class PlayerTempmuteEvent extends Event implements Cancellable {
    private final HandlerList handlerList = new HandlerList();
    private final User user;
    private final Ban ban;
    private String reason = defaultReason();
    private boolean cancelled;

    public User getUser() {
        return this.user;
    }

    public Ban getBan() {
        return this.ban;
    }

    public void setCancelledReason(String text) {
        this.reason = text;
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

    private String defaultReason() {
        return "&cEvent zostal anulowany!";
    }
}
