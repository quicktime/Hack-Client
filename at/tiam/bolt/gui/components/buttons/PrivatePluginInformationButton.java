package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.api.PrivatePluginInformationThread;
import at.tiam.bolt.gui.components.PluginDisplay;
import at.tiam.bolt.gui.components.TextField;
import at.tiam.bolt.util.download.DownloadCallback;

/**
 * Created by quicktime on 5/26/17.
 */
public class PrivatePluginInformationButton extends Button implements DownloadCallback {

    private PluginDisplay pluginDisplay;
    private PrivatePluginInformationThread downloadThread;
    private TextField nameField, passwordField;

    public PrivatePluginInformationButton(PluginDisplay pluginDisplay, TextField nameField, TextField passwordField) {
        super();
        this.pluginDisplay = pluginDisplay;
        this.nameField = nameField;
        this.passwordField = passwordField;
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

        if(downloadThread == null || (downloadThread != null && !downloadThread.isRunning())){
            new Thread(downloadThread = new PrivatePluginInformationThread(nameField.getText(), passwordField.getText(), this)).start();
        }

    }

    @Override
    public String getText() {
        return "Get plugin";
    }

    @Override
    public void finished(Object download) {

        if(downloadThread.isSuccessful()){
            pluginDisplay.setPlugin(downloadThread.getPlugin());
        }else{
            pluginDisplay.setMessage("Invalid plugin name/password!");
            pluginDisplay.setPlugin(null);
        }
    }

    public PrivatePluginInformationThread getDownloadThread() {
        return downloadThread;
    }

}
