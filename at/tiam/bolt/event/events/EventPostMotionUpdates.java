package at.tiam.bolt.event.events;

import at.tiam.bolt.event.EventOld;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventPostMotionUpdates extends EventOld {

    public float yaw, pitch;

    public boolean onGround;

    public double y;

    public EventPostMotionUpdates(float yaw, float pitch, boolean onGround, double y) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.y = y;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
