package at.tiam.bolt.event.oldevent;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventPreMotionUpdates extends EventOld {

    private boolean cancel;

    public float yaw, pitch;

    public double y;

    public EventPreMotionUpdates(float yaw, float pitch, double y) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
