package at.tiam.bolt.event;

import com.darkmagician6.eventapi.events.Event;

public class EventKeyPress implements Event{
	
	private int key;
	
	public EventKeyPress(int key) {
		super();
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
