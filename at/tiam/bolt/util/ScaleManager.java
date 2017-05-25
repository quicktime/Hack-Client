package at.tiam.bolt.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by quicktime on 5/25/17.
 */
public class ScaleManager {
    private Minecraft mc = Minecraft.getMinecraft();

    private int lastGuiSize, scaleFactor;

    public ScaledResolution scaledResolution = new ScaledResolution(mc);

    public int getScaleFactor(){
        if(mc.gameSettings.guiScale != lastGuiSize){
            scaledResolution = new ScaledResolution(mc);
            scaleFactor = scaledResolution.getScaleFactor();

            lastGuiSize = mc.gameSettings.guiScale;
        }

        return scaleFactor;
    }
}
