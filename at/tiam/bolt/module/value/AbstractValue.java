package at.tiam.bolt.module.value;

import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.IRectangle;

/**
 * Created by quicktime on 5/25/17.
 */
public abstract class AbstractValue<T> {

    protected T value;
    protected String name;
    private ValueRegistry valueRegistry;
    private String saveName;

    public AbstractValue(String name, String saveName, ValueRegistry valueRegistry, T defaultValue) {
        if (valueRegistry.hasValue(saveName)) {
            this.value = (T)valueRegistry.get(saveName);
        } else {
            this.value = defaultValue;
        }

        this.valueRegistry = valueRegistry;
        this.name = name;
        this.saveName = saveName;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        valueRegistry.set(saveName, value);
    }

    public abstract AbstractComponent getComponent(IRectangle panel);


}
