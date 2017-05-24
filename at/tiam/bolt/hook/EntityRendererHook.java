package at.tiam.bolt.hook;

import at.tiam.bolt.module.Module;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import at.tiam.bolt.Bolt;
import at.tiam.bolt.camera.Camera;
import at.tiam.bolt.module.modules.player.NoHitFlinch;

public class EntityRendererHook extends EntityRenderer{

	public EntityRendererHook(Minecraft mcIn, IResourceManager p_i45076_2_) {
		super(mcIn, p_i45076_2_);
	}
	
	@Override
	public float getFOVModifier(float partialTicks, boolean p_78481_2_) {
		
		if(Camera.isCapturing()){
            return 90.0F;
		}else{
			return super.getFOVModifier(partialTicks, p_78481_2_);
		}
	}
	
	//Set by Client after the mod manager is created
	public Module noHitFlinch;
	
	@Override
	public void hurtCameraEffect(float damage) {
		
		if(!noHitFlinch.isEnabled()){
			super.hurtCameraEffect(damage);
		}
	}
}
