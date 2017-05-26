package at.tiam.bolt.gui.components.scrolling;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.api.PluginData;
import at.tiam.bolt.gui.components.selector.LargePluginSelectorButton;
import at.tiam.bolt.gui.components.selector.PluginSelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorSystem;

/**
 * Created by quicktime on 5/26/17.
 */
public class PluginScrollPane extends FilterableScrollPane {

    private SelectorSystem<SelectorButton> system;
    private boolean description;

    public PluginScrollPane(int height, SelectorSystem<SelectorButton> system, boolean description) {
        super(height);
        this.system = system;
        this.description = description;
    }

    private boolean hasFoundPlugins;

    @Override
    public void draw(int x, int y) {

        if(!hasFoundPlugins){
            if(Bolt.getBolt().getPluginManager().pluginData.size() > 0){
                for(PluginData plugin : Bolt.getBolt().getPluginManager().pluginData){

                    if(description){
                        addFilterableComponent(system.add(new LargePluginSelectorButton(plugin, system)));
                    }else{
                        addFilterableComponent(system.add(new PluginSelectorButton(plugin.getName(), system, plugin)));
                    }

                    hasFoundPlugins = true;
                }
            }
        }

        super.draw(x, y);
    }
}
