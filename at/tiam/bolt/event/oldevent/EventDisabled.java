package at.tiam.bolt.event.oldevent;

import at.tiam.bolt.module.Module;

import com.darkmagician6.eventapi.events.Event;

/**
 * Created by quicktime on 5/24/17.
 */
public class EventDisabled implements Event {

    public Module module;

    public EventDisabled(Module module) {
        super();
        this.module = module;
    }

}
