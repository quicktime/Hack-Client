package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.ColorType;
import at.tiam.bolt.ttf.FontManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.SoundEvents;

/**
 * Created by quicktime on 5/25/17.
 */
public abstract class Button extends AbstractComponent {

    FontRenderer fontRenderer = Bolt.getBolt().getFontRenderer();

    public Button() {

    }

    @Override
    public void draw(int x, int y) {
        if (isEnabled()) {
            fontRenderer.drawStringWithShadow(getText(),
                    getX() + (isCentered() ? (rect.getWidth() / 2 - fontRenderer.getStringWidth(getText()) / 2) : 3),
                    getY() + getHeight() / 2 - fontRenderer.FONT_HEIGHT / 2,
            mouseOverButton(x, y, getX(), getY()) ? ColorType.HIGHLIGHT.getModifiedColor() : ColorType.HIGHLIGHT.getColor());
        } else {
            fontRenderer.drawStringWithShadow(getText(),
                    getX() + (isCentered() ? (rect.getWidth() / 2 - fontRenderer.getStringWidth(getText()) / 2) : 3),
                    getY() + getHeight() / 2 - fontRenderer.FONT_HEIGHT / 2,
                    mouseOverButton(x, y, getX(), getY()) ? ColorType.TEXT.getModifiedColor() : ColorType.TEXT.getColor());
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(mouseOverButton(x, y, getX(), getY())){
            Bolt.getBolt().getMc().player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 1);
            toggle();
        }
    }

    public boolean mouseOverButton(int x, int y, int cx, int cy){
        return x > cx && x < cx+rect.getWidth() && y > cy && y < cy+getHeight();
    }

    @Override
    public int getHeight() {
        return 12;
    }

    public abstract boolean isCentered();
    public abstract boolean isEnabled();
    public abstract void toggle();
    public abstract String getText();
}
