package at.tiam.bolt.camera;

import at.tiam.bolt.gui.Window;
import net.minecraft.client.Minecraft;

/**
 * Created by quicktime on 5/26/17.
 */
public class RearviewCamera extends Camera {

    private Minecraft minecraft;

    public RearviewCamera(Window displayedWindow) {
        super(displayedWindow, true);
        minecraft = Minecraft.getMinecraft();
    }

    @Override
    public void updateFramebuffer() {
        setToEntityPosition(minecraft.player);

        cameraRotationPitch = minecraft.player.rotationPitch;
        cameraRotationYaw = minecraft.player.rotationYaw + 180;
    }
}
