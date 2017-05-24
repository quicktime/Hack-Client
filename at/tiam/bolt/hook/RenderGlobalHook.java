package at.tiam.bolt.hook;

import at.tiam.bolt.module.modules.world.NearbyRespawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import at.tiam.bolt.Bolt;

//TODO: Use this for something
public class RenderGlobalHook extends RenderGlobal{

	public RenderGlobalHook(Minecraft mcIn) {
		super(mcIn);
	}

	public NearbyRespawn nearbyRespawn;
	
	@Override
	public void onEntityAdded(Entity e) {
		super.onEntityAdded(e);
		nearbyRespawn.onEntityAdded(e);
	}

}
