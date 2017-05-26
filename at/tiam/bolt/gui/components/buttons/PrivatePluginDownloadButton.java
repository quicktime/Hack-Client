package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.Bolt;

/**
 * Created by quicktime on 5/26/17.
 */
public class PrivatePluginDownloadButton extends Button {

    private PrivatePluginInformationButton information;

    public PrivatePluginDownloadButton(PrivatePluginInformationButton information) {
        super();
        this.information = information;
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

        if(isPluginSelected()){
            Bolt.getBolt().getPluginManager().downloadPlugin(information.getDownloadThread().getPlugin());
        }

    }

    @Override
    public String getText() {
        return isPluginSelected()? "Download plugin":"";
    }

    private boolean isPluginSelected(){
        return information.getDownloadThread() != null && !information.getDownloadThread().isRunning() && information.getDownloadThread().getPlugin() != null;
    }

}
