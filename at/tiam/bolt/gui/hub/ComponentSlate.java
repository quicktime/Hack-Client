package at.tiam.bolt.gui.hub;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.Canvas;
import at.tiam.bolt.gui.IScalerPosition;

import java.io.IOException;

/**
 * Created by quicktime on 5/26/17.
 */
public abstract class ComponentSlate extends Slate {

    private Bolt bolt;
    protected Canvas canvas;
    private GuiHub guiHub;

    public ComponentSlate(String name, final GuiHub guiHub, Bolt bolt) {
        super(name, null, guiHub);
        this.bolt = bolt;
        this.guiHub = guiHub;

        IScalerPosition position = new IScalerPosition() {
            @Override
            public int getX() {
                return guiHub.width / 2 - 150;
            }

            @Override
            public int getY() {
                return guiHub.height / 2 - canvas.getHeight() / 2;
            }
        };

        canvas = new Canvas(position, 300);
    }

    @Override
    public void renderSlate(int x, int y) {
        canvas.draw(x, y);
    }

    @Override
    public void mouseClicked(int x, int y) { canvas.mouseClicked(x, y); }

    @Override
    public void mouseReleased(int x, int y, int state) { canvas.mouseReleased(x, y, state); }

    @Override
    public void keyTyped(char c, int key) throws IOException {
        canvas.keyPress(c, key);
    }

    public abstract void init();
}
