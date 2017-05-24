package at.tiam.bolt.event.events;

import at.tiam.bolt.event.Event;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventKeyboard extends Event {

    public int key;

    public EventKeyboard(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
