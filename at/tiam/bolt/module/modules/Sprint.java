package at.tiam.bolt.module.modules;

import at.tiam.bolt.event.EventTargetOld;
import at.tiam.bolt.event.events.EventUpdate;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import at.tiam.bolt.module.Module;

/**
 * Created by quicktime on 5/22/17.
 */
@Module.ModInfo(name = "Sprint", description = "Continuous sprint", category = Module.Category.MOVEMENT, bind = Keyboard.KEY_G)
public class Sprint extends Module {

    @EventTargetOld
    public void onUpdate(EventUpdate event) {
        if (getState()) {
            Minecraft.getMinecraft().player.isSprinting();
        }
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().player.stepHeight = 0.5f;
    }
}
