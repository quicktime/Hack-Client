package at.tiam.bolt.hook;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.camera.Camera;
import at.tiam.bolt.event.EventGuiRender;
import at.tiam.bolt.gui.Window;
import at.tiam.bolt.module.Module;
import com.darkmagician6.eventapi.EventManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.text.TextFormatting;

public class GuiIngameHook extends GuiIngame{
	
	public GuiIngameHook(Minecraft mc) {
		super(mc);

		Bolt.setClient(new Bolt(mc));
		Bolt.getBolt().init();
		
		hideClientModule = Bolt.getBolt().getHideClientModule();
	}
	
	private Module hideClientModule;
	
	private EventGuiRender eventGuiRender = new EventGuiRender();
	
	//private Camera cam = new Camera();

	// Was public void func_175180_a(float p_175180_1_)

	@Override
	public void renderGameOverlay(float p_175180_1_){
		
		super.renderGameOverlay(p_175180_1_);
		
		if(!Bolt.getBolt().getMc().gameSettings.showDebugInfo) {
			if(!hideClientModule.isEnabled()){
				Bolt.getBolt().getFontRenderer().drawStringWithShadow("Bolt " + TextFormatting.GREEN + Bolt.VERSION + TextFormatting.LIGHT_PURPLE + " (" + ( Bolt.getBolt().getSession().isPremium() ? "Premium":"Beta" ) + ")", 3, 2, 0xFFFFFFFF);
			}
			
			if(Bolt.getBolt().getMc().currentScreen == null){
				for(Window win : Bolt.getBolt().clickGui.windows){
					if(win.pinned){
						win.renderWindow(0, 0, false);
					}
				}
			}
		}
		
		EventManager.call(eventGuiRender);
		
		/*
		cam.draw(0, 0, 180, 100);
		
		cam.cameraPosX = Client.getClient().getPlayer().posX;
		cam.cameraPosY = Client.getClient().getPlayer().posY;
		cam.cameraPosZ = Client.getClient().getPlayer().posZ;
		
		cam.cameraRotationYaw = Client.getClient().getPlayer().rotationYaw;
		cam.cameraRotationPitch = Client.getClient().getPlayer().rotationPitch;
		
		cam.updateFramebuffer();
		*/
		
		for(Camera camera : Bolt.getBolt().getCameras()){
			
			/*
			camera.cameraPosX = Client.getClient().getPlayer().posX;
			camera.cameraPosY = Client.getClient().getPlayer().posY;
			camera.cameraPosZ = Client.getClient().getPlayer().posZ;
			
			camera.cameraRotationYaw = Client.getClient().getPlayer().rotationYaw;
			camera.cameraRotationPitch = Client.getClient().getPlayer().rotationPitch;
			*/
			
			camera.updateFramebuffer();
			
		}
	}
}
