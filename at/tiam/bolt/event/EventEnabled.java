package at.tiam.bolt.event;

import at.tiam.bolt.module.Module;

import com.darkmagician6.eventapi.events.Event;

public class EventEnabled implements Event {
	
	public Module module;
	
	public EventEnabled(Module module) {
		super();
		this.module = module;
	}
	
}
