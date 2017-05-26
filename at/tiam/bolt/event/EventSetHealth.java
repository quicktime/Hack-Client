package at.tiam.bolt.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventSetHealth extends EventCancellable{

	public float health;
	
	public EventSetHealth(float health) {
		super();
		this.health = health;
	}
}
