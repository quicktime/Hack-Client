package at.tiam.bolt.camera;

import at.tiam.bolt.gui.Window;
import net.minecraft.client.Minecraft;

/**
 * Created by quicktime on 5/26/17.
 */
public class PeriscopeCamera extends Camera {

    private Minecraft minecraft;

    public PeriscopeCamera(Window displayedWindow) {
        super(displayedWindow);
        minecraft = Minecraft.getMinecraft();
    }

    @Override
    public void updateFramebuffer() {
        setToEntityPositionAndRotation(minecraft.player);

        this.cameraPosY += 8;

        super.updateFramebuffer();
    }
}
