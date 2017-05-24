package at.tiam.bolt.event.events;

import at.tiam.bolt.event.Event;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventRender3D extends Event {

    public float particleTicks;

    public EventRender3D(float particleTicks) {
        this.particleTicks = particleTicks;
    }

    public float getParticleTicks() {
        return particleTicks;
    }

    public void setParticleTicks(float particleTicks) {
        this.particleTicks = particleTicks;
    }
}
