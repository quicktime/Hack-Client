package at.tiam.bolt.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

/**
 * Created by quicktime on 5/26/17.
 */
public class Scissor {

    public static ScaledResolution getScaledResolution() {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        return scaledResolution;
    }

    public static void enableScissoring() {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    /**
     * @author Jonalu
     * @return Used for nifty things ;)
     */
    public static void scissor(final double x, final double y, final double w, final double h) {
        final ScaledResolution scaledResolution = getScaledResolution();
        final int factor = scaledResolution.getScaleFactor();

        final double x2 = x + w, y2 = y + h;

        GL11.glScissor((int)(x * factor), (int)((scaledResolution.getScaledHeight() - y2) * factor),
                (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }

    public static void disableScissoring() { GL11.glDisable(GL11.GL_SCISSOR_TEST); }

}
