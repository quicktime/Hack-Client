package at.tiam.bolt.api;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.Window;
import at.tiam.bolt.module.Module;

import java.util.ArrayList;

/**
 * The main class used by any plugins
 * @author RandomAmazingGuy
 */
public abstract class PluginRegister {

    /**
     * Used for the plugin to add components to the plugin's window
     * @param bolt
     * @param win
     */
    public abstract void addElements(Bolt bolt, Window win);

    /**
     * Used for the plugin to add mods
     * @param bolt
     * @param list
     */
    public abstract void init(Bolt bolt, ArrayList<Module> list);

    /**
     * The name of the plugin. This is used as the window name and is how the plugin will be identified in the GUI
     * @return
     */
    public abstract String getName();

    /**
     * The description of the plugin. Replaced by a server-side description
     * @return
     */
    @Deprecated
    public String getDescription(){
        return "";
    }

    /**
     * Returns the width which the plugin window should have, defaulting to 85
     * @return
     */
    public int getWidth(){
        return 85;
    }
}
