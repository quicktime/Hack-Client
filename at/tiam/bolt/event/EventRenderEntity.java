package at.tiam.bolt.event;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity implements Event{
	
	public EntityLivingBase entity;
	public byte type;
	
	public EventRenderEntity(EntityLivingBase entity, byte type) {
		super();
		this.entity = entity;
		this.type = type;
	}
}
