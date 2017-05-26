package at.tiam.bolt.module.modules;

import at.tiam.bolt.event.oldevent.EventTargetOld;
import at.tiam.bolt.event.oldevent.EventUpdate;
import at.tiam.bolt.module.RegisterMod;
import net.minecraft.client.Minecraft;
import at.tiam.bolt.module.Module;

/**
 * Created by quicktime on 5/22/17.
 */
@RegisterMod(name = "Step", desc = "Instantly walk up blocks", category = Module.Category.MOVEMENT)
public class Step extends Module {

    @EventTargetOld
    public void onUpdate(EventUpdate event) {
        if (player.isCollidedHorizontally && player.onGround) {
            player.stepHeight = 1.2f;
        }
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().player.stepHeight = 0.5f;
    }
}
