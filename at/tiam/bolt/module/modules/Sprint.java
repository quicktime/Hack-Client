package at.tiam.bolt.module.modules;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.event.EventTargetOld;
import at.tiam.bolt.event.events.EventTick;
import at.tiam.bolt.event.events.EventUpdate;
import at.tiam.bolt.module.RegisterMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import at.tiam.bolt.module.Module;

/**
 * Created by quicktime on 5/22/17.
 */
@RegisterMod(name = "Sprint", desc = "Continuous sprint", category = Module.Category.MOVEMENT)
public class Sprint extends Module {
    private EntityPlayerSP player = Bolt.getBolt().getPlayer();

    @EventTargetOld
    public void onUpdate(EventTick event) {
//            Minecraft.getMinecraft().player.setSprinting();
            player.setSprinting(player.moveForward > 0 && player.getFoodStats().getFoodLevel() > 4);
    }

    @Override
    public void onDisable() {
        player.setSprinting(false);
    }
}
