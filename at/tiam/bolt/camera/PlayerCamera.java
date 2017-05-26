package at.tiam.bolt.camera;

import at.tiam.bolt.gui.Window;
import at.tiam.bolt.gui.components.selector.EntitySelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorSystem;

/**
 * Created by quicktime on 5/26/17.
 */
public class PlayerCamera extends Camera {

    private SelectorSystem<SelectorButton> selectorSystem;

    public PlayerCamera(Window displayedWindow, SelectorSystem<SelectorButton> selectorSystem) {
        super(displayedWindow);
        this.selectorSystem = selectorSystem;
    }

    @Override
    public void updateFramebuffer() {
        if (selectorSystem.selectedButton != null) {
            setToEntityPositionAndRotation(((EntitySelectorButton)selectorSystem.selectedButton).getPlayer());
        }
        super.updateFramebuffer();
    }
}
