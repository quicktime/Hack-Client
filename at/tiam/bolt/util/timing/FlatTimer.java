package at.tiam.bolt.util.timing;

/**
 * Created by quicktime on 5/25/17.
 */
public class FlatTimer extends MillisecondTimer {

    private int delay;

    public FlatTimer() { this.delay = 1000; }
    public FlatTimer(int delay) { this.delay = delay; }

    public MillisecondTimer setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    @Override
    public int getDelay() {
        return delay;
    }
}
