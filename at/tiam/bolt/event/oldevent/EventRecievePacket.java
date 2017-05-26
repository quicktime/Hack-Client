package at.tiam.bolt.event.oldevent;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

/**
 * Created by quicktime on 5/26/17.
 */
public class EventRecievePacket extends EventCancellable {

    public Packet packet;

    public EventRecievePacket(Packet packet) {
        super();
        this.packet = packet;
    }
}
