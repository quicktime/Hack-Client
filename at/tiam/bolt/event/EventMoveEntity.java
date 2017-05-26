package at.tiam.bolt.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventMoveEntity extends EventCancellable{
	
	public double x, y, z;
	public byte type;
	
	public EventMoveEntity(byte type, double x, double y, double z){
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
}
