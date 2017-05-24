package at.tiam.bolt.module.modules;

import at.tiam.bolt.event.EventTarget;
import at.tiam.bolt.event.events.EventUpdate;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import at.tiam.bolt.module.Module;

/**
 * Created by quicktime on 5/22/17.
 */
@Module.ModInfo(name = "Step", description = "Instantly walk up blocks", category = Module.Category.MOVEMENT, bind = Keyboard.KEY_V)
public class Step extends Module {

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (getState()) {
            Minecraft.getMinecraft().player.stepHeight = 1;
        }
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().player.stepHeight = 0.5f;
    }
}
