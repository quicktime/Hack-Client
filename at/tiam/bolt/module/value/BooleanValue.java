package at.tiam.bolt.module.value;

import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.IRectangle;
import at.tiam.bolt.gui.components.buttons.BooleanButton;

/**
 * Created by quicktime on 5/26/17.
 */
public class BooleanValue extends AbstractValue<Boolean> {

    public BooleanValue(String name, String saveName, ValueRegistry valueRegistry, Boolean defaultValue) {
        super(name, saveName, valueRegistry, defaultValue);
    }

    @Override
    public AbstractComponent getComponent(IRectangle panel) { return new BooleanButton(this); }
}
