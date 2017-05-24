package at.tiam.bolt.event.events;

import at.tiam.bolt.event.EventOld;
import com.darkmagician6.eventapi.events.Event;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventKeyboard implements Event {

    public int key;

    public EventKeyboard(int key) {
        super();
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) { this.key = key; }

}
