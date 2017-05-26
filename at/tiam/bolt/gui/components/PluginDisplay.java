package at.tiam.bolt.gui.components;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.api.PluginData;
import at.tiam.bolt.gui.AbstractComponent;
import net.minecraft.client.gui.FontRenderer;

/**
 * Created by quicktime on 5/26/17.
 */
public class PluginDisplay extends AbstractComponent {

    private PluginData plugin;
    private String message;
    private FontRenderer fontRenderer;

    public PluginDisplay(){
        this(null);
    }

    public PluginDisplay(PluginData plugin) {
        super();
        this.plugin = plugin;
        this.fontRenderer = Bolt.getBolt().getFontRenderer();
    }

    @Override
    public int getHeight() {
        return 36;
    }

    @Override
    public void draw(int x, int y) {

        if (plugin == null) {

            if (message == null) {
                fontRenderer.drawStringWithShadow("No Plugin selected", getX() + 3, getY() + 2, 0xFFFFFFFF);
            } else {
                fontRenderer.drawStringWithShadow(message, getX() + 3, getY() + 2, 0xFFFFFFFF);
            }

        } else {
            fontRenderer.drawStringWithShadow("Name: " + plugin.getName(), getX() + 3, getY() + 2, 0xFFFFFFFF);
            fontRenderer.drawStringWithShadow("Description: " + plugin.getDescription(), getX() + 3, getY() + 14, 0xFFFFFFFF);
            fontRenderer.drawStringWithShadow("Download link: " + plugin.getFilePath(), getX() + 3, getY() + 26, 0xFFFFFFFF);
        }

    }

    public PluginData getPlugin() {
        return plugin;
    }

    public void setPlugin(PluginData plugin) {
        this.plugin = plugin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
