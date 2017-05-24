package at.tiam.bolt.ui;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.event.EventManager;
import at.tiam.bolt.event.EventTarget;
import at.tiam.bolt.event.events.EventRender2D;
import at.tiam.bolt.module.Module;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by quicktime on 5/22/17.
 */
public class Hud {
    public Hud() {
        EventManager.register(this);
    }

    @EventTarget
    public void onRender(EventRender2D event) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Bolt.INSTANCE.FONT_MANAGER.hud.drawString(Bolt.INSTANCE.NAME + " Rel-" + Bolt.INSTANCE.VERSION, 2, 5, -1);

        renderArrayList(scaledResolution);
    }

    private void renderArrayList(ScaledResolution scaledResolution) {
        int yCount = 5;
        int right = scaledResolution.getScaledWidth();

        for (Module module : Bolt.INSTANCE.MODULE_MANAGER.getAllModules()) {
            if (module.getState() && module.getCategory() != Module.Category.GUI) {
                Bolt.INSTANCE.FONT_MANAGER.arraylist.drawString(module.getName(), right - Bolt.INSTANCE.FONT_MANAGER.arraylist.getStringWidth(module.getName()), yCount, module.getCategory().color);
                yCount += 10;
            }
        }
    }
}