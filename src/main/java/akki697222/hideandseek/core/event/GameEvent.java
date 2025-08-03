package akki697222.hideandseek.core.event;

import akki697222.hideandseek.core.Mode;

import javax.annotation.Nonnull;

public interface GameEvent {
    void onEvent();
    @Nonnull
    Mode getMode();
    String getEventName();
    String getEventDesc();
}
