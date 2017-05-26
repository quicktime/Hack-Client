package at.tiam.bolt.camera;

import at.tiam.bolt.gui.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.projectile.EntityArrow;

/**
 * Created by quicktime on 5/26/17.
 */
public class ArrowCamera extends Camera {

    private EntityArrow currentArrow;
    private Minecraft minecraft;

    public ArrowCamera(Window displedWindow) {
        super(displedWindow);
        minecraft = Minecraft.getMinecraft();
    }

    @Override
    public void updateFramebuffer() {
        if (isArrowAlive()) {
            setToEntityPosition(currentArrow);
            cameraRotationYaw = currentArrow.rotationYaw + 90;
            cameraRotationPitch = currentArrow.rotationPitch + 180;
        } else {
            for (Object object : minecraft.world.loadedEntityList) {
                if (object != null && object instanceof EntityArrow) {
                    EntityArrow arrow = (EntityArrow)object;
                    if (arrow.shootingEntity == minecraft.player) {
                        currentArrow = arrow;
                    }
                }
            }
            setToEntityPosition(minecraft.player);
        }
        super.updateFramebuffer();
    }

    private boolean isArrowAlive() {
        return currentArrow != null &&
                !currentArrow.onGround &&
                !currentArrow.isCollided &&
                !currentArrow.isDead &&
                minecraft.world.loadedEntityList.contains(currentArrow);
    }
}
