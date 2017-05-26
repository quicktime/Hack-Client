package at.tiam.bolt.event.oldevent;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventRender3D extends EventOld {

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
