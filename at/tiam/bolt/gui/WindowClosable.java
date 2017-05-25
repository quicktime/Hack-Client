package at.tiam.bolt.gui;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.module.ModuleManager;

/**
 * Created by quicktime on 5/25/17.
 */
class WindowClosable extends Window {

    public WindowClosable(String title, ModuleManager moduleManager) {
        super(title, moduleManager, 85);
        this.extended = true;
    }

    public WindowClosable(String title, ModuleManager moduleManager, int width) {
        super(title, moduleManager, width);
        this.extended = true;
    }

    @Override
    public boolean hasPinnedButton() { return false; }

    @Override
    public void onExtensionToggle() {
        Bolt.getBolt().getClickGui().windows.remove(this); }
}
