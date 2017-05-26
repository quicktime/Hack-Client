package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.module.value.BooleanValue;

/**
 * Created by quicktime on 5/26/17.
 */
public class BooleanButton extends Button {

    private BooleanValue value;

    public BooleanButton(BooleanValue value) {
        super();
        this.value = value;
    }

    @Override
    public boolean isCentered() { return true; }

    @Override
    public boolean isEnabled() { return value.getValue(); }

    @Override
    public void toggle() { value.setValue(!value.getValue()); }

    @Override
    public String getText() { return value.getName(); }
}
