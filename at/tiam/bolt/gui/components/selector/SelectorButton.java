package at.tiam.bolt.gui.components.selector;

import at.tiam.bolt.gui.components.buttons.Button;

/**
 * Created by quicktime on 5/26/17.
 */
public class SelectorButton extends Button {

    protected String text;
    protected boolean isSelected;
    protected SelectorSystem<SelectorButton> system;

    public SelectorButton(String text, SelectorSystem system) {
        super();
        this.text = text;
        this.system = system;
    }

    @Override
    public boolean isEnabled() {
        return isSelected;
    }

    @Override
    public void toggle() {
        system.setOnly(this);
    }

    @Override
    public String getText() {
        return text;
    }

    public String getStaticText() {
        return text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public boolean isCentered() {
        return false;
    }
}
