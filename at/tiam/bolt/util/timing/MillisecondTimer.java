package at.tiam.bolt.util.timing;

/**
 * Created by quicktime on 5/25/17.
 */
public abstract class MillisecondTimer {

    private long lastTime;

    public MillisecondTimer() { reset(); }
    public boolean finished() { return elapsedTime() >= getDelay(); }
    public void reset() { lastTime = getCurrentTime(); }
    public boolean output() {
        boolean done = elapsedTime() >= getDelay();

        if (done) {
            lastTime = getCurrentTime();
        }
        return done;
    }
    public long elapsedTime() { return getCurrentTime() - lastTime; }
    public abstract int getDelay();
    public long getCurrentTime() { return System.currentTimeMillis(); }
}
