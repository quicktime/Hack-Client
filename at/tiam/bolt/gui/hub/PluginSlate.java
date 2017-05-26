package at.tiam.bolt.gui.hub;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.components.SearchableTextField;
import at.tiam.bolt.gui.components.buttons.PluginDownloadButton;
import at.tiam.bolt.gui.components.scrolling.PluginScrollPane;
import at.tiam.bolt.gui.components.selector.SelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorSystem;

/**
 * Created by quicktime on 5/26/17.
 */
public class PluginSlate extends ComponentSlate {

    public PluginSlate(GuiHub guiHub, Bolt bolt) {
        super("Plugins", guiHub, bolt);
    }

    @Override
    public void init() {
        SelectorSystem<SelectorButton> system = new SelectorSystem<SelectorButton>();
        PluginScrollPane pluginScrollPane;
        canvas.addComponent(pluginScrollPane = new PluginScrollPane(200, system, true));

        canvas.addComponent(new SearchableTextField("Search", pluginScrollPane));
        canvas.addComponent(new PluginDownloadButton(system));
    }

}
