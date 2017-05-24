package at.tiam.bolt.event;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by quicktime on 5/22/17.
 */
public class EventOld {

    /**
     * Main events you may need:
     * <p>
     * Minecraft:
     * - EventKeyboard
     * - EventMiddleClick
     * - EventTick
     * <p>
     * EntityPlayerSP:
     * - EventUpdate
     * - EventPreMotionUpdates
     * - EventPostMotionUpdates
     * <p>
     * GuiIngame:
     * - EventRender2D
     * <p>
     * EntityRenderer:
     * - EventRender3D
     */

    private boolean cancelled;

    public enum State {
        PRE("PRE", 0),

        POST("POST", 1);

        State(final String sting, final int number) {

        }
    }

    public EventOld call() {
        this.cancelled = false;
        call(this);
        return this;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private static final void call(final EventOld eventOld) {
        final ArrayHelper<Data> dataList = EventManagerOld.get(eventOld.getClass());

        if (dataList != null) {
            for (final Data data : dataList) {
                try {
                    data.target.invoke(data.source, eventOld);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}