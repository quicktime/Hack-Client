package at.tiam.bolt.gui.hub;

import at.tiam.bolt.gui.Canvas;
import net.minecraft.client.gui.GuiScreen;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.util.timing.MillisecondTimer;
import at.tiam.bolt.util.timing.FlatTimer;
import at.tiam.bolt.gui.IScalerPosition;

import java.util.List;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
/**
 * Created by quicktime on 5/24/17.
 */
public class GuiHub extends GuiScreen {

    public int buttonPaddingX = 30;
    public int buttonWidth = 16;
    public int buttonSlateSpace = 30;
    public int cellSize = 100;
    public int cellPadding = 5;

    public int slateIndex, rotation;

    private State state = State.IDLE;
    private String trail = "";
    private MillisecondTimer timer = new FlatTimer(5);

    public ColorModifier colorModifier; // TODO: Create ColorModifier

    public List<Slate> slates = new ArrayList<Slate>();
    private Canvas options; // TODO: Create Canvas class
    private Bolt bolt;

    public GuiHub(Bolt bolt) {
        this.bolt = bolt;
        this.colorModifier = new ColorModifier();

        IScalerPosition pos = new IScalerPosition() {
            @Override
            public int getX() { return width / 2 - 50; }
            @Override
            public int getY() { return 3; }
        };
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        if (this.state == State.LOADING) {
            drawLoadingScreen();
        } else if (this.state == State.FAILED) {
            drawFailedScreen();
        } else if (this.state == State.CONNECTED) {
            renderSlates(x, y);
        }
        options.draw(x, y);
    }

    private void updateTrail() {
        if (trail.length() >= 3) {
            trail = "";
            return;
        }
        trail += ".";
    }

    public void renderSlates(int x, int y) {
        if (needsLeft()) {
            if (mouseOverLeft(x, y)) {

            }
        }
    }

}

enum State {
    IDLE, CONNECTED, LOADING, FAILED;
}
