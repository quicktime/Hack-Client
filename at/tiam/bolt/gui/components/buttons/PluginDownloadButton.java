package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.api.DependencyNotFoundException;
import at.tiam.bolt.api.DependencyNotInstalledException;
import at.tiam.bolt.api.PluginData;
import at.tiam.bolt.gui.components.selector.SelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorSystem;

/**
 * Created by quicktime on 5/26/17.
 */
public class PluginDownloadButton extends Button {

    private SelectorSystem<SelectorButton> system;

    public PluginDownloadButton(SelectorSystem<SelectorButton> system) {
        super();
        this.system = system;
    }

    @Override
    public boolean isCentered() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void toggle() {

        if(system.selectedButton != null){

            String name = system.selectedButton.getStaticText();

            PluginData selected = getSelected();

            if(selected != null){

                if(Bolt.getBolt().getPluginManager().pluginEnabled(selected)){
                    Bolt.getBolt().getPluginManager().removePlugin(selected);
                }else{
                    try {
                        Bolt.getBolt().getPluginManager().addPlugin(selected);
                    } catch (DependencyNotInstalledException e) {
                        Bolt.getBolt().addChat("Error - Plugin dependancy not installed: " + e.getDependency());
                    } catch (DependencyNotFoundException e) {
                        Bolt.getBolt().addChat("Error - Plugin dependancy not found or installed: " + e.getDependency());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public String getText() {

        PluginData selected = getSelected();

        if(selected == null){
            return "No Plugin Selected";
        }else{
            return Bolt.getBolt().getPluginManager().pluginEnabled(selected) ? "Remove Plugin":"Add Plugin";
        }
    }

    private PluginData getSelected(){

        if(system.selectedButton != null){

            String name = system.selectedButton.getStaticText();

            for(PluginData plugin : Bolt.getBolt().getPluginManager().pluginData){
                if(plugin.getName().equals(name)){
                    return plugin;
                }
            }
        }

        return null;
    }
}
