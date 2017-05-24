package at.tiam.bolt.event;

import java.lang.reflect.Method;

/**
 * Created by quicktime on 5/22/17.
 */
public class Data {

    public final Object source;

    public final Method target;

    public final byte priority;

    Data(Object source, Method target, byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }

}
