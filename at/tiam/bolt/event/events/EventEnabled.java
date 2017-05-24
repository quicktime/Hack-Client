package at.tiam.bolt.event.events;

import at.tiam.bolt.module.Module;

import com.darkmagician6.eventapi.events.Event;

/**
 * Created by quicktime on 5/24/17.
 */
public class EventEnabled implements Event {

    public Module module;

    public EventEnabled(Module module) {
        super();
        this.module = module;
    }
}
