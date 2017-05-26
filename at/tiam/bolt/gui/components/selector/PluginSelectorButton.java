package at.tiam.bolt.gui.components.selector;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.api.PluginData;

/**
 * Created by quicktime on 5/26/17.
 */
public class PluginSelectorButton extends SelectorButton {

    private PluginData pluginData;

    public PluginSelectorButton(String mod, SelectorSystem selectorSystem, PluginData pluginData) {
        super(mod, selectorSystem);
        this.pluginData = pluginData;
    }

    @Override
    public String getText() {
        if (Bolt.getBolt().getPluginManager().pluginEnabled(pluginData)) {
            return super.getText() + " (On)";
        } else {
            return super.getText();
        }
    }
}
