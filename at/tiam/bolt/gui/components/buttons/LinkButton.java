package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.util.ClientUtils;

/**
 * Created by quicktime on 5/25/17.
 */
public class LinkButton extends Button {

    private String text;
    private String link;

    public LinkButton(String text, String link) {
        super();
        this.text = text;
        this.link = link;
    }

    @Override
    public boolean isCentered() { return false; }

    @Override
    public boolean isEnabled() { return false; }

    @Override
    public void toggle() { ClientUtils.openBrowserWindow(link); }

    @Override
    public String getText() { return text; }
}
