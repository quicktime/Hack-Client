package at.tiam.bolt.event;

import at.tiam.bolt.module.Module;

import com.darkmagician6.eventapi.events.Event;

public class EventDisabled implements Event {
	
	public Module module;
	
	public EventDisabled(Module module) {
		super();
		this.module = module;
	}
}
