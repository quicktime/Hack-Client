package at.tiam.bolt.event;

import net.minecraft.entity.Entity;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventInteractWithEntity extends EventCancellable{
	
	public Entity entityIn;
	
	public EventInteractWithEntity(Entity entityIn) {
		super();
		this.entityIn = entityIn;
	}
}
