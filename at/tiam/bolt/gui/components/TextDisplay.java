package at.tiam.bolt.gui.components;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.AbstractComponent;

/**
 * Created by quicktime on 5/25/17.
 */
public class TextDisplay extends AbstractComponent {

    private String text;

    public TextDisplay(String text) {
        super();
        this.text = text;
    }

    @Override
    public int getHeight() { return 12; }

    @Override
    public void draw(int x, int y) {
        Bolt.getBolt().getFontRenderer().drawStringWithShadow(text, getX() + 3, getY() + 2, 0xFFFFFFFF);
    }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }
}
