package at.tiam.bolt.module.value;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by quicktime on 5/24/17.
 */
public class ValueRegistry {

    private Map<String, Object> valueRegistry = new HashMap<String, Object>();

    public boolean hasValue(String name) { return valueRegistry.get(name) != null; }

    public Object get(String name) { return valueRegistry.get(name); }

    public int getInteger(String name) { return (Integer)valueRegistry.get(name); }

    public double getDouble(String name) { return (Double)valueRegistry.get(name); }

    public boolean getBoolean(String name) { return (Boolean)valueRegistry.get(name); }

    public void set(String name, Object object) {
        // Only in Java 8
        valueRegistry.replace(name, object);
    }

    public Map<String, Object> getValueRegistry() { return valueRegistry; }

    public void setValueRegistry(Map<String, Object> valueRegistry) { this.valueRegistry = valueRegistry; }

}
