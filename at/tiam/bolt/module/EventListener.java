package at.tiam.bolt.module;

import com.darkmagician6.eventapi.EventManager;

/**
 * Created by quicktime on 5/26/17.
 */
public class EventListener implements IToggleable {

    public EventListener() {

    }

    public void register() {
        EventManager.register(this);
    }

    public void unregister() {
        EventManager.unregister(this);
    }

    @Override
    public boolean isEnabled() { return true; }
}
