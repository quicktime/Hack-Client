package at.tiam.bolt.event.events;

import at.tiam.bolt.event.Event;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventRender2D extends Event {

    public int width, height;

    public EventRender2D(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
