package at.tiam.bolt.gui.hub;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.components.PluginDisplay;
import at.tiam.bolt.gui.components.TextField;
import at.tiam.bolt.gui.components.buttons.PrivatePluginDownloadButton;
import at.tiam.bolt.gui.components.buttons.PrivatePluginInformationButton;

/**
 * Created by quicktime on 5/26/17.
 */
public class PrivatePluginSlate extends ComponentSlate {

    public PrivatePluginSlate(GuiHub guiHub, Bolt bolt) {
        super("Private Plugins", guiHub, bolt);
    }

    @Override
    public void init() {
        PluginDisplay pluginDisplay;
        TextField nameField;
        TextField passwordField;
        PrivatePluginInformationButton informationButton;

        canvas.addComponent(nameField = new TextField("Plugin Name"));
        canvas.addComponent(passwordField = new TextField("Plugin Password"));

        canvas.addComponent(pluginDisplay = new PluginDisplay());

        canvas.addComponent(informationButton = new PrivatePluginInformationButton(pluginDisplay, nameField, passwordField));
        canvas.addComponent(new PrivatePluginDownloadButton(informationButton));
    }
}
