package at.tiam.bolt.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventMotionUpdate extends EventCancellable{
	
	public byte type;
	
	public EventMotionUpdate(byte type){
		this.type = type;
	}
}
