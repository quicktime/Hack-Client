package at.tiam.bolt.module;

import at.tiam.bolt.api.PluginData;

/**
 * Created by quicktime on 5/25/17.
 */
public class PluginModule extends Module {

    private PluginData pluginData = null;

    public PluginModule() { super(); }

    public PluginData getPluginData() { return pluginData; }

    public void setPluginData(PluginData pluginData) { this.pluginData = pluginData; }
}
