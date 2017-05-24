package at.tiam.bolt.hook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import at.tiam.bolt.camera.Camera;

public class ItemRendererHook extends ItemRenderer{

	public ItemRendererHook(Minecraft mcIn) {
		super(mcIn);
	}
	
	@Override
    public void renderFireInFirstPerson(float f){
		
    	if(!Camera.isCapturing()){
    		super.renderFireInFirstPerson(f);
    	}
    }

}
