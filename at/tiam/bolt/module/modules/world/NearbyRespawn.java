package at.tiam.bolt.module.modules.world;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;

/**
 * Created by quicktime on 5/24/17.
 */
public class NearbyRespawn extends Module {

    public NearbyRespawn() {

    }

    private String lastPlayer;

    //Called by RenderGlobalHook
    public void onEntityAdded(Entity e) {
        if (isEnabled() && e instanceof EntityOtherPlayerMP && Bolt.getBolt().getPlayer().ticksExisted > 5 && lastPlayer != e.getName()) {
            Bolt.getBolt().addChat("Player spawned nearby: " + e.getName());
            lastPlayer = e.getName();
    }
}}
