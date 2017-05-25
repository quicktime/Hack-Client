package at.tiam.bolt.gui.components;

import at.tiam.bolt.gui.ClickGui;
import at.tiam.bolt.gui.ColorType;
import at.tiam.bolt.gui.components.buttons.Button;
import at.tiam.bolt.util.GuiUtils;

/**
 * Created by quicktime on 5/25/17.
 */
public class ColorPicker extends Button { // TODO: Create Button class (at.tiam.bolt.gui.components.buttons)
    private ColorType colorType;
    private ClickGui clickGui;

    public ColorPicker(ColorType colorType, ClickGui clickGui) {
        super();
        this.colorType = colorType;
        this.clickGui = clickGui;
    }

    @Override
    public void draw(int x, int y) {
        super.draw(x, y);
        GuiUtils.drawRect(getX() + rect.getWidth() - 10, getY() + 2, getX() + rect.getWidth() - 2, getY() + 10, colorType.getColor());
    }

    @Override
    public boolean isCentered() { return false; }

    @Override
    public boolean isEnabled() { return false; }

    @Override
    public void toggle() { colorType.cycleIndex(); }

    @Override
    public String getText() { return colorType.getName(); }
}
