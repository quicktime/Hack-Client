package at.tiam.bolt.gui;

import at.tiam.bolt.gui.components.TextDisplay;
import at.tiam.bolt.module.ModuleManager;

/**
 * Created by quicktime on 5/25/17.
 */
public class WindowPopup extends WindowClosable {

    public WindowPopup(String title, String message, ModuleManager moduleManager, int width, int screenWidth, int screenHeight) {
        super(title, moduleManager, width);
        this.addComponent(new TextDisplay(message));
        this.posX = (screenWidth / 2) - (width / 2);
        this.posY = (screenHeight / 2);
    }
}
