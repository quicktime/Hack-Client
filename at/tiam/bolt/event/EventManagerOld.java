package at.tiam.bolt.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by quicktime on 5/22/2017
 */
public class EventManagerOld {

    //TODO: Comment EventOld's

    private static final Map<Class<? extends EventOld>, ArrayHelper<Data>> REGISTRY_MAP;

    public static void register(final Object o) {

        for (final Method method : o.getClass().getDeclaredMethods()) {
            if (!isMethodBad(method)) {
                register(method, o);
            }
        }
    }

    public static void register(final Object o, final Class<? extends EventOld> clazz) {

        for (final Method method : o.getClass().getDeclaredMethods()) {
            if (!isMethodBad(method, clazz)) {
                register(method, o);
            }
        }
    }

    private static void register(final Method method, final Object o) {

        final Class<?> clazz = method.getParameterTypes()[0];
        final Data methodData = new Data(o, method, method.getAnnotation(EventTargetOld.class).value());

        if (!methodData.target.isAccessible()) {
            methodData.target.setAccessible(true);
        }

        if (EventManagerOld.REGISTRY_MAP.containsKey(clazz)) {
            if (!EventManagerOld.REGISTRY_MAP.get(clazz).contains(methodData)) {
                EventManagerOld.REGISTRY_MAP.get(clazz).add(methodData);
                sortListValue((Class<? extends EventOld>) clazz);
            }
        } else {
            EventManagerOld.REGISTRY_MAP.put((Class<? extends EventOld>) clazz, new ArrayHelper<Data>() {

                {
                    this.add(methodData);
                }
            });
        }
    }

    public static void unregister(final Object o) {

        for (final ArrayHelper<Data> flexibalArray : EventManagerOld.REGISTRY_MAP.values()) {
            for (final Data methodData : flexibalArray) {
                if (methodData.source.equals(o)) {
                    flexibalArray.remove(methodData);
                }
            }
        }

        cleanMap(true);
    }

    public static void unregister(final Object o, final Class<? extends EventOld> clazz) {

        if (EventManagerOld.REGISTRY_MAP.containsKey(clazz)) {
            for (final Data methodData : EventManagerOld.REGISTRY_MAP.get(clazz)) {
                if (methodData.source.equals(o)) {
                    EventManagerOld.REGISTRY_MAP.get(clazz).remove(methodData);
                }
            }

            cleanMap(true);
        }
    }


    public static void cleanMap(final boolean b) {

        final Iterator<Map.Entry<Class<? extends EventOld>, ArrayHelper<Data>>> iterator = EventManagerOld.REGISTRY_MAP.entrySet().iterator();

        while (iterator.hasNext()) {
            if (!b || iterator.next().getValue().isEmpty()) {
                iterator.remove();
            }
        }
    }

    public static void removeEnty(final Class<? extends EventOld> clazz) {

        final Iterator<Map.Entry<Class<? extends EventOld>, ArrayHelper<Data>>> iterator = EventManagerOld.REGISTRY_MAP.entrySet().iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(clazz)) {
                iterator.remove();
                break;
            }
        }
    }

    private static void sortListValue(final Class<? extends EventOld> clazz) {

        final ArrayHelper<Data> flexibleArray = new ArrayHelper<Data>();

        for (final byte b : Priority.VALUE_ARRAY) {
            for (final Data methodData : EventManagerOld.REGISTRY_MAP.get(clazz)) {
                if (methodData.priority == b) {
                    flexibleArray.add(methodData);
                }
            }
        }

        EventManagerOld.REGISTRY_MAP.put(clazz, flexibleArray);
    }

    private static boolean isMethodBad(final Method method) {

        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTargetOld.class);
    }

    private static boolean isMethodBad(final Method method, final Class<? extends EventOld> clazz) {

        return isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
    }

    public static ArrayHelper<Data> get(final Class<? extends EventOld> clazz) {

        return EventManagerOld.REGISTRY_MAP.get(clazz);
    }

    public static void shutdown() {

        EventManagerOld.REGISTRY_MAP.clear();
    }

    static {
        REGISTRY_MAP = new HashMap<Class<? extends EventOld>, ArrayHelper<Data>>();
    }

}
